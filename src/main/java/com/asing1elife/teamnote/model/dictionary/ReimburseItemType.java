package com.asing1elife.teamnote.model.dictionary;

import com.asing1elife.teamnote.model.DictionaryModel;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("com.asing1elife.teamnote.model.dictionary.ReimburseItemType")
public class ReimburseItemType extends DictionaryModel {

    // 加班
    public static final ReimburseItemType RITY_Extra = new ReimburseItemType("RITY_Extra");
    // 车费
    public static final ReimburseItemType RITY_Fare = new ReimburseItemType("RITY_Fare");

    public ReimburseItemType() {
    }

    public ReimburseItemType(String code) {
        super(code);
    }
}
