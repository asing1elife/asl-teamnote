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

    @ManyToOne
    private OrganizationModel organization;

    // @OrderBy("status_code DESC, level_code DESC")
    // @OneToMany(mappedBy = "project")
    // @JsonManagedReference
    // private List<TaskModel> tasks = Lists.newArrayList();

}
