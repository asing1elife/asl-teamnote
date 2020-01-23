package com.asing1elife.teamnote.module.daily.service;

import com.asing1elife.teamnote.core.service.BaseService;
import com.asing1elife.teamnote.core.util.DateUtil;
import com.asing1elife.teamnote.model.DailyModel;
import com.asing1elife.teamnote.model.DailyRecordModel;
import com.asing1elife.teamnote.module.daily.repository.DailyRecordRepository;
import com.asing1elife.teamnote.module.task.service.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DailyRecordServiceImpl extends BaseService<DailyRecordModel, DailyRecordRepository> {

    @Autowired
    private TaskServiceImpl taskService;

    /**
     * 获取指定日志所有记录
     */
    public List<DailyRecordModel> getDailyRecordsByDaily(long dailyId) {
        return repository.findByDailyIdOrderByDayAsc(dailyId);
    }

    /**
     * 检查当天日志，不存在则创建
     */
    @Transactional
    public DailyRecordModel checkOrSaveDailyRecord(DailyModel daily, long organizationId) {
        // 获取当天的日期
        int currentDay = DateUtil.getCurrentDay();

        // 获取当天日志
        DailyRecordModel dailyRecord = repository.getByDailyIdAndDay(daily.getId(), currentDay);

        // 不存在则创建
        if (dailyRecord == null) {
            dailyRecord = saveDailyRecord(daily, organizationId, currentDay);
        }

        return dailyRecord;
    }

    /**
     * 更新日志记录关联任务
     */
    public void updateDailyRecordTask(DailyRecordModel record) {
        super.save(record);
    }

    /**
     * 更新日志记录加班标识
     */
    public void updateDailyRecordExtra(long recordId, boolean isExtra) {
        DailyRecordModel dailyRecord = super.get(recordId);
        dailyRecord.setExtra(isExtra);

        super.save(dailyRecord);
    }

    /**
     * 更新日志记录还班标识
     */
    public void updateDailyRecordRepay(long recordId, boolean isRepay) {
        DailyRecordModel dailyRecord = super.get(recordId);
        dailyRecord.setRepay(isRepay);

        super.save(dailyRecord);
    }

    /**
     * 更新日志记录休息标识
     */
    @Transactional
    public void updateDailyRecordRest(long recordId, boolean isRest) {
        DailyRecordModel dailyRecord = super.get(recordId);
        dailyRecord.setRest(isRest);

        super.save(dailyRecord);
    }

    /**
     * 保存日志记录
     */
    private DailyRecordModel saveDailyRecord(DailyModel daily, long organizationId, int currentDay) {
        DailyRecordModel dailyRecord = new DailyRecordModel();
        dailyRecord.setDaily(daily);
        dailyRecord.setDay(currentDay);
        // 每次新建日志记录时，将之前所有处于进行中的任务都加入到当天的日志中
        dailyRecord.setTasks(taskService.findByImplTasks(organizationId));

        super.save(dailyRecord);

        return dailyRecord;
    }

}
