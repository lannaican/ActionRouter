package com.baby.jojo.router.compiler;


import com.baby.jojo.router.annotation.JoJoRoute;
import com.baby.jojo.router.base.IRouteGroup;
import com.baby.jojo.router.model.RouteData;
import com.google.auto.service.AutoService;
import com.google.common.reflect.TypeToken;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

@AutoService(Processor.class)
public class RouteCompiler extends AbstractProcessor {

    private Logger logger;
    private Filer filer;

    private String moduleName;

    private boolean hasProcess = false;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        logger = new Logger(processingEnv.getMessager());
        filer = processingEnv.getFiler();
        logger.info(">>>>>>>>>>>>>JoJo Route Start<<<<<<<<<<<<<");
        Map<String, String> options = processingEnv.getOptions();
        if (options != null) {
            moduleName = options.get(Constant.GENERATE_MODULE_OPTION);
            if (moduleName != null) {
                moduleName = moduleName.replace("-", "_");
                moduleName = Constant.GENERATE_MODULE_NAME_PRE + moduleName;
            }
        }
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (moduleName == null || hasProcess) {
            return true;
        }
        logger.info("ModuleName:" + moduleName);
        try {
            TypeSpec.Builder classBuilder = TypeSpec.classBuilder(moduleName)
                    .addSuperinterface(IRouteGroup.class)
                    .addModifiers(Modifier.PUBLIC);
            //获取注解
            Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(JoJoRoute.class);
            logger.info("Find Route Num: " + elements.size());
            //添加方法
            MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("loadAction");
            methodBuilder.addModifiers(Modifier.PUBLIC);
            methodBuilder.addAnnotation(Override.class);
            methodBuilder.addParameter(new TypeToken<Map<RouteData, Class>>(){}.getType(), "map", Modifier.FINAL);
            if (!elements.isEmpty()) {
                for (Element element : elements) {
                    JoJoRoute joJoRoute = element.getAnnotation(JoJoRoute.class);
                    methodBuilder.addStatement("map.put(new $T(\"" + joJoRoute.type() + "\",\"" + joJoRoute.path() + "\"), $T.class)", RouteData.class, element);
                }
            }
            classBuilder.addMethod(methodBuilder.build());

            if (!elements.isEmpty()) {
                hasProcess = true;
                // 创建class文件
                JavaFile javaFile = JavaFile.builder(Constant.GENERATE_PACKAGE, classBuilder.build()).build();
                javaFile.writeTo(filer);
                logger.info("Create class: " + moduleName + ".class");
                logger.info(">>>>>>>>>>>>>JoJo Route End<<<<<<<<<<<<<");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Error:" + e.getMessage());
        }
        return true;
    }

    /**
     * 指定支持的注解
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new HashSet<>();
        set.add(JoJoRoute.class.getCanonicalName());
        return set;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

}
