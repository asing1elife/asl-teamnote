package com.asing1elife.teamnote.model;

import com.asing1elife.teamnote.model.dictionary.ReimburseStatus;
import com.google.common.collect.Lists;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "al_reimburse")
public class ReimburseModel extends BaseModel {

    @OneToOne
    private DailyModel daily;

    @ManyToOne
    private ReimburseStatus status = ReimburseStatus.RBST_Impl;

    @OneToMany(mappedBy = "reimburse")
    private List<ReimburseItemModel> items = Lists.newArrayList();

    @OneToMany(mappedBy = "reimburse")
    private List<ReimburseInvoiceModel> invoices = Lists.newArrayList();

}
