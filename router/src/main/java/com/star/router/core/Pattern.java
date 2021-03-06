package com.star.router.core;


import com.star.router.model.RouteData;

/**
 * 查找符合条件的路由
 *
 * @author lnc
 * @date 2021/5/6
 */
public interface Pattern {

    boolean match(RouteData data, String path);

}
