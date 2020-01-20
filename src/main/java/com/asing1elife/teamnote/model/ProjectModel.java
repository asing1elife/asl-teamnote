package com.asing1elife.teamnote.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "al_project")
public class ProjectModel extends BaseModel {

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private Integer indexNo;

    @Column
    private Integer taskNum = 0;

    @ManyToOne
    private OrganizationModel organization;

    public void addTaskNum() {
        setTaskNum(getTaskNum() + 1);
    }

    public void reduceTaskNum() {
        setTaskNum(getTaskNum() - 1);
    }

}
