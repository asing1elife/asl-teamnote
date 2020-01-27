/*****************************************************************************
 * Copyright (c) 2020, www.shixun.online
 *
 * All rights reserved
 *
 *****************************************************************************/
package com.asing1elife.teamnote.module.user.repository;

import com.asing1elife.teamnote.core.repository.BaseRepository;
import com.asing1elife.teamnote.model.UserModel;

public interface UserRepository extends BaseRepository<UserModel, Long> {

    UserModel getByEmailOrMobile(String email, String mobile);

}
