package com.asing1elife.teamnote.module.daily.controller;

import com.asing1elife.teamnote.core.bean.ResponseData;
import com.asing1elife.teamnote.core.controller.BaseController;
import com.asing1elife.teamnote.core.handle.ControllerHandler;
import com.asing1elife.teamnote.model.DailyModel;
import com.asing1elife.teamnote.module.daily.service.DailyServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dailies")
public class DailyController extends BaseController<DailyModel, DailyServiceImpl> {

    @Override
    public ResponseData list() {
        return new ControllerHandler() {
            @Override
            public void doHandler(ResponseData responseData) {
                responseData.setData(service.getDailiesByYear());
            }
        }.handle();
    }
}
