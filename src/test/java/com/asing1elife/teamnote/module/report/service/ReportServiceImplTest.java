package com.asing1elife.teamnote.module.report.service;

import com.asing1elife.teamnote.model.ReportModel;
import com.asing1elife.teamnote.model.dictionary.ReportType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ReportServiceImplTest {

    @Autowired
    private ReportServiceImpl reportService;

    @Test
    @Transactional
    public void getReportByType() {
        long dailyId = 4;
        String typeCode = ReportType.RETY_Year.getRealCode();

        ReportModel report = reportService.getReportByDailyAndType(dailyId, typeCode);

        System.out.println(report);
    }
}