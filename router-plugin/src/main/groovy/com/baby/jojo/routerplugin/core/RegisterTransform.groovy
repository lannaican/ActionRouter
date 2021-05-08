package com.baby.jojo.routerplugin.core

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import org.apache.commons.codec.digest.DigestUtils
import org.gradle.api.Project
import com.baby.jojo.routerplugin.util.ScanUtil
import com.baby.jojo.routerplugin.util.ScanSetting

/**
 * @author lnc
 * @date 2021/4/30
 */
class RegisterTransform extends Transform {

    public static File fileContainsInitClass;
    static ArrayList<ScanSetting> registerList
    static Project project;

    public RegisterTransform(Project project) {
        this.project = project;
    }

    @Override
    public String getName() {
        return "JoJoRouteTransform";
    }

    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        //指定接收Class类型
        return TransformManager.CONTENT_CLASS;
    }

    @Override
    public Set<? super QualifiedContent.Scope> getScopes() {
        //指定作用域
        return TransformManager.SCOPE_FULL_PROJECT;
    }

    @Override
    public boolean isIncremental() {
        //不支持增量编译
        return false;
    }

    @Override
    public void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation);
        long startTime = System.currentTimeMillis()
        boolean leftSlash = File.separator == '/'
        def outputProvider = transformInvocation.outputProvider
        transformInvocation.inputs.each { TransformInput input ->

            // scan all jars
            input.jarInputs.each { JarInput jarInput ->
                String destName = jarInput.name
                // rename jar files
                def hexName = DigestUtils.md5Hex(jarInput.file.absolutePath)
                if (destName.endsWith(".jar")) {
                    destName = destName.substring(0, destName.length() - 4)
                }
                // input file
                File src = jarInput.file
                // output file
                File dest = outputProvider.getContentLocation(destName + "_" + hexName, jarInput.contentTypes, jarInput.scopes, Format.JAR)

                //scan jar file to find classes
                if (ScanUtil.shouldProcessPreDexJar(src.absolutePath)) {
                    ScanUtil.scanJar(src, dest)
                }
                FileUtils.copyFile(src, dest)

            }
            // scan class files
            input.directoryInputs.each { DirectoryInput directoryInput ->
                File dest = outputProvider.getContentLocation(directoryInput.name, directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY)
                String root = directoryInput.file.absolutePath
                if (!root.endsWith(File.separator))
                    root += File.separator
                directoryInput.file.eachFileRecurse { File file ->
                    def path = file.absolutePath.replace(root, '')
                    if (!leftSlash) {
                        path = path.replaceAll("\\\\", "/")
                    }
                    if(file.isFile() && ScanUtil.shouldProcessClass(path)){
                        ScanUtil.scanClass(file)
                    }
                }

                // copy to dest
                FileUtils.copyDirectory(directoryInput.file, dest)
            }
        }

        if (fileContainsInitClass) {
            registerList.each { ext ->
                if (ext.classList.isEmpty()) {

                } else {
                    RegisterCodeGenerator.insertInitCodeTo(ext)
                }
            }
        }
    }
}
