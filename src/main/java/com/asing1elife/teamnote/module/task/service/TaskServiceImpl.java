package com.asing1elife.teamnote.module.task.service;

import com.asing1elife.teamnote.core.service.BaseService;
import com.asing1elife.teamnote.core.util.DateUtil;
import com.asing1elife.teamnote.model.DailyRecordModel;
import com.asing1elife.teamnote.model.TaskModel;
import com.asing1elife.teamnote.model.dictionary.TaskStatus;
import com.asing1elife.teamnote.module.daily.service.DailyRecordServiceImpl;
import com.asing1elife.teamnote.module.daily.service.DailyServiceImpl;
import com.asing1elife.teamnote.module.project.service.ProjectServiceImpl;
import com.asing1elife.teamnote.module.task.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class TaskServiceImpl extends BaseService<TaskModel, TaskRepository> {

    @Autowired
    private DailyServiceImpl dailyService;

    @Autowired
    private DailyRecordServiceImpl dailyRecordService;

    @Autowired
    private ProjectServiceImpl projectService;

    @Override
    public Page<TaskModel> page(HttpServletRequest request) {
        long projectId = Long.valueOf(request.getParameter("projectId"));
        int pageNo = Integer.valueOf(request.getParameter("pageNo"));

        Pageable pageable = PageRequest.of(pageNo, 10, Sort.Direction.ASC, "level.code");

        return repository.findByProject_Id(projectId, pageable);
    }

    /**
     * 获取指定项目未完成的任务列表
     */
    public List<TaskModel> findUnFinishTasksByProjectId(long projectId) {
        return repository.findByStatusNotAndProject_IdOrderByLevelDescStatusAsc(TaskStatus.TAST_Finish, projectId);
    }

    /**
     * 获取指定项目已完成的任务列表
     */
    public List<TaskModel> findFinishTasksByProjectId(long projectId) {
        return repository.findByStatusAndProject_IdOrderByLevelDescStatusAsc(TaskStatus.TAST_Finish, projectId);
    }

    /**
     * 获取进行中的任务列表
     */
    public List<TaskModel> findByImplTasks(long organizationId) {
        return repository.findByProject_Organization_idAndStatus(organizationId, TaskStatus.TAST_Impl);
    }

    /**
     * 更新状态
     */
    @Transactional
    public void status(long taskId, long organizationId, String statusCode) {
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
        updateDailyRecordTaskRelation(task, organizationId);

        super.save(task);
    }

    @Override
    @Transactional
    public TaskModel save(TaskModel task) {
        // 新增任务时，所属项目中的任务数量需要累加
        if (task.isNew()) {
            projectService.addProjectTaskNum(task.getProject().getId());
        }

        return super.save(task);
    }

    /**
     * 删除任务
     */
    @Override
    @Transactional
    public void delete(long id) {
        TaskModel task = super.getOne(id);

        // 从与之关联的任务日志中删除该任务
        deleteDailyRecordTaskRelate(task);

        // 删除任务时，所属项目的任务数量需要减少
        projectService.reduceProjectTaskNum(task.getProject().getId());

        // 再删除任务
        super.delete(id);
    }

    /**
     * 从与之关联的任务日志中删除该任务
     */
    private void deleteDailyRecordTaskRelate(TaskModel task) {
        task.setRecords(null);

        super.save(task);
    }

    /**
     * 更新日志记录和任务的关联
     */
    private void updateDailyRecordTaskRelation(TaskModel task, long organizationId) {
        // 获取或新增日志及日志记录
        // 因为日志和日志记录默认是每次点击日志tab时检测并创建，但有可能一开始进入页面直接对任务进行操作，所以此处也需要检测
        DailyRecordModel record = dailyService.getOrSaveDailyAndRecord(organizationId);
        // 获取日志记录任务列表
        List<TaskModel> tasks = record.getTasks();

        // 不存在则添加关联
        if (getCurrentTask(tasks, task) == null) {
            tasks.add(task);
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
