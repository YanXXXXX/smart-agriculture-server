package com.frog.agriculture.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.frog.common.annotation.Excel;
import com.frog.common.core.domain.BaseEntity;

/**
 * 种质对象 agriculture_germplasm
 * 
 * @author nealtsiao
 * @date 2023-05-13
 */
@Data
public class Germplasm extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 种质ID */
    private Long germplasmId;

    /** 作物名称 */
    @Excel(name = "作物名称")
    private String cropName;

    /** 作物英文名称 */
    @Excel(name = "作物英文名称")
    private String cropEnName;

    /** 种质名称 */
    @Excel(name = "种质名称")
    private String germplasmName;

    /** 种质英文名称 */
    @Excel(name = "种质英文名称")
    private String germplasmEnName;

    /** 种质图片 */
    @Excel(name = "种质图片")
    private String germplasmImg;

    /** 宣传语 */
    @Excel(name = "宣传语")
    private String germplasmDes;

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
