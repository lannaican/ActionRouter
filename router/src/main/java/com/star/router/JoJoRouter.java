package com.star.router;

import android.content.Context;
import androidx.annotation.Nullable;

import com.baby.jojo.router.model.RouteData;
import com.baby.jojo.router.model.RouteResult;
import com.star.router.model.IRouteAction;
import com.star.router.core.DefaultPattern;
import com.star.router.core.LogisticsCenter;
import com.star.router.core.Pattern;
import com.star.router.core.Warehouse;

/**
 * Router管理器
 *
 * @author lnc
 * @date 2021/4/27
 */
public class JoJoRouter {

    private static JoJoRouter INSTANCE;
    private static boolean hasInit = false;

    private static Pattern pattern = new DefaultPattern();

    /**
     * 初始化
     */
    public static void init() {
        if (hasInit) {
            return;
        }
        LogisticsCenter.loadRoutes();
        hasInit = true;
    }

    public static JoJoRouter getInstance() {
        if (!hasInit) {
            throw new RuntimeException("JoJo Router must init first");
        }
        synchronized(JoJoRouter.class) {
            if (INSTANCE == null) {
                INSTANCE = new JoJoRouter();
            }
        }
        return INSTANCE;
    }

    /**
     * 设置查找器
     * @param pattern
     */
    public static void setPattern(Pattern pattern) {
        if (pattern == null) {
            pattern = new DefaultPattern();
        }
        JoJoRouter.pattern = pattern;
    }


    public RouteRequest with(@Nullable Context context) {
        RouteRequest request = new RouteRequest();
        request.context = context;
        return request;
    }

    /**
     * 无需Context的跳转
     */
    public RouteResult go(String type, String path) {
        return with(null).go(type, path);
    }


    public static class RouteRequest {
        Context context;
        /**
         * 跳转逻辑
         * @param type
         * @param path
         * @return
         */
        public RouteResult go(String type, String path) {
            for (RouteData data : Warehouse.actionMap.keySet()) {
                IRouteAction action = Warehouse.actionMap.get(data);
                if (action != null && pattern.match(data, type, path)) {
                    return action.doAction(context, path);
                }
            }
            for (RouteData data : Warehouse.actionClassMap.keySet()) {
                Class<? extends IRouteAction> cls = Warehouse.actionClassMap.get(data);
                if (cls != null && pattern.match(data, type, path)) {
                    try {
                        IRouteAction action = cls.newInstance();
                        Warehouse.actionMap.put(data, action);
                        return action.doAction(context, path);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return RouteResult.error(e.getMessage());
                    }
                }
            }
            return RouteResult.notFound();
        }
    }
}
