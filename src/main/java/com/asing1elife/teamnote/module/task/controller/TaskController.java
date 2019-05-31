package com.asing1elife.teamnote.module.task.controller;

import com.asing1elife.teamnote.core.bean.ResponseData;
import com.asing1elife.teamnote.core.controller.BaseController;
import com.asing1elife.teamnote.core.handle.ControllerHandler;
import com.asing1elife.teamnote.model.TaskModel;
import com.asing1elife.teamnote.module.task.service.TaskServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController extends BaseController<TaskModel, TaskServiceImpl> {

    @PostMapping("")
    public ResponseData tasks(@RequestParam long projectId) {
        return new ControllerHandler() {
            @Override
            public void doHandler(ResponseData responseData) {
                responseData.setData(service.findByProjectId(projectId));
            }
        }.handle();
    }

    @PutMapping("/{taskId}/status")
    public ResponseData status(@PathVariable long taskId, @RequestParam long organizationId, @RequestParam String statusCode) {
        return new ControllerHandler() {
            @Override
            public void doHandler(ResponseData responseData) {
                service.status(taskId, organizationId, statusCode);
            }
        }.handle();
    }

}
