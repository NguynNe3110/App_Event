package com.uzuu.learn20_recycler_basic.domain.repository

import com.uzuu.learn20_recycler_basic.domain.model.User
import kotlinx.coroutines.delay

class UserRepository {

    // giả lập gọi API/DB
    suspend fun getUsers(): List<User> {
        delay(600) // giả lập loading
        return (1..30).map { id ->
            User(id = id, displayName = "User #$id")
        }
    }
}