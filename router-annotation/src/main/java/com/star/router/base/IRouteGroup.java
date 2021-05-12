package com.star.router.base;


import com.star.router.model.RouteData;

import java.util.Map;

/**
 * @author lnc
 * @date 2021/4/28
 */
public interface IRouteGroup {

    void loadAction(Map<RouteData, Class> map);

}
