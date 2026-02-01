package com.frog.agriculture.mapper;

import java.util.HashMap;
import java.util.List;

public interface DataStatisticsMapper {

    public List<HashMap> selectBaseInfo(Long baseId);
    public List<HashMap> selectDeviceInfo(Long baseId);
    public List<HashMap> selectDeviceAlert(Long baseId);
    public List<HashMap> selectTaskInfo(Long baseId);
    public List<HashMap> selectTaskInfoOfMine(Long baseId);
    public List<HashMap> selectBatchInfo(Long batchHead);
    //根据batchHead查询今日待完成任务
    public HashMap selectToadyTaskCountByTaskHead(Long batchHead);

}
