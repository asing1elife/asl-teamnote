package com.asing1elife.teamnote.module.reimburse.service;

import com.asing1elife.teamnote.core.service.BaseService;
import com.asing1elife.teamnote.model.DailyRecordModel;
import com.asing1elife.teamnote.model.ReimburseItemModel;
import com.asing1elife.teamnote.model.ReimburseModel;
import com.asing1elife.teamnote.module.daily.service.DailyRecordServiceImpl;
import com.asing1elife.teamnote.module.reimburse.repository.ReimburseItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReimburseItemServiceImpl extends BaseService<ReimburseItemModel, ReimburseItemRepository> {

    @Autowired
    private DailyRecordServiceImpl dailyRecordService;

    /**
     * 生成月份加班报销单项目
     */
    public void generateDailyExtraReimburseItems(ReimburseModel reimburse, long dailyId) {
        // 获取该月份加班的日志记录
        List<DailyRecordModel> records = dailyRecordService.getExtraDailyRecordsByDaily(dailyId);

        records.forEach(record -> {
            // 报销对应加班记录的报销项目
            ReimburseItemModel item = new ReimburseItemModel();
            item.setReimburse(reimburse);
            item.setDailyRecord(record);
            item.setReimburseDate(record.getCreateTime());
            item.setAmount(18D);

            super.save(item);
        });
    }

}
