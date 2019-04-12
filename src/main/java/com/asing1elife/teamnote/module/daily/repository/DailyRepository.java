package com.asing1elife.teamnote.module.daily.repository;

import com.asing1elife.teamnote.core.repository.BaseRepository;
import com.asing1elife.teamnote.model.DailyModel;

public interface DailyRepository extends BaseRepository<DailyModel, Long> {

    /**
     * 获取指定月份的日志
     */
    DailyModel getByYearAndMonth(int year, int month);

}
