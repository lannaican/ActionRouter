package com.baby.jojo.demo;

import android.content.Context;

import com.baby.jojo.router.annotation.JoJoRoute;
import com.star.router.model.IRouteAction;
import com.baby.jojo.router.model.RouteResult;


/**
 * @author lnc
 * @date 2021/4/30
 */
@JoJoRoute(type = "RouteURL", path = "course/abc")
public class TestAction implements IRouteAction {
    @Override
    public RouteResult doAction(Context context, String url) {
        System.out.println("doAction: " + url);
        return RouteResult.success();
    }
}
