package com.asing1elife.teamnote.module.daily.controller;

import com.asing1elife.teamnote.core.bean.ResponseData;
import com.asing1elife.teamnote.core.controller.BaseController;
import com.asing1elife.teamnote.core.handle.ControllerHandler;
import com.asing1elife.teamnote.model.DailyRecordModel;
import com.asing1elife.teamnote.module.daily.service.DailyRecordServiceImpl;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiresAuthentication
@RequestMapping("/api/daily/records")
public class DailyRecordController extends BaseController<DailyRecordModel, DailyRecordServiceImpl> {

    @PostMapping("")
    public ResponseData records(@RequestParam Long dailyId) {
        return new ControllerHandler() {
            @Override
            public void doHandler(ResponseData responseData) {
                responseData.setData(service.getDailyRecordsByDaily(dailyId));
            }
        }.handle();
    }

    @PutMapping("/{recordId}/extra")
    public ResponseData extra(@PathVariable Long recordId, @RequestParam Boolean extra) {
        return new ControllerHandler() {
            @Override
            public void doHandler(ResponseData responseData) {
                service.updateDailyRecordExtra(recordId, extra);
            }
        }.handle();
    }

    @PutMapping("/{recordId}/repay")
    public ResponseData repay(@PathVariable Long recordId, @RequestParam Boolean repay) {
        return new ControllerHandler() {
            @Override
            public void doHandler(ResponseData responseData) {
                service.updateDailyRecordRepay(recordId, repay);
            }
        }.handle();
    }

    @PutMapping("/{recordId}/rest")
    public ResponseData rest(@PathVariable Long recordId, @RequestParam Boolean rest) {
        return new ControllerHandler() {
            @Override
            public void doHandler(ResponseData responseData) {
                service.updateDailyRecordRest(recordId, rest);
            }
        }.handle();
    }

}
