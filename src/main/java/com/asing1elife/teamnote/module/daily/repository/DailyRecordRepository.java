package com.asing1elife.teamnote.module.daily.repository;

import com.asing1elife.teamnote.core.repository.BaseRepository;
import com.asing1elife.teamnote.model.DailyRecordModel;

import java.util.List;

public interface DailyRecordRepository extends BaseRepository<DailyRecordModel, Long> {

    /**
     * 获取指定日志所有记录
     */
    List<DailyRecordModel> findByDaily_IdOrderByDayAsc(long dailyId);

    /**
     * 获取指定月份加班的日志记录
     */
    List<DailyRecordModel> findByDaily_IdAndExtraIsTrue(long dailyId);

    /**
     * 获取指定日期的日志记录
     */
    DailyRecordModel getByDaily_IdAndDay(long dailyId, int day);

}
