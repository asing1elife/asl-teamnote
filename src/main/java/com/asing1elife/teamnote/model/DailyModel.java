package com.asing1elife.teamnote.model;

import com.google.common.collect.Lists;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "al_daily")
public class DailyModel extends BaseModel {

    @ManyToOne
    private OrganizationModel organization;

    // 所属年份
    @Column
    private Integer year;

    // 所属月份
    @Column
    private Integer month;

    // 是否报销
    @Column
    private Boolean expense = false;

    // 报销金额
    @Column
    private Double expenseAmount = 0D;

    // 总计天数
    @Column
    private Integer totalDay = 0;

    @Transient
    private List<DailyRecordModel> dailyRecords = Lists.newArrayList();

    public DailyModel() { }

    public DailyModel(long id) {
        super.setId(id);
    }

}
