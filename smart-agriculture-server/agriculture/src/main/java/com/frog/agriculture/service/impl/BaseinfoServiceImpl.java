package com.frog.agriculture.service.impl;

import java.util.List;

import com.frog.common.core.domain.entity.SysRole;
import com.frog.common.core.domain.entity.SysUser;
import com.frog.common.utils.DateUtils;
import com.frog.common.utils.SecurityUtils;
import com.frog.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.frog.agriculture.mapper.BaseinfoMapper;
import com.frog.agriculture.domain.Baseinfo;
import com.frog.agriculture.service.IBaseinfoService;

/**
 * 基地信息Service业务层处理
 * 
 * @author nealtsiao
 * @date 2023-05-13
 */
@Service
public class BaseinfoServiceImpl implements IBaseinfoService 
{
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

    @Autowired
    private BaseinfoMapper baseinfoMapper;

    /**
     * 查询基地信息
     * 
     * @param baseId 基地信息主键
     * @return 基地信息
     */
    @Override
    public Baseinfo selectBaseinfoByBaseId(Long baseId)
    {
        return baseinfoMapper.selectBaseinfoByBaseId(baseId);
    }
    /**
     * 查询基地信息
     *
     * @param deptId 基地信息主键
     * @return 基地信息
     */
    @Override
    public Baseinfo selectBaseinfoByDeptId(Long deptId) {
        Baseinfo baseinfo = baseinfoMapper.selectBaseinfoByDeptId(deptId);
        return baseinfo;
    }

    /**
     * 查询基地信息列表
     * 
     * @param baseinfo 基地信息
     * @return 基地信息
     */
    @Override
    public List<Baseinfo> selectBaseinfoList(Baseinfo baseinfo)
    {
        if(!SecurityUtils.isAdmin(SecurityUtils.getUserId())){
            baseinfo.setDeptId(SecurityUtils.getBaseId());
        }
        return baseinfoMapper.selectBaseinfoList(baseinfo);
    }

    /**
     * 根据角色查询基地信息
     *
     * @return
     */
    @Override
    public List<Baseinfo> selectBaseinfoListByRoles(Baseinfo baseinfo) {
        SysUser sysUser = SecurityUtils.getLoginUser().getUser();
        List<SysRole> roles = sysUser.getRoles();
        StringBuilder sqlString = new StringBuilder();
        if (StringUtils.isNotNull(sysUser) && !sysUser.isAdmin()) {
            for (SysRole role : roles) {
                String dataScope = role.getDataScope();
                if (TENANT_SCOPE_ALL.equals(dataScope)) {
                    break;
                } else if (TENANT_SCOPE_TENANT.equals(dataScope)) {
                    sqlString.append(StringUtils.format(" AND tenant_id = {} ", sysUser.getTenantId()));
                } else {
                    sqlString.append(StringUtils.format(" AND dept_id = {}", sysUser.getBaseId()));
                }
            }
        }
        baseinfo.getParams().put("sql", sqlString.toString());
        return baseinfoMapper.selectBaseinfoListByRoles(baseinfo);
    }

    /**
     * 根据角色查询基地信息包含用户
     * @param baseinfo
     * @return
     */
    @Override
    public List<Baseinfo> selectBaseinfoAndUsersListByRoles(Baseinfo baseinfo) {
        SysUser sysUser = SecurityUtils.getLoginUser().getUser();
        List<SysRole> roles = sysUser.getRoles();
        StringBuilder sqlString = new StringBuilder();
        if (StringUtils.isNotNull(sysUser) && !sysUser.isAdmin()) {
            for (SysRole role : roles) {
                String dataScope = role.getDataScope();
                if (TENANT_SCOPE_ALL.equals(dataScope)) {
                    break;
                } else if (TENANT_SCOPE_TENANT.equals(dataScope)) {
                    sqlString.append(StringUtils.format(" AND tenant_id = {} ", sysUser.getTenantId()));
                } else {
                    sqlString.append(StringUtils.format(" AND dept_id = {}", sysUser.getBaseId()));
                }
            }
        }
        baseinfo.getParams().put("sql", sqlString.toString());
        return baseinfoMapper.selectBaseinfoAndUsersListByRoles(baseinfo);
    }

    /**
     * 新增基地信息
     * 
     * @param baseinfo 基地信息
     * @return 结果
     */
    @Override
    public int insertBaseinfo(Baseinfo baseinfo)
    {
        baseinfo.setCreateTime(DateUtils.getNowDate());
        return baseinfoMapper.insertBaseinfo(baseinfo);
    }

    /**
     * 修改基地信息
     * 
     * @param baseinfo 基地信息
     * @return 结果
     */
    @Override
    public int updateBaseinfo(Baseinfo baseinfo)
    {
        baseinfo.setUpdateTime(DateUtils.getNowDate());
        return baseinfoMapper.updateBaseinfo(baseinfo);
    }

    /**
     * 批量删除基地信息
     * 
     * @param baseIds 需要删除的基地信息主键
     * @return 结果
     */
    @Override
    public int deleteBaseinfoByBaseIds(Long[] baseIds)
    {
        return baseinfoMapper.deleteBaseinfoByBaseIds(baseIds);
    }

    /**
     * 删除基地信息信息
     * 
     * @param baseId 基地信息主键
     * @return 结果
     */
    @Override
    public int deleteBaseinfoByBaseId(Long baseId)
    {
        return baseinfoMapper.deleteBaseinfoByBaseId(baseId);
    }
}
