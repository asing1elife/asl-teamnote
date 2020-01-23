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
        int taskFinishPercent;
        int projectNum;
        String projectMemo;
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
        // 项目备注
        projectMemo = wrapProjectMemo(projectMap);
        // 任务标签备注
        taskTagMemo = wrapTaskTagMemo(taskTagMap);
        // 任务完成率
        taskFinishPercent = calcPercent(taskFinishNum, taskNum);

        report.setTaskNum(taskNum);
        report.setTaskFinishNum(taskFinishNum);
        report.setTaskFinishPercent(taskFinishPercent);
        report.setProjectNum(projectNum);
        report.setProjectMemo(projectMemo);
        report.setTaskTagMemo(taskTagMemo);
    }

    /**
     * 包装项目备注
     */
    private String wrapProjectMemo(Map<ProjectModel, List<TaskModel>> projectMap) {
        StringBuilder memo = new StringBuilder();

        for (ProjectModel project : projectMap.keySet()) {
            List<TaskModel> tasks = projectMap.get(project);

            // 包装任务备注
            memo.append(wrapTaskMemoByType(tasks, project.getName()));
        }

        return memo.toString();
    }

    /**
     * 包装任务标签备注
     */
    private String wrapTaskTagMemo(Map<TaskTagModel, List<TaskModel>> taskTagMap) {
        StringBuilder memo = new StringBuilder();

        for (TaskTagModel taskTag : taskTagMap.keySet()) {
            List<TaskModel> tasks = taskTagMap.get(taskTag);

            // 包装任务备注
            memo.append(wrapTaskMemoByType(tasks, taskTag.getName()));
        }

        return memo.toString();
    }

    /**
     * 根据类型包装任务备注
     */
    private String wrapTaskMemoByType(List<TaskModel> tasks, String typeName) {
        StringBuilder memo = new StringBuilder();

        int taskNum = tasks.size();
        int taskFinishNum = calcTaskFinishNum(tasks);
        int taskFinishPercent = calcPercent(taskFinishNum, taskNum);

        memo.append(String.format("[ %s ] 任务共计 %s 个，", typeName, tasks.size()));
        memo.append(String.format("已完成 %s 个，完成率达到 %s%%", taskFinishNum, taskFinishPercent));
        memo.append("<br>");

        return memo.toString();
    }

    /**
     * 计算百分比
     */
    private int calcPercent(double finishNum, double totalNum) {
        return (int) (finishNum / totalNum * 100D);
    }

    /**
     * 计算任务列表的完成数
     */
    private int calcTaskFinishNum(List<TaskModel> tasks) {
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
        int dayExtraPercent = 0;

        // 月度详细数据
        int monthDayMaxNum = 0;
        int monthDayMinNum = 0;
        int monthDayExtraNum = 0;
        String monthDayMaxMemo = "";
        String monthDayMinMemo = "";
        String monthDayExtraMemo = "";

        // 日期详细数据
        int dayTaskMaxNum = 0;
        String dayMemo = "";

        // 获取指定组织指定年份所有日志
        List<DailyModel> dailies = dailyService.getDailiesByOrganizationAndYear(organizationId, year);

        for (DailyModel daily : dailies) {
            List<DailyRecordModel> dailyRecords = daily.getDailyRecords();

            // 当前月份的总天数
            int nowMonthDayNum = dailyRecords.size();
            // 当前月休息天数
            int nowMonthDayRestNum = 0;
            // 当月加班天数
            int nowMonthDayExtraNum = 0;
            // 当前月有效工作日
            int nowMonthEffectiveDayNum;

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
                    nowMonthDayRestNum++;
                }

                // 加班日累加
                if (dailyRecord.getExtra()) {
                    dayExtraNum++;
                    nowMonthDayExtraNum++;
                }
            }

            // 当前月有效天数 = 当前月总天数 - 当前月休息天数
            nowMonthEffectiveDayNum = nowMonthDayNum - nowMonthDayRestNum;

            // 记录工作时间最长的天数
            if (monthDayMaxNum < nowMonthEffectiveDayNum) {
                monthDayMaxNum = nowMonthEffectiveDayNum;
                // 包装详细描述
                monthDayMaxMemo = wrapMonthMemo(daily, "最漫长");
            }

            // 计算工作时间最短的天数
            if (monthDayMinNum > nowMonthEffectiveDayNum || monthDayMinNum == 0) {
                monthDayMinNum = nowMonthEffectiveDayNum;
                // 包装详细描述
                monthDayMinMemo = wrapMonthMemo(daily, "最轻松");
            }

            // 计算加班最多的天数
            if (monthDayExtraNum < nowMonthDayExtraNum) {
                monthDayExtraNum = nowMonthDayExtraNum;
                // 包装详细描述
                monthDayExtraMemo = wrapMonthMemo(daily, "最辛苦");
            }
        }

        // 工作天数修正，需要减去休息日
        dayNum -= dayRestNum;
        // 加班率
        dayExtraPercent = calcPercent(dayExtraNum, dayNum);

        report.setDayNum(dayNum);
        report.setDayExtraNum(dayExtraNum);
        report.setDayExtraPercent(dayExtraPercent);
        report.setMonthNum(dailies.size());
        report.setMonthMemo(String.format("%s<br>%s<br>%s", monthDayMaxMemo, monthDayExtraMemo, monthDayMinMemo));
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
    private String wrapMonthMemo(DailyModel daily, String typeName) {
        StringBuilder monthMemo = new StringBuilder();

        List<DailyRecordModel> dailyRecords = daily.getDailyRecords();

        // 休息天数
        int dayRestNum = calcDayRestNum(dailyRecords);
        // 有效天数
        int effectiveDayNum = dailyRecords.size() - dayRestNum;
        // 加班天数
        int dayExtraNum = calcDayExtraNum(dailyRecords);
        // 加班比率
        int dayExtraPercent = calcPercent(dayExtraNum, effectiveDayNum);

        monthMemo.append(String.format("今年%s的一个月是 %s 月，共计工作了 %s 天，", typeName,
          daily.getMonth(), effectiveDayNum));
        monthMemo.append(String.format("其中有 %s 天在加班，加班率达到 %s%%", dayExtraNum, dayExtraPercent));

        return monthMemo.toString();
    }

    /**
     * 计算休息天数
     */
    private int calcDayRestNum(List<DailyRecordModel> dailyRecords) {
        int dayRestNum = 0;

        for (DailyRecordModel dailyRecord : dailyRecords) {
            if (dailyRecord.getRest()) {
                dayRestNum++;
            }
        }

        return dayRestNum;
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
