package com.asing1elife.teamnote.module.organization.service;

import com.asing1elife.teamnote.core.service.BaseService;
import com.asing1elife.teamnote.model.OrganizationModel;
import com.asing1elife.teamnote.model.UserModel;
import com.asing1elife.teamnote.module.organization.repository.OrganizationRepository;
import com.asing1elife.teamnote.module.user.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationServiceImpl extends BaseService<OrganizationModel, OrganizationRepository> {

    @Autowired
    private UserServiceImpl userService;

    /**
     * 获取当前用户的组织列表
     */
    public List<OrganizationModel> getOrganizationsByCurrentUser() {
        long currentUserId = userService.getCurrentUserId();

        return repository.findByUserId(currentUserId);
    }

    @Override
    public OrganizationModel save(OrganizationModel organization) {
        // 获取当前用户id
        long currentUserId = userService.getCurrentUserId();

        // 与当前用户关联
        organization.setUser(new UserModel(currentUserId));

        return super.save(organization);
    }
}
