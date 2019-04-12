package com.asing1elife.teamnote.model;

import com.asing1elife.teamnote.model.dictionary.TaskLevel;
import com.asing1elife.teamnote.model.dictionary.TaskStatus;
import com.google.common.collect.Lists;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
@Table(name = "al_task")
public class TaskModel extends BaseModel {

    @Column
    private String name;

    @Column
    private String description;

    @OneToOne
    private TaskTagModel taskTag;

    @ManyToOne
    private ProjectModel project;

    @ManyToOne
    private TaskLevel level = TaskLevel.TALE_Normal;

    @ManyToOne
    private TaskStatus status = TaskStatus.TAST_Init;

    @Column
    private Timestamp planBeginDate;

    @Column
    private Timestamp planFinishDate;

    @Column
    private Timestamp beginDate;

    @Column
    private Timestamp finishDate;

}
