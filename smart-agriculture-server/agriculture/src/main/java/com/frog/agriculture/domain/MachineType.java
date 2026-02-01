package com.frog.agriculture.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.frog.common.annotation.Excel;
import com.frog.common.core.domain.BaseEntity;

/**
 * 机械类别对象 agriculture_machine_type
 * 
 * @author kerwincui
 * @date 2023-05-24
 */
@Data
public class MachineType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 机械类别ID */
    private Long machineTypeId;

    /** 机械类别名称 */
    @Excel(name = "机械类别名称")
    private String machineTypeName;

    /** 状态 */
    private String status;

    /** 排序 */
    @Excel(name = "排序")
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
