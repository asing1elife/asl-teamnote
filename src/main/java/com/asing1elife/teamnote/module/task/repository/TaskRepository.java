package com.asing1elife.teamnote.module.task.repository;

import com.asing1elife.teamnote.core.repository.BaseRepository;
import com.asing1elife.teamnote.model.TaskModel;
import com.asing1elife.teamnote.model.dictionary.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends BaseRepository<TaskModel, Long> {

    /**
     * 分页获取项目任务列表
     */
    Page<TaskModel> findByProjectId(long projectId, Pageable pageable);

    /**
     * 获取指定项目未完成任务
     */
    List<TaskModel> findByStatusNotAndProjectIdOrderByLevelDescStatusAsc(TaskStatus status, long projectId);

    /**
     * 获取指定项目已完成完成任务
     */
    List<TaskModel> findByStatusAndProjectIdOrderByLevelDescStatusAsc(TaskStatus status, long projectId);

    /**
     * 获取指定状态任务列表
     */
    List<TaskModel> findByProjectOrganizationIdAndStatus(long organizationId, TaskStatus status);

    /**
     * 获取对应组织和名称的任务列表
     */
    List<TaskModel> findByProjectOrganizationIdAndNameLike(long organizationId, String taskName);

    @Query(value =
        "SELECT " +
            "ta.* " +
            "FROM " +
            "al_task ta, " +
            "al_project pr " +
            "WHERE " +
            "ta.project_id = pr.id AND " +
            "pr.organization_id = ? AND " +
            "ta.createTime >= ? AND " +
            "ta.createTime <= ?",
        nativeQuery = true)
    List<TaskModel> queryByOrganizationIdAndYear(long organizationId, String beginYear, String endYear);

    Long countByProjectId(long projectId);

}
