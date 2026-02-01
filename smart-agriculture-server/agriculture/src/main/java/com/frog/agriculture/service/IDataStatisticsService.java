package com.frog.agriculture.service;

import java.util.HashMap;
import java.util.List;

public interface IDataStatisticsService {
    public List<HashMap> selectBaseInfo(Long baseId);
    public List<HashMap> selectDeviceInfo(Long baseId);
    public List<HashMap> selectDeviceAlert(Long baseId);
    public List<HashMap> selectTaskInfo(Long baseId);
    public List<HashMap> selectTaskInfoOfMine();
    public List<HashMap> selectBatchInfo(Long baseId);
    public HashMap selectToadyTaskCountByTaskHead();

}
