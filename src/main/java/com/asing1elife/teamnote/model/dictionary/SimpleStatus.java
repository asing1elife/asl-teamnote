package com.asing1elife.teamnote.model.dictionary;

import com.asing1elife.teamnote.model.DictionaryModel;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("com.asing1elife.teamnote.model.dictionary.SimpleStatus")
public class SimpleStatus extends DictionaryModel {

    public static final SimpleStatus SIST_Init = new SimpleStatus("SIST_Init");
    public static final SimpleStatus SIST_Active = new SimpleStatus("SIST_Active");
    public static final SimpleStatus SIST_Disable = new SimpleStatus("SIST_Disable");

    public SimpleStatus() {
    }

    public SimpleStatus(String code) {
        super(code);
    }
}
