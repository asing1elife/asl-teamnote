package com.asing1elife.teamnote.model.dictionary;

import com.asing1elife.teamnote.model.DictionaryModel;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("com.asing1elife.teamnote.model.dictionary.TaskLevel")
public class TaskLevel extends DictionaryModel {

    public static final TaskLevel TALE_Normal = new TaskLevel("TALE_Normal");
    public static final TaskLevel TALE_Urgency = new TaskLevel("TALE_Urgency");
    public static final TaskLevel TALE_Very = new TaskLevel("TALE_Very");

    public TaskLevel() {
    }

    public TaskLevel(String code) {
        super(code);
    }
}
