/*****************************************************************************
 * Copyright (c) 2020, www.shixun.online
 *
 * All rights reserved
 *
 *****************************************************************************/
package com.asing1elife.teamnote.model;

import com.asing1elife.teamnote.model.dictionary.ReportType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "al_report")
public class ReportModel extends BaseModel {

    @Column
    private String name;

    @JsonIgnore
    @ManyToOne
    private OrganizationModel organization;

    @JsonIgnore
    @ManyToOne
    private DailyModel daily;

    @Column
    private Integer taskNum;

    @Column
    private Integer taskFinishNum;

    @Column
    private Integer projectNum;

    @Column
    private String taskTagMemo;

    @Column
    private Integer dayNum;

    @Column
    private Integer dayExtraNum;

    @Column
    private String monthMemo;

    @Column
    private String dayMemo;

    @ManyToOne
    private ReportType type = ReportType.RETY_Year;

    public ReportModel() { }

    public ReportModel(DailyModel daily) {
        this.organization = daily.getOrganization();
        this.daily = daily;
    }

    @Override
    public String toString() {
        return "ReportModel{" +
          "name='" + name + '\'' +
          ", taskNum=" + taskNum +
          ", taskFinishNum=" + taskFinishNum +
          ", projectNum=" + projectNum +
          ", taskTagMemo='" + taskTagMemo + '\'' +
          ", dayNum=" + dayNum +
          ", dayExtraNum=" + dayExtraNum +
          ", monthMemo='" + monthMemo + '\'' +
          ", dayMemo='" + dayMemo + '\'' +
          ", type=" + type +
          '}';
    }
}
