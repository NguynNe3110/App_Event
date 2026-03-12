package com.uzuu.learn19_4_practice_nav_isolation.feature.detail

import com.uzuu.learn19_4_practice_nav_isolation.domain.model.Products

data class DetailUiState (
    val product: Products ?= null
)