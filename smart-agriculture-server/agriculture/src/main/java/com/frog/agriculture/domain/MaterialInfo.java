package com.frog.agriculture.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.frog.common.annotation.Excel;
import com.frog.common.core.domain.BaseEntity;

/**
 * 农资信息对象 agriculture_material_info
 * 
 * @author xuweidong
 * @date 2023-05-24
 */
@Data
public class MaterialInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 农资ID */
    private Long materialId;

    /** 农资编码 */
    @Excel(name = "农资编码")
    private String materialCode;

    /** 农资名称 */
    @Excel(name = "农资名称")
    private String materialName;

    /** 农资类别 */
    @Excel(name = "农资类别")
    private Long materialTypeId;

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
