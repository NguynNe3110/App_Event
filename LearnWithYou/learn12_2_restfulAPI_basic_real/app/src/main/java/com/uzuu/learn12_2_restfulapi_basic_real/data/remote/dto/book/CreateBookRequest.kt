package com.uzuu.learn12_2_restfulapi_basic_real.data.remote.dto.book

import com.uzuu.learn12_2_restfulapi_basic_real.data.remote.dto.CategoryDto

    data class CreateBookRequest(
        val title: String,
        val quantity: Int,
        //neu server tra ve list thi phai la list
        val category: categoryRef
    )

    data class categoryRef(val id: Int)



