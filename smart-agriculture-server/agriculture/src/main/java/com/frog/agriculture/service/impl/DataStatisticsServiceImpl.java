package com.frog.agriculture.service.impl;

import com.frog.agriculture.mapper.DataStatisticsMapper;
import com.frog.agriculture.service.IDataStatisticsService;
import com.frog.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
@Service
public class DataStatisticsServiceImpl implements IDataStatisticsService {

    @Autowired
    private DataStatisticsMapper dataStatisticsMapper;
    //工作台
    public List<HashMap> selectBaseInfo(Long baseId){
        return dataStatisticsMapper.selectBaseInfo(baseId);
    }
    public List<HashMap> selectDeviceInfo(Long baseId){
        return dataStatisticsMapper.selectDeviceInfo(baseId);
    }
    public List<HashMap> selectDeviceAlert(Long baseId){
        return dataStatisticsMapper.selectDeviceAlert(baseId);
    }
    public List<HashMap> selectTaskInfo(Long baseId){
        return  dataStatisticsMapper.selectTaskInfo(baseId);
    }
    public List<HashMap> selectTaskInfoOfMine(){
        return  dataStatisticsMapper.selectTaskInfoOfMine(SecurityUtils.getUserId());
    }
    public List<HashMap> selectBatchInfo(Long baseId){
        return  dataStatisticsMapper.selectBatchInfo(baseId);
    }
    //根据batchHead查询今日待完成任务
    @Override
    public HashMap selectToadyTaskCountByTaskHead() {

        return dataStatisticsMapper.selectToadyTaskCountByTaskHead(SecurityUtils.isAdmin(SecurityUtils.getUserId())?null:SecurityUtils.getUserId());
    }
}
