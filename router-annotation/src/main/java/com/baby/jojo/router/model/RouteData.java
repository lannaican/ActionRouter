package com.baby.jojo.router.model;

/**
 * 路由参数
 *
 * @author lnc
 * @date 2021/4/27
 */
public class RouteData {

    /**
     * 路由类型
     */
    private String type;

    /**
     * 路由地址
     */
    private String url;

    public RouteData(String type, String url) {
        this.type = type;
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
