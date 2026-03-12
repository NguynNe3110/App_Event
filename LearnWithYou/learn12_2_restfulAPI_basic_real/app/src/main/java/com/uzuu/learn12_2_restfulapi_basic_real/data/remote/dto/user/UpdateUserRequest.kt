package com.uzuu.learn12_2_restfulapi_basic_real.data.remote.dto.user

data class UpdateUserRequest(
    //ở mức độ product sẽ có nhiều trường dl hơn khi ấy
    //khi ấy những trường dữ liệu k set not null thì có thể ? để null
    val username: String?,
    val password: String?,
    val fullName: String?
)
