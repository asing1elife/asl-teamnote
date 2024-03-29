package com.asing1elife.teamnote.module.project.repository;

import com.asing1elife.teamnote.core.repository.BaseRepository;
import com.asing1elife.teamnote.model.ProjectModel;

import java.util.List;

/**
 * @author asing1elife
 */
public interface ProjectRepository extends BaseRepository<ProjectModel, Long> {

    /**
     * 获取指定组织所有项目
     */
    List<ProjectModel> findByOrganizationIdOrderByIndexNoAsc(Long organizationId);

    List<ProjectModel> findByOrganizationIdAndDisplayIsTrueOrderByIndexNoAsc(Long organizationId);

}
