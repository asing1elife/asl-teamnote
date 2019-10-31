package com.asing1elife.teamnote.model;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

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

    public String getClassName() {
        String fullClassName = this.getClass().getName();

        return fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
    }

    public String getRealCode() {
        return StringUtils.isEmpty(code) ? "" : code.substring(code.indexOf("_") + 1).toLowerCase();
    }

}
