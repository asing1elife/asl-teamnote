package com.asing1elife.teamnote.module.daily.service;

import com.asing1elife.teamnote.model.DailyModel;
import com.asing1elife.teamnote.model.DailyRecordModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sound.midi.Soundbank;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DailyServiceImplTest {

    @Autowired
    private DailyServiceImpl dailyService;

    @Autowired
    private DailyRecordServiceImpl dailyRecordService;

    @Test
    public void getDailiesByYearTest() {
        Map<Integer, List<DailyModel>> dailyMap = dailyService.getDailiesByYear(2L);

        dailyMap.keySet().forEach(key -> {
            System.out.printf("YEAR -> %s \n", key);

            dailyMap.get(key).forEach(daily -> {
                System.out.printf("MONTH -> %s \n", daily.getMonth());

                List<DailyRecordModel> records = dailyRecordService.getDailyRecordsByDaily(daily.getId());

                System.out.print("DAY ->");
                records.forEach(record -> System.out.printf(" %s \t", record.getDay()));
            });
        });
    }
}