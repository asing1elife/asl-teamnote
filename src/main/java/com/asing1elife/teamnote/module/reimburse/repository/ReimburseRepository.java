package com.asing1elife.teamnote.module.reimburse.repository;

import com.asing1elife.teamnote.core.repository.BaseRepository;
import com.asing1elife.teamnote.model.ReimburseModel;

public interface ReimburseRepository extends BaseRepository<ReimburseModel, Long> {

    /**
     * 获取指定月份报销单
     */
    ReimburseModel getByDaily_Id(long dailyId);

}
