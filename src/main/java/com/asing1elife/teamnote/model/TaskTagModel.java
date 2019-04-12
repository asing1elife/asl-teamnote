package com.asing1elife.teamnote.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "al_task_tag")
public class TaskTagModel extends BaseModel {

    @Column
    private String name;

    @Column
    private String color;

}
