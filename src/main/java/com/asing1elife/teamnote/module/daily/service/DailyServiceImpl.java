package com.asing1elife.teamnote.module.daily.service;

import com.asing1elife.teamnote.core.service.BaseService;
import com.asing1elife.teamnote.core.util.DateUtil;
import com.asing1elife.teamnote.model.DailyModel;
import com.asing1elife.teamnote.model.DailyRecordModel;
import com.asing1elife.teamnote.model.OrganizationModel;
import com.asing1elife.teamnote.module.daily.repository.DailyRepository;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DailyServiceImpl extends BaseService<DailyModel, DailyRepository> {

    @Autowired
    private DailyRecordServiceImpl dailyRecordService;

    @Transactional
    public Map<Integer, List<DailyModel>> getDailiesByYear(long organizationId) {
        // 获取或保存日志及日志记录
        getOrSaveDailyAndRecord(organizationId);

        // 获取所有日志
        return generateDailiesOfYear(organizationId);
    }

    /**
     * 获取或保存日志及日志记录
     */
    public DailyRecordModel getOrSaveDailyAndRecord(long organizationId) {
        // 尝试获取当前年当前月的日志
        DailyModel currentMonthDaily = repository.getByOrganization_IdAndYearAndMonth(organizationId, DateUtil.getCurrentYear(), DateUtil.getCurrentMonth());

        // 没有则创建
        if (currentMonthDaily == null) {
            currentMonthDaily = saveDaily(organizationId);
        }

        // 尝试获取当天的日志，不存在则内部创建
        return dailyRecordService.checkOrSaveDailyRecord(currentMonthDaily);
    }

    private Map<Integer, List<DailyModel>> generateDailiesOfYear(long organizationId) {
        Map<Integer, List<DailyModel>> dailyMap = Maps.newHashMap();

        // 获取按照创建时间排序的日志列表
        List<DailyModel> dailies = getDailiesOrderByYearAndMonth(organizationId);

        // 将日志按照年份划分
        dailies.forEach(daily -> {
            if (dailyMap.get(daily.getYear()) != null) {
                dailyMap.get(daily.getYear()).add(daily);
            } else {
                ArrayList<DailyModel> children = Lists.newArrayList();
                children.add(daily);

                dailyMap.put(daily.getYear(), children);
            }
        });

        return dailyMap;
    }

    /**
     * 获取按照创建时间排序的日志列表
     */
    private List<DailyModel> getDailiesOrderByYearAndMonth(Long organizationId) {
        return repository.findByAndOrganization_IdOrderByYearAscMonthAsc(organizationId);
    }

    /**
     * 保存日志
     */
    private DailyModel saveDaily(long organizationId) {
        DailyModel daily = new DailyModel();
        daily.setOrganization(new OrganizationModel(organizationId));
        daily.setYear(DateUtil.getCurrentYear());
        daily.setMonth(DateUtil.getCurrentMonth());

        return super.save(daily);
    }

}
