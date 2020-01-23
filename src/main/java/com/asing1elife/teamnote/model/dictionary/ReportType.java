package com.asing1elife.teamnote.model.dictionary;

import com.asing1elife.teamnote.model.DictionaryModel;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("com.asing1elife.teamnote.model.dictionary.ReportType")
public class ReportType extends DictionaryModel {

    public static final ReportType RETY_Month = new ReportType("RETY_Month");
    public static final ReportType RETY_Year = new ReportType("RETY_Year");

    public ReportType() {
    }

    public ReportType(String code) {
        super(code);
    }
}
