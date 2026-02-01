package com.frog.common.annotation;

import java.lang.annotation.*;

/**
 * @author neal
 * @date 2024/8/5  17:30
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TenantScope {
    /**
     * 租户表别名
     */
    public String tenantAlias() default "";
    /**
     * 基地表的别名
     */
    public String baseAlias() default "";
    /**
     * 部门表的别名
     */
    public String deptAlias() default "";

    /**
     * 用户表的别名
     */
    public String userAlias() default "";
    /**
     * 是否包含管理员创建数据
     */
    public boolean isContainAdmin() default false;
}
