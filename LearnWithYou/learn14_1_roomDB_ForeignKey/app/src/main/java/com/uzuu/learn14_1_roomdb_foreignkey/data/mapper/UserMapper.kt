package com.uzuu.learn14_1_roomdb_foreignkey.data.mapper

import com.uzuu.learn14_1_roomdb_foreignkey.data.local.entity.UserEntity
import com.uzuu.learn14_1_roomdb_foreignkey.domain.model.User

fun UserEntity.toDomain(): User =
    User(
        id = id,
        nameUser = nameStudent,
        idClass = idClass
    )