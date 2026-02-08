package com.uzuu.learn14_1_db_deeplearn.data.repository

import com.uzuu.learn14_1_db_deeplearn.data.local.dao.PostDao
import com.uzuu.learn14_1_db_deeplearn.data.local.dao.UserDao
import com.uzuu.learn14_1_db_deeplearn.data.local.entity.PostEntity
import com.uzuu.learn14_1_db_deeplearn.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

class DbRepository(
    private val userDao: UserDao,
    private val postDao: PostDao
) {
    fun observeUsers(): Flow<List<UserEntity>> = userDao.observeAll()
    fun observePosts(): Flow<List<PostEntity>> = postDao.observeAll()

    suspend fun addUser(name: String, email: String): Long {
        return userDao.insert(UserEntity(name = name, email = email))
    }

    suspend fun addPost(userId: Long, title: String): Long {
        // Nếu userId không tồn tại => Room sẽ ném lỗi vì foreign key (đúng ý mình)
        return postDao.insert(PostEntity(userId = userId, title = title))
    }

    suspend fun updateUser(id: Long, name: String, email: String): Int {
        val old = userDao.getById(id) ?: return 0
        return userDao.update(old.copy(name = name, email = email))
    }

    suspend fun deleteUserById(id: Long): Int = userDao.deleteById(id)

    suspend fun clearAll(): Unit {
        // chú ý: xóa posts trước hay users trước đều được vì CASCADE,
        // nhưng để “sạch sẽ” thì clear posts trước.
        postDao.clearAll()
        userDao.clearAll()
    }
}