/*****************************************************************************
 * Copyright (c) 2020, www.shixun.online
 *
 * All rights reserved
 *
 *****************************************************************************/
package com.asing1elife.teamnote.model;

import com.asing1elife.teamnote.model.dictionary.SimpleStatus;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "al_user")
public class UserModel extends BaseModel {

    @Column
    private String email;

    @Column
    private String mobile;

    @Column
    private String password;

    @Column
    private String salt;

    @Column
    private String nickname;

    @Column
    private String gender;

    @Column
    private Date birthday;

    @ManyToOne
    private SimpleStatus status = SimpleStatus.SIST_Init;

    public UserModel() { }

    public UserModel(long id) {
        super.setId(id);
    }
}
