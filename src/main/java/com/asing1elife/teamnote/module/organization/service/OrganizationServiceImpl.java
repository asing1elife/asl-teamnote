package com.asing1elife.teamnote.module.organization.service;

import com.asing1elife.teamnote.core.service.BaseService;
import com.asing1elife.teamnote.model.OrganizationModel;
import com.asing1elife.teamnote.model.UserModel;
import com.asing1elife.teamnote.module.organization.repository.OrganizationRepository;
import com.asing1elife.teamnote.module.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author asing1elife
 */
@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl extends BaseService<OrganizationModel, OrganizationRepository> {

    private final UserServiceImpl userService;

    /**
     * 获取当前用户的组织列表
     */
    public List<OrganizationModel> findByCurrentUser() {
        String allStr = request.getParameter("all");
        boolean all = false;

        // 尝试从前端获取参数
        if (!StringUtils.isEmpty(allStr)) {
            all = Boolean.parseBoolean(allStr);
        }

        long currentUserId = userService.getCurrentUserId();

        // 该用户的所有组织
        if (all) {
            return repository.findByUserId(currentUserId);
        }

        // 只显示没有隐藏的组织
        return repository.findByUserIdAndDisplayIsTrue(currentUserId);
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
