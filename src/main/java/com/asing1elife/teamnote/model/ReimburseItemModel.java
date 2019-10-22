package com.asing1elife.teamnote.model;

import com.asing1elife.teamnote.model.dictionary.ReimburseItemType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "al_reimburse_item")
public class ReimburseItemModel extends BaseModel {

    @ManyToOne
    @JsonIgnore
    private ReimburseModel reimburse;

    @ManyToOne
    private DailyRecordModel dailyRecord;

    @ManyToOne
    private ReimburseItemType type = ReimburseItemType.RITY_Extra;

    @Column
    private Date reimburseDate;

    @Column
    private Double amount = 0D;

}
