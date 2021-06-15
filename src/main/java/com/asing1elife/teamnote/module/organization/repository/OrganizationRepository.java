package com.asing1elife.teamnote.module.organization.repository;

import com.asing1elife.teamnote.core.repository.BaseRepository;
import com.asing1elife.teamnote.model.OrganizationModel;

import java.util.List;

/**
 * @author asing1elife
 */
public interface OrganizationRepository extends BaseRepository<OrganizationModel, Long> {

    List<OrganizationModel> findByUserId(Long userId);

    List<OrganizationModel> findByUserIdAndDisplayIsTrue(Long userid);

}
