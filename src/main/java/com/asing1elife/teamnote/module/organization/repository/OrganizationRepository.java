package com.asing1elife.teamnote.module.organization.repository;

import com.asing1elife.teamnote.core.repository.BaseRepository;
import com.asing1elife.teamnote.model.OrganizationModel;

import java.util.List;

public interface OrganizationRepository extends BaseRepository<OrganizationModel, Long> {

    List<OrganizationModel> findByUserId(long userId);

}
