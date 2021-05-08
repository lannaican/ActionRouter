package com.baby.jojo.router.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 路由处理器注解
 *
 * @author lnc
 * @date 2021/4/27
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface JoJoRoute {

    /**
     * 路由类型
     * RouteURL、URLInfo
     *
     * @return
     */
    String type();

    /**
     * 路由路径 类型xxx/业务名xxx/接口名xxx
     *
     * @return
     */
    String path();

}
