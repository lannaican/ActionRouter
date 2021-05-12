package com.star.router.model;

import android.content.Context;

import com.baby.jojo.router.model.RouteResult;

/**
 * @author lnc
 * @date 2021/4/27
 */
public interface IRouteAction {

    RouteResult doAction(Context context, String url);

}
