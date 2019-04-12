package com.asing1elife.teamnote.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "sys_dictionary")
@DiscriminatorColumn(name = "category")
public class DictionaryModel {

    @Id
    private String code;

    @Column
    private String name;

    @Column
    private Integer indexNo;

    public DictionaryModel() {
    }

    public DictionaryModel(String code) {
        this.code = code;
    }
}
