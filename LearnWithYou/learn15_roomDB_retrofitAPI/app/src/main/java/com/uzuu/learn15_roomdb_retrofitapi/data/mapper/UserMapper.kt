package com.uzuu.learn15_roomdb_retrofitapi.data.mapper

import com.uzuu.learn15_roomdb_retrofitapi.data.local.entity.UserEntity
import com.uzuu.learn15_roomdb_retrofitapi.data.remote.UserDto
import com.uzuu.learn15_roomdb_retrofitapi.domain.model.User

fun UserEntity.toDomain(): User = User(
    id = id,
    displayName = name
)

fun UserDto.toEntity(): UserEntity = UserEntity(
    id = id,
    name = name
)