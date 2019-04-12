package com.asing1elife.teamnote.module.task.repository;

import com.asing1elife.teamnote.core.repository.BaseRepository;
import com.asing1elife.teamnote.model.TaskModel;
import com.asing1elife.teamnote.model.dictionary.TaskStatus;

import java.util.List;

public interface TaskRepository extends BaseRepository<TaskModel, Long> {

    /**
     * 获取指定项目未完成任务
     */
    List<TaskModel> findByStatusNotAndProject_IdOrderByLevelDescStatusAsc(TaskStatus status, Long projectId);

    /**
     * 获取指定项目已完成完成任务
     */
    List<TaskModel> findByStatusAndProject_IdOrderByLevelDescStatusAsc(TaskStatus status, Long projectId);

    /**
     * 获取指定状态任务列表
     */
    List<TaskModel> findByStatus(TaskStatus status);

}
