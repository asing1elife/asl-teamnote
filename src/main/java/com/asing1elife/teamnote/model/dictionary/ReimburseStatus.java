package com.asing1elife.teamnote.model.dictionary;

import com.asing1elife.teamnote.model.DictionaryModel;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("com.asing1elife.teamnote.model.dictionary.ReimburseStatus")
public class ReimburseStatus extends DictionaryModel {

    // 报销中
    public static final ReimburseStatus RBST_Impl = new ReimburseStatus("RBST_Impl");
    // 已结算
    public static final ReimburseStatus RBST_Finish = new ReimburseStatus("RBST_Finish");

    public ReimburseStatus() {
    }

    public ReimburseStatus(String code) {
        super(code);
    }
}
