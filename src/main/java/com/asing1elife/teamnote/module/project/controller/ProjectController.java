package com.asing1elife.teamnote.module.project.controller;

import com.asing1elife.teamnote.core.bean.ResponseData;
import com.asing1elife.teamnote.core.controller.BaseController;
import com.asing1elife.teamnote.core.handle.ControllerHandler;
import com.asing1elife.teamnote.model.ProjectModel;
import com.asing1elife.teamnote.module.project.service.ProjectServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
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

}
