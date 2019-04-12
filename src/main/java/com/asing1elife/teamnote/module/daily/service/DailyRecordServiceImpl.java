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
    public List<DailyRecordModel> getDailyRecordsByDaily(Long dailyId) {
        return repository.findByDaily_IdOrderByDayAsc(dailyId);
    }

    /**
     * 检查当天日志，不存在则创建
     */
    @Transactional
    public DailyRecordModel checkOrSaveDailyRecord(DailyModel daily) {
        // 获取当天的日期
        int currentDay = DateUtil.getCurrentDay();

        // 获取当天日志
        DailyRecordModel dailyRecord = repository.getByDaily_IdAndDay(daily.getId(), currentDay);

        // 不存在则创建
        if (dailyRecord == null) {
            dailyRecord = saveDailyRecord(daily, currentDay);
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
    public void updateDailyRecordExtra(Long recordId, Boolean isExtra) {
        DailyRecordModel dailyRecord = super.getOne(recordId);
        dailyRecord.setExtra(isExtra);

        super.save(dailyRecord);
    }

    /**
     * 更新日志记录还班标识
     */
    public void updateDailyRecordRepay(Long recordId, Boolean isRepay) {
        DailyRecordModel dailyRecord = super.getOne(recordId);
        dailyRecord.setRepay(isRepay);

        super.save(dailyRecord);
    }

    /**
     * 保存日志记录
     */
    private DailyRecordModel saveDailyRecord(DailyModel daily, int currentDay) {
        DailyRecordModel dailyRecord = new DailyRecordModel();
        dailyRecord.setDaily(daily);
        dailyRecord.setDay(currentDay);
        // 每次新建日志记录时，将之前所有处于进行中的任务都加入到当天的日志中
        dailyRecord.setTasks(taskService.findByImplTasks());

        super.save(dailyRecord);

        return dailyRecord;
    }

}
