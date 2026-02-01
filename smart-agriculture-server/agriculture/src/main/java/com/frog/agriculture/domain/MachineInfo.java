package com.frog.agriculture.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.frog.common.annotation.Excel;
import com.frog.common.core.domain.BaseEntity;

/**
 * 机械信息对象 agriculture_machine_info
 * 
 * @author xuweidong
 * @date 2023-05-24
 */
@Data
public class MachineInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 机械ID */
    private Long machineId;

    /** 机械编码 */
    @Excel(name = "机械编码")
    private String machineCode;

    /** 机械名称 */
    @Excel(name = "机械名称")
    private String machineName;

    /** 机械类别 */
    @Excel(name = "机械类别")
    private Long machineTypeId;

    /** 计量单位 */
    @Excel(name = "计量单位")
    private String measureUnit;

    /** 状态 */
    @Excel(name = "状态")
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
