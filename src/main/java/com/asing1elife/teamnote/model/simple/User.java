/*****************************************************************************
 * Copyright (c) 2020, www.shixun.online
 *
 * All rights reserved
 *
 *****************************************************************************/
package com.asing1elife.teamnote.model.simple;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class User {

    private String username;

    private String nickname;

    private String gender;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

}
