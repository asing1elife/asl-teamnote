package com.asing1elife.teamnote.module.daily.repository;

import com.asing1elife.teamnote.core.repository.BaseRepository;
import com.asing1elife.teamnote.model.DailyRecordModel;

import java.util.List;

public interface DailyRecordRepository extends BaseRepository<DailyRecordModel, Long> {

    /**
     * 获取指定日志所有记录
     */
    List<DailyRecordModel> findByDaily_IdOrderByDayAsc(Long dailyId);

    /**
     * 获取指定日期的日志记录
     */
    DailyRecordModel getByDaily_IdAndDay(Long dailyId, int day);

}
