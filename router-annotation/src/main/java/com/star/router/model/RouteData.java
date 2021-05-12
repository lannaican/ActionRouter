package com.star.router.model;

/**
 * 路由参数
 *
 * @author lnc
 * @date 2021/4/27
 */
public class RouteData {

    /**
     * 路由地址
     */
    private String url;

    public RouteData(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
