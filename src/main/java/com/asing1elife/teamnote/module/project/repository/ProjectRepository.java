package com.asing1elife.teamnote.module.project.repository;

import com.asing1elife.teamnote.core.repository.BaseRepository;
import com.asing1elife.teamnote.model.ProjectModel;

import java.util.List;

public interface ProjectRepository extends BaseRepository<ProjectModel, Long> {

    /**
     * 获取指定组织所有项目
     */
    List<ProjectModel> findByOrganization_IdOrderByIndexNoAsc(Long organizationId);

}
