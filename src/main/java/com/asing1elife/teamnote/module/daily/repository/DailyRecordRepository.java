package com.asing1elife.teamnote.module.daily.repository;

import com.asing1elife.teamnote.core.repository.BaseRepository;
import com.asing1elife.teamnote.model.DailyRecordModel;

import java.util.List;

public interface DailyRecordRepository extends BaseRepository<DailyRecordModel, Long> {

    /**
     * 获取指定日志所有记录
     */
    List<DailyRecordModel> findByDailyIdOrderByDayAsc(long dailyId);

    /**
     * 获取指定日期的日志记录
     */
    DailyRecordModel getByDailyIdAndDay(long dailyId, int day);

}
