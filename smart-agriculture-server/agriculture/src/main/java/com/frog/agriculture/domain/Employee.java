package com.frog.agriculture.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.frog.common.annotation.Excel;
import com.frog.common.core.domain.BaseEntity;

/**
 * 雇员对象 agriculture_employee
 * 
 * @author nealtsiao
 * @date 2023-05-13
 */
@Data
public class Employee extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 雇员ID */
    private Long employeeId;

    /** 编码 */
    @Excel(name = "编码")
    private String employeeCode;

    /** 姓名 */
    @Excel(name = "姓名")
    private String employeeName;

    /** 字典 agriculture_employee_type */
    @Excel(name = "字典 agriculture_employee_type")
    private String employeeType;

    /** 手机号码 */
    @Excel(name = "手机号码")
    private String employeeTel;

    /** 字典 sys_user_sex */
    @Excel(name = "字典 sys_user_sex")
    private String employeeSex;

    /** 地址 */
    @Excel(name = "地址")
    private String employeeAddress;

    /** 状态 */
    private String status;

    /** 排序 */
    private Long orderNum;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 租户ID */
    private Long tenantId;

    /** 基地ID */
    private Long baseId;

    /** 用户部门ID */
    private Long deptId;

    /** 用户ID */
    private Long userId;
}
