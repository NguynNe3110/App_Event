package com.uzuu.learn20_2_recycler_dao.data.mapper

import com.uzuu.learn20_2_recycler_dao.data.local.entity.UserEntity
import com.uzuu.learn20_2_recycler_dao.domain.model.User


fun UserEntity.entityToDomain(): User {
    return User(
        id = id,
        displayName = name
    )
}

fun User.domainToEntity(): UserEntity {
    return UserEntity(
        id = id,
        name = displayName
    )
}