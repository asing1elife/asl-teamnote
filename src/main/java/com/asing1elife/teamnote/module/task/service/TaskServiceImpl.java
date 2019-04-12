package com.asing1elife.teamnote.module.task.service;

import com.asing1elife.teamnote.core.service.BaseService;
import com.asing1elife.teamnote.core.util.DateUtil;
import com.asing1elife.teamnote.model.DailyRecordModel;
import com.asing1elife.teamnote.model.TaskModel;
import com.asing1elife.teamnote.model.dictionary.TaskStatus;
import com.asing1elife.teamnote.module.daily.service.DailyRecordServiceImpl;
import com.asing1elife.teamnote.module.daily.service.DailyServiceImpl;
import com.asing1elife.teamnote.module.task.repository.TaskRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl extends BaseService<TaskModel, TaskRepository> {

    @Autowired
    private DailyServiceImpl dailyService;

    @Autowired
    private DailyRecordServiceImpl dailyRecordService;

    /**
     * 获取指定项目所有任务
     */
    public List<TaskModel> findByProjectId(Long projectId) {
        List<TaskModel> tasks = Lists.newArrayList();

        // 先获取初始化和进行中的任务
        List<TaskModel> unFinishTasks = repository.findByStatusNotAndProject_IdOrderByLevelDescStatusAsc(TaskStatus.TAST_Finish, projectId);
        // 再获取已完成的任务
        List<TaskModel> finishTasks = repository.findByStatusAndProject_IdOrderByLevelDescStatusAsc(TaskStatus.TAST_Finish, projectId);

        tasks.addAll(unFinishTasks);
        tasks.addAll(finishTasks);
        return tasks;
    }

    /**
     * 获取进行中的任务列表
     */
    public List<TaskModel> findByImplTasks() {
        return repository.findByStatus(TaskStatus.TAST_Impl);
    }

    /**
     * 更新状态
     */
    public void status(Long taskId, String statusCode) {
        TaskStatus status = new TaskStatus(statusCode);

        TaskModel task = super.getOne(taskId);
        task.setStatus(status);

        if (status.equals(TaskStatus.TAST_Impl)) {
            // 将任务状态更新为进行中，设置开始时间
            task.setBeginDate(DateUtil.getSysDateTimestamp());
        } else if (status.equals(TaskStatus.TAST_Finish)) {
            // 将任务状态更新为已完成，设置完成时间
            task.setFinishDate(DateUtil.getSysDateTimestamp());

            if (task.getBeginDate() == null) {
                // 没有设置开始时间，则直接设置
                task.setBeginDate(DateUtil.getSysDateTimestamp());
            }
        }

        // 更新日志记录和任务的关联
        updateDailyRecordTaskRelation(task);

        super.save(task);
    }

    /**
     * 更新日志记录和任务的关联
     */
    private void updateDailyRecordTaskRelation(TaskModel task) {
        // 获取或新增日志及日志记录
        // 因为日志和日志记录默认是每次点击日志tab时检测并创建，但有可能一开始进入页面直接对任务进行操作，所以此处也需要检测
        DailyRecordModel record = dailyService.getOrSaveDailyAndRecord();
        // 获取日志记录任务列表
        List<TaskModel> tasks = record.getTasks();

        // 只要任务状态不是初始化，则需要与当天的日志进行关联
        if (!task.getStatus().equals(TaskStatus.TAST_Init)) {
            // 不存在则添加关联
            if (getCurrentTask(tasks, task) == null) {
                tasks.add(task);
            }
        } else {
            // 移除任务关联
            tasks.removeIf(tempTask -> tempTask.getId().equals(task.getId()));
        }

        dailyRecordService.updateDailyRecordTask(record);
    }

    /**
     * 从列表中获取当前任务
     */
    private TaskModel getCurrentTask(List<TaskModel> tasks, TaskModel task) {
        TaskModel currentTask = null;

        for (TaskModel tempTask : tasks) {
            if (tempTask.getId().equals(task.getId())) {
                currentTask = tempTask;
                break;
            }
        }

        return currentTask;
    }

}
