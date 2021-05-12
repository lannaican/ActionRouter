package com.star.router.core;

import android.text.TextUtils;

import com.baby.jojo.router.model.RouteData;

/**
 * 默认的查找器
 *
 * @author lnc
 * @date 2021/5/6
 */
public class DefaultPattern implements Pattern{

    @Override
    public boolean match(RouteData data, String type, String path) {
        return TextUtils.equals(data.getType(), type) && !TextUtils.isEmpty(path) && path.contains(data.getUrl());
    }
}
