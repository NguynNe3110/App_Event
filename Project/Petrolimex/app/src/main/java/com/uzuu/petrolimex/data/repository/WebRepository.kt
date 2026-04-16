package com.uzuu.petrolimex.data.repository

import com.uzuu.petrolimex.core.result.ApiResult
import com.uzuu.petrolimex.domain.model.Fuel
import com.uzuu.petrolimex.domain.model.Time
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface WebRepository {
    suspend fun syncData() : ApiResult<Unit>

    val fuels: Flow<List<Fuel>>

    val time: Flow<Time>
}