package com.star.route.sample;

import android.content.Context;

import com.star.router.annotation.ActionRoute;
import com.star.router.model.IRouteAction;
import com.star.router.model.RouteResult;


/**
 * @author lnc
 * @date 2021/4/30
 */
@ActionRoute(path = "course/abc")
public class TestAction implements IRouteAction {
    @Override
    public RouteResult doAction(Context context, String url) {
        System.out.println("doAction: " + url);
        return RouteResult.success();
    }
}
