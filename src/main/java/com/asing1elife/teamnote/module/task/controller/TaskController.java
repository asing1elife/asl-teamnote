package com.asing1elife.teamnote.module.task.controller;

import com.asing1elife.teamnote.core.bean.ResponseData;
import com.asing1elife.teamnote.core.controller.BaseController;
import com.asing1elife.teamnote.core.handle.ControllerHandler;
import com.asing1elife.teamnote.model.TaskModel;
import com.asing1elife.teamnote.module.task.service.TaskServiceImpl;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiresAuthentication
@RequestMapping("/api/tasks")
public class TaskController extends BaseController<TaskModel, TaskServiceImpl> {

    @GetMapping("/{projectId}/unfinish")
    public ResponseData unFinishTasks(@PathVariable long projectId) {
        return new ControllerHandler() {
            @Override
            public void doHandler(ResponseData responseData) {
                responseData.setData(service.findUnFinishTasksByProjectId(projectId));
            }
        }.handle();
    }

    @GetMapping("/{projectId}/finish")
    public ResponseData finishTasks(@PathVariable long projectId) {
        return new ControllerHandler() {
            @Override
            public void doHandler(ResponseData responseData) {
                responseData.setData(service.findFinishTasksByProjectId(projectId));
            }
        }.handle();
    }

    @PutMapping("{taskId}/status")
    public ResponseData status(@PathVariable long taskId, @RequestParam long organizationId, @RequestParam String statusCode) {
        return new ControllerHandler() {
            @Override
            public void doHandler(ResponseData responseData) {
                service.status(taskId, organizationId, statusCode);
            }
        }.handle();
    }

    @PostMapping("/{organizationId}/search")
    public ResponseData search(@PathVariable long organizationId, @RequestParam String taskName) {
        return new ControllerHandler() {
            @Override
            public void doHandler(ResponseData responseData) {
                responseData.setData(service.getOrganizationTasksByName(organizationId, taskName));
            }
        }.handle();
    }

}
