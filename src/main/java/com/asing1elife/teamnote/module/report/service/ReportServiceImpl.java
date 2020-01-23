/*****************************************************************************
 * Copyright (c) 2020, www.shixun.online
 *
 * All rights reserved
 *
 *****************************************************************************/
package com.asing1elife.teamnote.module.report.service;

import com.asing1elife.teamnote.core.exception.CustomException;
import com.asing1elife.teamnote.core.service.BaseService;
import com.asing1elife.teamnote.core.util.DateUtil;
import com.asing1elife.teamnote.model.*;
import com.asing1elife.teamnote.model.dictionary.ReportType;
import com.asing1elife.teamnote.model.dictionary.TaskStatus;
import com.asing1elife.teamnote.module.daily.service.DailyServiceImpl;
import com.asing1elife.teamnote.module.report.repository.ReportRepository;
import com.asing1elife.teamnote.module.task.service.TaskServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl extends BaseService<ReportModel, ReportRepository> {

    @Autowired
    private TaskServiceImpl taskService;

    @Autowired
    private DailyServiceImpl dailyService;

    /**
     * 获取指定组织指定类型报告
     */
    public ReportModel getReportByDailyAndType(long dailyId, String realCode) {
        DailyModel daily = dailyService.get(dailyId);
        Long organizationId = daily.getOrganization().getId();

        // 获取有效的类型
        ReportType type = ReportType.RETY_Year.getRealCode().equalsIgnoreCase(realCode) ? ReportType.RETY_Year : ReportType.RETY_Month;

        // 尝试获取报告
        ReportModel report = repository.getReportByOrOrganizationIdAndDailyIdAndTypeCode(organizationId, dailyId, type.getCode());

        if (report != null) {
            return report;
        }

        // 生成新的报告
        report = new ReportModel(daily);

        // 根据类型决定是生成年度报告还是月度报告
        if (ReportType.RETY_Year.equals(type)) {
            // 年度
            generateYearReport(report);
        } else {
            // 月度
            generateMonthReport(report);
        }

        // 保存数据
        super.save(report);

        return report;
    }

    /**
     * 生成年报
     */
    private void generateYearReport(ReportModel report) {
        int year = report.getDaily().getYear();

        int currentYear = DateUtil.getCurrentYear();

        if (currentYear <= year) {
            throw new CustomException("今年还没有结束，无法生成年报");
        }

        report.setName(String.format("%s - %s - 年度报告", report.getOrganization().getName(), year));

        // 包装项目任务相关数据
        wrapProjectTaskData(report);

        // 包装日志相关数据
        wrapDailyRecordData(report);
    }

    /**
     * 包装项目任务相关数据
     */
    private void wrapProjectTaskData(ReportModel report) {
        long organizationId = report.getOrganization().getId();
        int year = report.getDaily().getYear();

        int taskNum;
        int taskFinishNum = 0;
        int projectNum;
        String taskTagMemo;

        // 获取该组织该年度所有任务
        List<TaskModel> tasks = taskService.getTasksByOrganizationAndYear(organizationId, year);

        // 将任务按项目区分
        Map<ProjectModel, List<TaskModel>> projectMap = Maps.newHashMap();

        // 将任务按标签区分
        Map<TaskTagModel, List<TaskModel>> taskTagMap = Maps.newHashMap();

        // 任务总数
        taskNum = tasks.size();

        for (TaskModel task : tasks) {
            String taskStatus = task.getStatus().getCode();
            ProjectModel project = task.getProject();
            TaskTagModel taskTag = task.getTaskTag();

            // 累计任务完成数量
            if (taskStatus.equals(TaskStatus.TAST_Finish.getCode())) {
                taskFinishNum++;
            }

            // 按项目区分
            if (projectMap.containsKey(project)) {
                projectMap.get(project).add(task);
            } else {
                projectMap.put(project, Lists.newArrayList(task));
            }

            // 按任务标签区分
            if (taskTagMap.containsKey(taskTag)) {
                taskTagMap.get(taskTag).add(task);
            } else {
                taskTagMap.put(taskTag, Lists.newArrayList(task));
            }
        }

        // 项目数量
        projectNum = projectMap.size();
        // 任务标签备注
        taskTagMemo = wrapTaskTagMemo(taskTagMap);

        report.setTaskNum(taskNum);
        report.setTaskFinishNum(taskFinishNum);
        report.setProjectNum(projectNum);
        report.setTaskTagMemo(taskTagMemo);
    }

    /**
     * 包装任务标签备注
     */
    private String wrapTaskTagMemo(Map<TaskTagModel, List<TaskModel>> taskTagMap) {
        StringBuilder memo = new StringBuilder();

        for (TaskTagModel taskTag : taskTagMap.keySet()) {
            // 获取该标签所有任务
            List<TaskModel> tasks = taskTagMap.get(taskTag);

            memo.append(String.format("[ %s ] 类任务共计 %s 个，", taskTag.getName(), tasks.size()));
            memo.append(String.format("已完成 %s 个", calcTaskFinishNumByTaskTag(tasks)));
            memo.append("<br>");
        }

        return memo.toString();
    }

    /**
     * 计算指定标签任务列表的完成数
     */
    private int calcTaskFinishNumByTaskTag(List<TaskModel> tasks) {
        int taskFinishNum = 0;

        for (TaskModel task : tasks) {
            if (task.getStatus().getCode().equalsIgnoreCase(TaskStatus.TAST_Finish.getCode())) {
                taskFinishNum++;
            }
        }

        return taskFinishNum;
    }

    /**
     * 包装日志相关数据
     */
    private void wrapDailyRecordData(ReportModel report) {
        long organizationId = report.getOrganization().getId();
        int year = report.getDaily().getYear();

        int dayNum = 0;
        int dayRestNum = 0;
        int dayExtraNum = 0;

        // 月度详细数据
        int monthDayMaxNum = 0;
        int monthDayMinNum = 0;
        String monthDayMaxMemo = "";
        String montyDayMinMemo = "";

        // 日期详细数据
        int dayTaskMaxNum = 0;
        String dayMemo = "";

        // 获取指定组织指定年份所有日志
        List<DailyModel> dailies = dailyService.getDailiesByOrganizationAndYear(organizationId, year);

        for (DailyModel daily : dailies) {
            List<DailyRecordModel> dailyRecords = daily.getDailyRecords();

            int nowMonthDayNum = dailyRecords.size();

            // 记录工作时间最长的天数
            if (monthDayMaxNum < nowMonthDayNum) {
                monthDayMaxNum = nowMonthDayNum;
                // 包装详细描述
                monthDayMaxMemo = wrapMonthMemo(daily, true);
            }

            // 计算工作时间最短的天数
            if (monthDayMinNum > nowMonthDayNum || monthDayMinNum == 0) {
                monthDayMinNum = nowMonthDayNum;
                // 包装详细描述
                montyDayMinMemo = wrapMonthMemo(daily, false);
            }

            // 天数累加
            dayNum += nowMonthDayNum;

            for (DailyRecordModel dailyRecord : dailyRecords) {
                List<TaskModel> tasks = dailyRecord.getTasks();

                int nowDayTaskNum = tasks.size();

                // 记录任务最多的天数
                if (dayTaskMaxNum < nowDayTaskNum) {
                    dayTaskMaxNum = nowDayTaskNum;
                    // 包装详细描述
                    dayMemo = wrapDayMemo(dailyRecord);
                }

                // 休息日累加
                if (dailyRecord.getRest()) {
                    dayRestNum++;
                }

                // 加班日累加
                if (dailyRecord.getExtra()) {
                    dayExtraNum++;
                }
            }
        }

        // 工作天数修正，需要减去休息日
        dayNum -= dayRestNum;

        report.setDayNum(dayNum);
        report.setDayExtraNum(dayExtraNum);
        report.setMonthMemo(String.format("%s<br>%s", monthDayMaxMemo, montyDayMinMemo));
        report.setDayMemo(dayMemo);
    }

    /**
     * 包装日期备注
     */
    private String wrapDayMemo(DailyRecordModel dailyRecord) {
        StringBuilder dayMemo = new StringBuilder();

        int month = dailyRecord.getDaily().getMonth();
        int day = dailyRecord.getDay();
        int dayTaskNum = dailyRecord.getTasks().size();

        dayMemo.append(String.format("今年最辛苦的一天是 %s 月 %s 日，在这一天竟然完成了 %s 个任务", month, day, dayTaskNum));

        return dayMemo.toString();
    }

    /**
     * 包装月份备注
     */
    private String wrapMonthMemo(DailyModel daily, boolean max) {
        StringBuilder monthMemo = new StringBuilder();

        List<DailyRecordModel> dailyRecords = daily.getDailyRecords();

        monthMemo.append(String.format("今年最%s的一个月是 %s 月，共计工作了 %s 天，", max ? "辛苦" : "轻松",
          daily.getMonth(), dailyRecords.size()));
        monthMemo.append(String.format("其中有 %s 天在加班", calcDayExtraNum(dailyRecords)));

        return monthMemo.toString();
    }

    /**
     * 计算加班天数
     */
    private int calcDayExtraNum(List<DailyRecordModel> dailyRecords) {
        int dayExtraNum = 0;

        for (DailyRecordModel dailyRecord : dailyRecords) {
            if (dailyRecord.getExtra()) {
                dayExtraNum++;
            }
        }

        return dayExtraNum;
    }

    /**
     * 生成月报
     */
    private void generateMonthReport(ReportModel report) {
    }

}
