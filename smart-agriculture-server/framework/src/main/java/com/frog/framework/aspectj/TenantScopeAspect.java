package com.frog.framework.aspectj;

import com.frog.common.annotation.DataScope;
import com.frog.common.annotation.TenantScope;
import com.frog.common.core.domain.BaseEntity;
import com.frog.common.core.domain.entity.SysRole;
import com.frog.common.core.domain.entity.SysUser;
import com.frog.common.core.domain.model.LoginUser;
import com.frog.common.utils.SecurityUtils;
import com.frog.common.utils.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @author neal
 * @date 2024/8/5  17:38
 */
@Aspect
@Component
public class TenantScopeAspect {
    /**
     * 全部数据权限【所有租户数据】
     */
    public static final String TENANT_SCOPE_ALL = "1";

    /**
     * 自定数据权限
     */
    public static final String TENANT_SCOPE_CUSTOM = "2";

    /**
     * 部门数据权限
     */
    public static final String TENANT_SCOPE_DEPT = "3";

    /**
     * 部门及以下数据权限
     */
    public static final String TENANT_SCOPE_DEPT_AND_CHILD = "4";

    /**
     * 仅本人数据权限
     */
    public static final String TENANT_SCOPE_SELF = "5";
    /**
     * 本租户数据权限
     */
    public static final String TENANT_SCOPE_TENANT="6";
    /**
     * 本租基地据权限
     */
    public static final String TENANT_SCOPE_BASE="7";
    /**
     * 租户数据权限关键字
     */
    public static final String TENANT_SCOPE = "tenantScope";

    @Before("@annotation(tenantScope)")
    public void doBefore(JoinPoint point, TenantScope tenantScope) throws Throwable
    {
        clearDataScope(point);
        handleTenantScope(point, tenantScope);
    }
    protected void handleTenantScope(final JoinPoint joinPoint, TenantScope tenantScope)
    {
        // 获取当前的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (StringUtils.isNotNull(loginUser))
        {
            SysUser currentUser = loginUser.getUser();
            // 如果是超级管理员，则不过滤数据
            if (StringUtils.isNotNull(currentUser) && !currentUser.isAdmin())
            {
                tenantScopeFilter(joinPoint, currentUser,tenantScope);
            }
        }
    }
    /**
     * 数据范围过滤
     *
     * @param joinPoint 切点
     * @param user 用户
     */
    public static void tenantScopeFilter(JoinPoint joinPoint, SysUser user,TenantScope tenantScope)
    {
        StringBuilder sqlString = new StringBuilder();
        String deptParam = "";
        String userParam ="";
        String tenantParam="";
        String baseParam ="";
        if(StringUtils.isNotBlank(tenantScope.deptAlias())) {
            deptParam = tenantScope.deptAlias()+".";
        }
        if(StringUtils.isNotBlank(tenantScope.userAlias())) {
            userParam = tenantScope.userAlias()+".";
        }
        if(StringUtils.isNotBlank(tenantScope.tenantAlias())) {
            tenantParam = tenantScope.tenantAlias()+".";
        }
        if(StringUtils.isNotBlank(tenantScope.baseAlias())) {
            baseParam = tenantScope.baseAlias()+".";
        }
        for (SysRole role : user.getRoles())
        {
            String dataScope = role.getDataScope();
            if (TENANT_SCOPE_ALL.equals(dataScope))
            {
                sqlString = new StringBuilder();
                break;
            }
            else if (TENANT_SCOPE_CUSTOM.equals(dataScope))
            {
                sqlString.append(StringUtils.format(
                        " OR {}dept_id IN ( SELECT dept_id FROM sys_role_dept WHERE role_id = {} ) ", deptParam,
                        role.getRoleId()));
            }
            else if (TENANT_SCOPE_DEPT.equals(dataScope))
            {
                sqlString.append(StringUtils.format(" OR {}dept_id = {} ", deptParam, user.getDeptId()));
            }
            else if (TENANT_SCOPE_DEPT_AND_CHILD.equals(dataScope))
            {
                sqlString.append(StringUtils.format(
                        " OR {}dept_id IN ( SELECT dept_id FROM sys_dept WHERE dept_id = {} or find_in_set( {} , ancestors ) )",
                        deptParam, user.getDeptId(), user.getDeptId()));
            }
            else if (TENANT_SCOPE_SELF.equals(dataScope))
            {
                sqlString.append(StringUtils.format(" OR {}user_id = {} ", userParam, user.getUserId()));
            }
            else if(TENANT_SCOPE_TENANT.equals(dataScope)){
                sqlString.append(StringUtils.format(" OR {}tenant_id = {} ",tenantParam, user.getTenantId()));
            }
            else if(TENANT_SCOPE_BASE.equals(dataScope)){
                sqlString.append(StringUtils.format(" OR {}base_id = {} ",baseParam, user.getBaseId()));
            }
        }
        if(tenantScope.isContainAdmin()){
            sqlString.append(" OR user_id = 1");
        }
        if (StringUtils.isNotBlank(sqlString.toString()))
        {
            Object params = joinPoint.getArgs()[0];
            if (StringUtils.isNotNull(params) && params instanceof BaseEntity)
            {
                BaseEntity baseEntity = (BaseEntity) params;
                baseEntity.getParams().put(TENANT_SCOPE, " AND (" + sqlString.substring(4) + ")");
            }
        }
    }
    /**
     * 拼接权限sql前先清空params.dataScope参数防止注入
     */
    private void clearDataScope(final JoinPoint joinPoint)
    {
        Object params = joinPoint.getArgs()[0];
        if (StringUtils.isNotNull(params) && params instanceof BaseEntity)
        {
            BaseEntity baseEntity = (BaseEntity) params;
            baseEntity.getParams().put(TENANT_SCOPE, "");
        }
    }
}
