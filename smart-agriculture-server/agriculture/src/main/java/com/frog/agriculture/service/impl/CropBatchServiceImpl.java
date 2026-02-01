package com.frog.agriculture.service.impl;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.frog.agriculture.domain.BatchTask;
import com.frog.agriculture.domain.StandardJob;
import com.frog.agriculture.domain.TaskLog;
import com.frog.agriculture.mapper.BatchTaskMapper;
import com.frog.agriculture.mapper.TaskLogMapper;
import com.frog.common.annotation.TenantScope;
import com.frog.common.core.domain.entity.SysRole;
import com.frog.common.core.domain.entity.SysUser;
import com.frog.common.utils.DateUtils;
import com.frog.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.frog.agriculture.mapper.CropBatchMapper;
import com.frog.agriculture.mapper.StandardJobMapper;
import com.frog.agriculture.domain.CropBatch;
import com.frog.agriculture.service.ICropBatchService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 作物批次Service业务层处理
 * 
 * @author nealtsiao
 * @date 2023-05-13
 */
@Service
public class CropBatchServiceImpl implements ICropBatchService 
{
    @Autowired
    private CropBatchMapper cropBatchMapper;
    @Autowired
    private StandardJobMapper standardJobMapper;
    @Autowired
    private BatchTaskMapper batchTaskMapper;
    @Autowired
    private TaskLogMapper taskLogMapper;

    /**
     * 查询作物批次
     * 
     * @param batchId 作物批次主键
     * @return 作物批次
     */
    @Override
    public CropBatch selectCropBatchByBatchId(Long batchId)
    {
        return cropBatchMapper.selectCropBatchByBatchId(batchId);
    }

    /**
     * 查询作物批次列表
     * 
     * @param cropBatch 作物批次
     * @return 作物批次
     */
    @Override
    @TenantScope(tenantAlias = "b",baseAlias = "b",deptAlias = "b",userAlias = "b")
    public List<CropBatch> selectCropBatchList(CropBatch cropBatch)
    {
        return cropBatchMapper.selectCropBatchList(cropBatch);
    }
    /**
     * 查询自己作物批次列表
     *
     * @param cropBatch 作物批次
     * @return 作物批次
     */
    @Override
    @TenantScope(tenantAlias = "b",baseAlias = "b",deptAlias = "b",userAlias = "b")
    public List<CropBatch> selectCropBatchListOfMine(CropBatch cropBatch) {
        cropBatch.setBatchHead(SecurityUtils.getUserId());
        return cropBatchMapper.selectCropBatchList(cropBatch);
    }

    /**
     * 新增作物批次
     * 
     * @param cropBatch 作物批次
     * @return 结果
     */
    @Override
    @Transactional
    public int insertCropBatch(CropBatch cropBatch)
    {
        cropBatch.setCreateTime(DateUtils.getNowDate());
        cropBatch.setCreateBy(SecurityUtils.getUserId().toString());
        cropBatch.setTenantId(SecurityUtils.getTenantId());
        cropBatch.setBaseId(SecurityUtils.getBaseId());
        cropBatch.setDeptId(SecurityUtils.getDeptId());
        cropBatch.setUserId(SecurityUtils.getUserId());
        int i =cropBatchMapper.insertCropBatch(cropBatch);
        //创建工作
        StandardJob standardJob = new StandardJob();
        standardJob.setGermplasmId(cropBatch.getGermplasmId());
        List<StandardJob> sjList = standardJobMapper.selectStandardJobList(standardJob);
        for(StandardJob sj : sjList){
            BatchTask bt = new BatchTask();
            bt.setBatchId(cropBatch.getBatchId());
            bt.setTaskHead(cropBatch.getBatchHead());
            bt.setTaskName(sj.getJobName());

            int mult = "0".equals(sj.getCycleUnit())?1:7;//0是天，1是周
            try {
                if(mult==7) {
                    bt.setPlanStart(DateUtils.plusDay((int) ((sj.getJobStart()-1) * mult), cropBatch.getStartTime()));
                    bt.setPlanFinish(DateUtils.plusDay((int) (sj.getJobFinish() * mult - 1), cropBatch.getStartTime()));
                }else if(mult==1){
                    bt.setPlanStart(DateUtils.plusDay((int) ((sj.getJobStart()-1) * mult), cropBatch.getStartTime()));
                    bt.setPlanFinish(DateUtils.plusDay((int) (sj.getJobFinish() * mult - 1), cropBatch.getStartTime()));
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            bt.setCreateBy(SecurityUtils.getUserId().toString());
            bt.setCreateTime(DateUtils.getNowDate());
            batchTaskMapper.insertBatchTask(bt);
            TaskLog tl = new TaskLog();
            tl.setTaskId(bt.getTaskId());
            tl.setOperName(SecurityUtils.getUsername());
            tl.setOperId(SecurityUtils.getUserId());
            tl.setOperDes("创建任务");
            tl.setCreateTime(DateUtils.getNowDate());
            taskLogMapper.insertTaskLog(tl);
        }
        return i;

    }

    /**
     * 修改作物批次
     * 
     * @param cropBatch 作物批次
     * @return 结果
     */
    @Override
    public int updateCropBatch(CropBatch cropBatch)
    {
        cropBatch.setUpdateTime(DateUtils.getNowDate());
        cropBatch.setUpdateBy(SecurityUtils.getUserId().toString());
        return cropBatchMapper.updateCropBatch(cropBatch);
    }

    /**
     * 删除作物批次信息
     *
     * @param batchId 作物批次主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteCropBatchByBatchId(Long batchId)
    {
        BatchTask batchTask = new BatchTask();
        batchTask.setBatchId(batchId);
        List<BatchTask> batchTasks = batchTaskMapper.selectBatchTaskList(batchTask);
        for(BatchTask b : batchTasks){
            if(!b.getStatus().equals("0")){
                throw new RuntimeException("存在已经处理的任务，批次不能删除");
            }
        }
        batchTaskMapper.deleteBatchTaskByBatchId(batchId);
        return cropBatchMapper.deleteCropBatchByBatchId(batchId);
    }

    /**
     * 给手机端批次列表查询数据
     * @param cropBatch
     * @return
     */
    @Override
    public List<HashMap> selectCropBatchListToMobile(CropBatch cropBatch) {
        cropBatch.setBatchHead(SecurityUtils.getUserId());
        return cropBatchMapper.selectCropBatchListToMobile(cropBatch);
    }
}
