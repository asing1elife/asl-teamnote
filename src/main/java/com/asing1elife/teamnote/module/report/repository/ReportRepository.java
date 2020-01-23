/*****************************************************************************
 * Copyright (c) 2020, www.shixun.online
 *
 * All rights reserved
 *
 *****************************************************************************/
package com.asing1elife.teamnote.module.report.repository;

import com.asing1elife.teamnote.core.repository.BaseRepository;
import com.asing1elife.teamnote.model.ReportModel;

public interface ReportRepository extends BaseRepository<ReportModel, Long> {

    ReportModel getReportByOrOrganizationIdAndDailyIdAndTypeCode(long organizationId, long dailyId, String typeCode);

}

