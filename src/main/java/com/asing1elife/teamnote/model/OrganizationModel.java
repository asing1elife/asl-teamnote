package com.asing1elife.teamnote.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author asing1elife
 */
@Data
@Entity
@Table(name = "al_organization")
public class OrganizationModel extends BaseModel {

    @JsonIgnore
    @ManyToOne
    private UserModel user;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private Boolean star = false;

    @Column
    private Boolean display = true;

    public OrganizationModel() {
    }

    public OrganizationModel(long id) {
        super.setId(id);
    }
}
