package com.asing1elife.teamnote.module.project.service;

import com.asing1elife.teamnote.core.service.BaseService;
import com.asing1elife.teamnote.model.ProjectModel;
import com.asing1elife.teamnote.module.project.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl extends BaseService<ProjectModel, ProjectRepository> {

    /**
     * 获取指定组织所有项目
     */
    public List<ProjectModel> findByOrganizationId(Long organizationId) {
        return repository.findByOrganization_IdOrderByIndexNoAsc(organizationId);
    }

}
