package com.asing1elife.teamnote.module.reimburse.controller;

import com.asing1elife.teamnote.core.bean.ResponseData;
import com.asing1elife.teamnote.core.controller.BaseController;
import com.asing1elife.teamnote.core.handle.ControllerHandler;
import com.asing1elife.teamnote.model.ReimburseModel;
import com.asing1elife.teamnote.module.reimburse.service.ReimburseServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reimburses")
public class ReimburseController extends BaseController<ReimburseModel, ReimburseServiceImpl> {

    @GetMapping("/check/{dailyId}")
    public ResponseData check(@PathVariable long dailyId) {
        return new ControllerHandler() {
            @Override
            public void doHandler(ResponseData responseData) {
                responseData.setData(service.getReimburseByDaily(dailyId));
            }
        }.handle();
    }

    @PostMapping("/generate/{dailyId}")
    public ResponseData generate(@PathVariable long dailyId) {
        return new ControllerHandler() {
            @Override
            public void doHandler(ResponseData responseData) {
                service.generateDailyReimburse(dailyId);
            }
        }.handle();
    }

}
