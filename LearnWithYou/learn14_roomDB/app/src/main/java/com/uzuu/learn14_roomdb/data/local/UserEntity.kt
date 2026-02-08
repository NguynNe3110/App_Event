package com.uzuu.learn14_roomdb.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

// khai báo class này là table
//tableName để đặt tên bảng, nếu k thì room tự đặt theo class name
@Entity(tableName = "users")
// khai bao cac cot du lieu
data class UserEntity(
    //khai bao khoa chinh
    @PrimaryKey val id: Int,
    val name: String
)
