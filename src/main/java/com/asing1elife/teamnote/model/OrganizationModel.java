package com.asing1elife.teamnote.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "al_organization")
public class OrganizationModel extends BaseModel {

    @Column
    private String name;

    @Column
    private String description;

    // 是否星标
    @Column
    private Boolean isStar = false;

    public OrganizationModel() {
    }

    public OrganizationModel(long id) {
        super.setId(id);
    }
}
