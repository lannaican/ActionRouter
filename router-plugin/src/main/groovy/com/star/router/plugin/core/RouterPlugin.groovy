package com.star.router.plugin.core

import com.star.router.plugin.util.ScanSetting
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin

class RouterPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        def isApp = project.plugins.hasPlugin(AppPlugin)
        if (isApp) {
            System.out.println("ActionRoute Plugin Run Start")
            def android = project.extensions.getByType(AppExtension)
            def transformImpl = new RegisterTransform(project)
            ArrayList<ScanSetting> list = new ArrayList<>(3)
            list.add(new ScanSetting('IRouteGroup'))
            RegisterTransform.registerList = list
            android.registerTransform(transformImpl)
            System.out.println("ActionRoute Plugin Run End")
        }
    }
}