package com.asing1elife.teamnote.module.daily.repository;

import com.asing1elife.teamnote.core.repository.BaseRepository;
import com.asing1elife.teamnote.model.DailyModel;

import java.util.List;

public interface DailyRepository extends BaseRepository<DailyModel, Long> {

    /**
     * 获取指定机构日志
     */
    List<DailyModel> findByOrganizationIdOrderByYearAscMonthAsc(long organizationId);

    /**
     * 获取指定机构指定年份所有日志
     */
    List<DailyModel> findByOrganizationIdAndYear(long organizationId, int year);

    /**
     * 获取指定机构指定年月的日志
     */
    DailyModel getByOrganizationIdAndYearAndMonth(long organizationId, int year, int month);

}
