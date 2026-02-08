package com.uzuu.learn14_1_roomdb_foreignkey.data.mapper

import com.uzuu.learn14_1_roomdb_foreignkey.data.local.entity.ClassEntity
import com.uzuu.learn14_1_roomdb_foreignkey.data.local.entity.UserEntity
import com.uzuu.learn14_1_roomdb_foreignkey.domain.model.Classes
import com.uzuu.learn14_1_roomdb_foreignkey.domain.model.User

fun ClassEntity.toDomain(): Classes {
    return Classes(
        id = id,
        nameClass = name
    )
}
