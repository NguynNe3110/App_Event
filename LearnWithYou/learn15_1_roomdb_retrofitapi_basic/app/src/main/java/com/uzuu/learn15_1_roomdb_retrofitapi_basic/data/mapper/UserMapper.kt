package com.uzuu.learn15_1_roomdb_retrofitapi_basic.data.mapper

import com.uzuu.learn15_1_roomdb_retrofitapi_basic.data.local.entity.UserEntity
import com.uzuu.learn15_1_roomdb_retrofitapi_basic.data.remote.UserDto
import com.uzuu.learn15_1_roomdb_retrofitapi_basic.domain.model.User

fun UserEntity.toDomain(): User = User(
    id = id,
    displayName = name
)

fun UserDto.toEntity(): UserEntity = UserEntity(
    id = id,
    name = name
)