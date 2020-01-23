package com.asing1elife.teamnote.module.task.service;

import com.asing1elife.teamnote.model.TaskModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TaskServiceImplTest {

    @Autowired
    private TaskServiceImpl taskService;

    @Test
    @Transactional
    public void getTasksByOrganizationAndYear() {
        long organizationId = 2;
        int year = 2019;

        List<TaskModel> tasks = taskService.getTasksByOrganizationAndYear(organizationId, year);

        tasks.forEach(task -> System.out.println(task));
    }
}