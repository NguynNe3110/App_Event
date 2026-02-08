package com.uzuu.learn3_roomdb_basic.data.mapper

import com.uzuu.learn3_roomdb_basic.data.local.UserEntity
import com.uzuu.learn3_roomdb_basic.domain.model.User

fun UserEntity.toDomain(): User {
    return User(
        id = id,
        displayName = name
    )
}