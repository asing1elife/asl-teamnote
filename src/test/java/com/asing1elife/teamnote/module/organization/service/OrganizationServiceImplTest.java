package com.asing1elife.teamnote.module.organization.service;

import com.asing1elife.teamnote.model.OrganizationModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrganizationServiceImplTest {

    @Autowired
    private OrganizationServiceImpl organizationService;

    @Test
    public void saveTest() {
        OrganizationModel projectGroup = new OrganizationModel();
        projectGroup.setName("name");
        projectGroup.setDescription("description");

        System.out.println(organizationService.save(projectGroup));
    }

    @Test
    public void findTest() {
        List<OrganizationModel> projectGroups = organizationService.findAll();

        projectGroups.forEach(projectGroup -> System.out.println(projectGroup.toString()));
    }

}