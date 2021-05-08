package com.baby.jojo.router.base;


import com.baby.jojo.router.model.RouteData;

import java.util.Map;

/**
 * @author lnc
 * @date 2021/4/28
 */
public interface IRouteGroup {

    void loadAction(Map<RouteData, Class> map);

}
