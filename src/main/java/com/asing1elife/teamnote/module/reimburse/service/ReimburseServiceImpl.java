package com.asing1elife.teamnote.module.reimburse.service;

import com.asing1elife.teamnote.core.service.BaseService;
import com.asing1elife.teamnote.model.DailyModel;
import com.asing1elife.teamnote.model.ReimburseModel;
import com.asing1elife.teamnote.module.reimburse.repository.ReimburseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReimburseServiceImpl extends BaseService<ReimburseModel, ReimburseRepository> {

    @Autowired
    private ReimburseItemServiceImpl reimburseItemService;

    /**
     * 生成月份报销单
     */
    @Transactional
    public void generateDailyReimburse(long dailyId) {
        // 获取该月份报销单
        ReimburseModel reimburse = getReimburseByDaily(dailyId);

        // 为空则为该月份创建一份报销单
        if (reimburse == null) {
            reimburse = new ReimburseModel();
            reimburse.setDaily(new DailyModel(dailyId));

            super.save(reimburse);
        }

        // 生成月份加班报销单项目
        reimburseItemService.generateDailyExtraReimburseItems(reimburse, dailyId);
    }

    /**
     * 获取指定月份报销单
     */
    public ReimburseModel getReimburseByDaily(long dailyId) {
        return repository.getByDaily_Id(dailyId);
    }

}
