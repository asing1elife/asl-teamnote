package com.asing1elife.teamnote.module.project.service;

import com.asing1elife.teamnote.core.service.BaseService;
import com.asing1elife.teamnote.model.ProjectModel;
import com.asing1elife.teamnote.module.project.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectServiceImpl extends BaseService<ProjectModel, ProjectRepository> {

    /**
     * 获取指定组织所有项目
     */
    public List<ProjectModel> findByOrganizationId(Long organizationId) {
        return repository.findByOrganizationIdOrderByIndexNoAsc(organizationId);
    }

    /**
     * 增加项目任务数量
     */
    public void addProjectTaskNum(long projectId) {
        ProjectModel project = super.get(projectId);
        project.addTaskNum();

        super.save(project);
    }

    /**
     * 减少项目任务数量
     */
    public void reduceProjectTaskNum(long projectId) {
        ProjectModel project = super.get(projectId);
        project.reduceTaskNum();

        super.save(project);
    }

    /**
     * 项目排序
     */
    @Transactional
    public void sortProjects(List<ProjectModel> projects) {
        for (int i = 0; i < projects.size(); i++) {
            ProjectModel project = projects.get(i);
            project.setIndexNo(i);

            super.save(project);
        }
    }

}
