package com.uzuu.learn12_2_restfulapi_basic_real.data.mapper

import androidx.room.Entity
import com.uzuu.learn12_2_restfulapi_basic_real.data.local.entity.UsersEntity
import com.uzuu.learn12_2_restfulapi_basic_real.data.remote.dto.user.CreateUserRequest
import com.uzuu.learn12_2_restfulapi_basic_real.data.remote.dto.user.UpdateUserRequest
import com.uzuu.learn12_2_restfulapi_basic_real.data.remote.dto.user.UserDto
import com.uzuu.learn12_2_restfulapi_basic_real.domain.model.user.CreateUser
import com.uzuu.learn12_2_restfulapi_basic_real.domain.model.user.UpdateUser
import com.uzuu.learn12_2_restfulapi_basic_real.domain.model.user.Users

fun UsersEntity.entityToDomain(): Users {
    return Users(
        id = id,
        displayName = username,
        fullName = fullName
    )
}

fun CreateUserRequest.domainCreateRequestToEntity() : UsersEntity = UsersEntity(
    id = 0,
    username  = username,
    password = password,
    fullName = fullName
)

fun CreateUser.domainCreateToEntity(): UsersEntity{
    return UsersEntity(
        id = 0,
        username = username,
        password = password,
        fullName = fullName
    )
}

// hayyyyyyyyyyyyyyyyyyy
fun UpdateUser.mergeToEntity(old: UsersEntity) : UsersEntity{
    return old.copy(
        id = id,
        username = username ?: old.username,
        password = password ?: old.password,
        fullName = fullName ?: old.fullName
    )
}

// api

fun UserDto.userDtoToEntity() : UsersEntity{
    return UsersEntity(
        id = id,
        username = username,
        password = password,
        fullName = fullName
    )
}


