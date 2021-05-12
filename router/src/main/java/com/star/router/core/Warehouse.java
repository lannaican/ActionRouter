package com.star.router.core;

import com.star.router.model.IRouteAction;
import com.baby.jojo.router.model.RouteData;

import java.util.HashMap;
import java.util.Map;

/**
 * 存放路由数据
 *
 * @author lnc
 * @date 2021/4/30
 */
public class Warehouse {

    public final static Map<RouteData, Class> actionClassMap = new HashMap<>();
    public final static Map<RouteData, IRouteAction> actionMap = new HashMap<>();

}
