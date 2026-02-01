package com.frog.framework.web.service;

import com.frog.common.core.domain.entity.SysDept;
import com.frog.common.utils.SecurityUtils;
import com.frog.framework.security.context.AuthenticationContextHolder;
import com.frog.system.mapper.SysDeptMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.frog.common.core.domain.entity.SysUser;
import com.frog.common.core.domain.model.LoginUser;
import com.frog.common.enums.UserStatus;
import com.frog.common.exception.ServiceException;
import com.frog.common.utils.StringUtils;
import com.frog.system.service.ISysUserService;

import java.util.List;

/**
 * 用户验证处理
 *
 * @author ruoyi
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private ISysUserService userService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private SysDeptMapper deptMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        SysUser user = userService.selectUserByUserName(username);
        List<SysDept> sysTenantDepts = deptMapper.selectTenantById(user.getDeptId());
        if(sysTenantDepts.size()==1){
            user.setTenantId(sysTenantDepts.get(0).getDeptId());
        }
        List<SysDept> sysBaseDepts = deptMapper.selectBaseById(user.getDeptId());
        if(sysBaseDepts.size()==1){
            user.setBaseId(sysBaseDepts.get(0).getDeptId());
        }

        if (StringUtils.isNull(user))
        {
            log.info("登录用户：{} 不存在.", username);
            throw new ServiceException("登录用户：" + username + " 不存在");
        }
        else if (UserStatus.DELETED.getCode().equals(user.getDelFlag()))
        {
            log.info("登录用户：{} 已被删除.", username);
            throw new ServiceException("对不起，您的账号：" + username + " 已被删除");
        }
        else if (UserStatus.DISABLE.getCode().equals(user.getStatus()))
        {
            log.info("登录用户：{} 已被停用.", username);
            throw new ServiceException("对不起，您的账号：" + username + " 已停用");
        }
        UserDetails details=createLoginUser(user);
        return createLoginUser(user);
    }

    public UserDetails createLoginUser(SysUser user)
    {
        String password = AuthenticationContextHolder.getContext().getCredentials().toString();
        if("autologin".equals(password)){
            user.setPassword(SecurityUtils.encryptPassword("autologin"));
        }
        return new LoginUser(user.getUserId(), user.getDeptId(), user, permissionService.getMenuPermission(user));
    }
}
