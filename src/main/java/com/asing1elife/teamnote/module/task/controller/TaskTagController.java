package com.asing1elife.teamnote.module.task.controller;

import com.asing1elife.teamnote.core.controller.BaseController;
import com.asing1elife.teamnote.model.TaskTagModel;
import com.asing1elife.teamnote.module.task.service.TaskTagServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/task/tags")
public class TaskTagController extends BaseController<TaskTagModel, TaskTagServiceImpl> {
}
