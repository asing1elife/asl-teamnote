/*****************************************************************************
 * Copyright (c) 2020, www.shixun.online
 *
 * All rights reserved
 *
 *****************************************************************************/
package com.asing1elife.teamnote.module.report.controller;

import com.asing1elife.teamnote.core.bean.ResponseData;
import com.asing1elife.teamnote.core.controller.BaseController;
import com.asing1elife.teamnote.core.handle.ControllerHandler;
import com.asing1elife.teamnote.model.ReportModel;
import com.asing1elife.teamnote.module.report.service.ReportServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
public class ReportController extends BaseController<ReportModel, ReportServiceImpl> {

    @GetMapping("/{type}/{dailyId}")
    public ResponseData getReportByDailyAndType(@PathVariable long dailyId, @PathVariable String type) {
        return new ControllerHandler() {
            @Override
            public void doHandler(ResponseData responseData) {
                responseData.setData(service.getReportByDailyAndType(dailyId, type));
            }
        }.handle();
    }

}
