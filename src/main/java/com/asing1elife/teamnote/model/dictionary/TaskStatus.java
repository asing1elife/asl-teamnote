package com.asing1elife.teamnote.model.dictionary;

import com.asing1elife.teamnote.model.DictionaryModel;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("com.asing1elife.teamnote.model.dictionary.TaskStatus")
public class TaskStatus extends DictionaryModel {

    public static final TaskStatus TAST_Init = new TaskStatus("TAST_Init");
    public static final TaskStatus TAST_Impl = new TaskStatus("TAST_Impl");
    public static final TaskStatus TAST_Finish = new TaskStatus("TAST_Finish");

    public TaskStatus() {
    }

    public TaskStatus(String code) {
        super(code);
    }
}
