package com.baby.jojo.router.model;

/**
 * 路由调用结果
 *
 * @author lnc
 * @date 2021/4/27
 */
public class RouteResult {

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 错误消息
     */
    private String errorMsg;

    public static RouteResult success() {
        RouteResult result = new RouteResult();
        result.success = true;
        return result;
    }

    public static RouteResult notFound() {
        RouteResult result = new RouteResult();
        result.success = false;
        result.errorMsg = "Route not found";
        return result;
    }

    public static RouteResult error(String msg) {
        RouteResult result = new RouteResult();
        result.success = false;
        result.errorMsg = msg;
        return result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
