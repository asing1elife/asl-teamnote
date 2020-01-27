package com.asing1elife.teamnote.module.project.controller;

import com.asing1elife.teamnote.core.bean.ResponseData;
import com.asing1elife.teamnote.core.controller.BaseController;
import com.asing1elife.teamnote.core.handle.ControllerHandler;
import com.asing1elife.teamnote.model.ProjectModel;
import com.asing1elife.teamnote.module.project.service.ProjectServiceImpl;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiresAuthentication
@RequestMapping("/api/projects")
public class ProjectController extends BaseController<ProjectModel, ProjectServiceImpl> {

    @PostMapping("")
    public ResponseData projects(@RequestParam Long organizationId) {
        return new ControllerHandler() {
            @Override
            public void doHandler(ResponseData responseData) {
                responseData.setData(service.findByOrganizationId(organizationId));
            }
        }.handle();
    }

    @PostMapping("/sort")
    public ResponseData sort(@RequestBody List<ProjectModel> projects) {
        return new ControllerHandler() {
            @Override
            public void doHandler(ResponseData responseData) {
                service.sortProjects(projects);
            }
        }.handle();
    }

}
