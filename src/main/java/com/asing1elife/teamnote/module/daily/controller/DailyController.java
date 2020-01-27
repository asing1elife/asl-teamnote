package com.asing1elife.teamnote.module.daily.controller;

import com.asing1elife.teamnote.core.bean.ResponseData;
import com.asing1elife.teamnote.core.controller.BaseController;
import com.asing1elife.teamnote.core.handle.ControllerHandler;
import com.asing1elife.teamnote.model.DailyModel;
import com.asing1elife.teamnote.module.daily.service.DailyServiceImpl;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiresAuthentication
@RequestMapping("/api/dailies")
public class DailyController extends BaseController<DailyModel, DailyServiceImpl> {

    @PostMapping("")
    public ResponseData lists(@RequestParam Long organizationId) {
        return new ControllerHandler() {
            @Override
            public void doHandler(ResponseData responseData) {
                responseData.setData(service.getDailiesByYear(organizationId));
            }
        }.handle();
    }
}
