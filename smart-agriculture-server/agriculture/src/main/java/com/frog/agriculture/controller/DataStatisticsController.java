package com.frog.agriculture.controller;

import com.frog.agriculture.service.IDataStatisticsService;
import com.frog.trace.service.ITraceRecordService;
import com.frog.common.core.controller.BaseController;
import com.frog.common.core.domain.AjaxResult;
import com.frog.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@Api(tags = "agri-统计分析")
@RestController
@RequestMapping("/agriculture/statistics")
public class DataStatisticsController extends BaseController {

    @Autowired
    private IDataStatisticsService dataStatisticsService;
    @Autowired
    private ITraceRecordService traceRecordService;

    /**
     * 统计基地信息
     */
    @ApiOperation("统计基地信息-大屏和工作台使用")
    @GetMapping("/selectBaseInfo/{baseId}")
    public TableDataInfo selectBaseInfo(@PathVariable Long baseId){
        List<HashMap> list = dataStatisticsService.selectBaseInfo(baseId);
        return getDataTable(list);
    }
    /**
     * 统计设备信息
     */
    @ApiOperation("统计设备信息")
    @GetMapping("/selectDeviceInfo/{baseId}")
    public TableDataInfo selectDeviceInfo(@PathVariable Long baseId){
        List<HashMap> list = dataStatisticsService.selectDeviceInfo(baseId);
        return getDataTable(list);
    }
    /**
     * 统计设备定时任务信息
     */
    @ApiOperation("统计设备数据上报信息")
    @GetMapping("/selectDeviceLog/{baseId}")
    public TableDataInfo selectDeviceJobInfo(@PathVariable Long baseId){
        List<HashMap> list = dataStatisticsService.selectDeviceAlert(baseId);
        return getDataTable(list);
    }
    /**
     * 统计农事任务信息
     */
    @ApiOperation("统计农事任务信息")
    @GetMapping("/selectTaskInfo/{baseId}")
    public TableDataInfo selectTaskInfo(@PathVariable Long baseId){
        List<HashMap> list = dataStatisticsService.selectTaskInfo(baseId);
        return getDataTable(list);
    }
    /**
     * 统计农事任务信息
     */
    @ApiOperation("统计分配给我的农事任务信息")
    @GetMapping("/selectTaskInfoOfMine")
    public TableDataInfo selectTaskInfoOfMine(){
        List<HashMap> list = dataStatisticsService.selectTaskInfoOfMine();
        return getDataTable(list);
    }
    /**
     * 统计种植面积信息
     */
    @ApiOperation("统计批次信息")
    @GetMapping("/selectBatchInfo/{baseId}")
    public TableDataInfo selectAreaInfo(@PathVariable Long baseId){
        List<HashMap> list = dataStatisticsService.selectBatchInfo(baseId);
        return getDataTable(list);
    }

    /**
     * 查询今日待办任务
     */
    @ApiOperation("查询今日待办任务")
    @GetMapping("/selectToadyTaskCountByTaskHead")
    public AjaxResult selectToadyTaskCountByTaskHead(){
        HashMap data = dataStatisticsService.selectToadyTaskCountByTaskHead();
        return AjaxResult.success(data);
    }
}
