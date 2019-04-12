package com.asing1elife.teamnote.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "al_config")
public class ConfigModel extends BaseModel {

    @Column
    private String code;

    @Column
    private String value;

    @Column
    private String description;

}
