package com.uzuu.learn12_1_resfulapi_basic.feature.main

sealed class UiItem {
    // vì reccycler đang hiển thị 2 dữ liệu khác nhau, vì thế cần 1 cái để quản lý chúng
    // trả lời cho câu hỏi recycler hiển thị các dữ liệu khác nhau kiểu gì
    data class PostItem(val id: Int, val line1: String, val line2: String) : UiItem()
    data class CommentItem(val id: Int, val line1: String, val line2: String) : UiItem()
}