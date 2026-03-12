package com.uzuu.learn12_1_resfulapi_basic.data.mapper

import com.uzuu.learn12_1_resfulapi_basic.data.remote.dto.CommentDto
import com.uzuu.learn12_1_resfulapi_basic.data.remote.dto.PostDto
import com.uzuu.learn12_1_resfulapi_basic.domain.model.Comment
import com.uzuu.learn12_1_resfulapi_basic.domain.model.Post

fun PostDto.toDomain(): Post = Post(userId, id, title, body)
fun CommentDto.toDomain(): Comment = Comment(postId, id, name, email, body)