package com.baby.jojo.router.core;

import com.baby.jojo.router.base.IRouteGroup;
import com.baby.jojo.router.model.RouteData;

/**
 * @author lnc
 * @date 2021/4/30
 */
public class LogisticsCenter {

    private static boolean registerByPlugin;

    public static void loadRoutes() {
        System.out.println("-----------------loadRoutes()");
    }

    //由Plugin注册
    private static void register(String className) {
        if (className != null && className.length() > 0) {
            try {
                Class<?> clazz = Class.forName(className);
                Object obj = clazz.getConstructor().newInstance();
                if (obj instanceof IRouteGroup) {
                    registerRouteGroup((IRouteGroup) obj);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void registerRouteGroup(IRouteGroup group) {
        markRegisterResult();
        if (group != null) {
            group.loadAction(Warehouse.actionClassMap);
        }
    }

    private static void markRegisterResult() {
        if (!registerByPlugin) {
            registerByPlugin = true;
        }
    }

}
