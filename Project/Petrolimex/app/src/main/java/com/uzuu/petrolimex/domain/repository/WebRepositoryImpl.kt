package com.uzuu.petrolimex.domain.repository

import com.uzuu.petrolimex.core.result.ApiResult
import com.uzuu.petrolimex.data.local.datasource.DataLocalSource
import com.uzuu.petrolimex.data.local.entity.FuelEntity
import com.uzuu.petrolimex.data.local.entity.TimeEntity
import com.uzuu.petrolimex.data.mapper.fuelDomainToEntity
import com.uzuu.petrolimex.data.mapper.fuelEntityToDomain
import com.uzuu.petrolimex.data.mapper.timeDomainToEntity
import com.uzuu.petrolimex.data.mapper.timeEntityToDomain
import com.uzuu.petrolimex.data.remote.datasource.RemoteDataSource
import com.uzuu.petrolimex.data.remote.dto.FuelResponse
import com.uzuu.petrolimex.data.repository.WebRepository
import com.uzuu.petrolimex.domain.model.Fuel
import com.uzuu.petrolimex.domain.model.Time
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

import javax.inject.Inject


class WebRepositoryImpl @Inject constructor(
    private val dataLocal: DataLocalSource,
    private val dataRemote: RemoteDataSource
) : WebRepository {
    override val fuels: Flow<List<Fuel>> =
        dataLocal.observeFuels().filterNotNull().map { listEntity ->
            listEntity.filterNotNull().map { it.fuelEntityToDomain() }
        }

    override val time: Flow<Time> =
        dataLocal.observeTime().map { listE ->
            listE?.timeEntityToDomain() ?: Time("Chưa có dữ liệu")
        }

    override suspend fun syncData(): ApiResult<Unit> {
        return when (val result = dataRemote.getData()) {

            is ApiResult.Success -> {
                val data = result.data

                dataLocal.insertTime(TimeEntity(time = data.time.toString()))

                dataLocal.insertFuel(
                    data.fuels.map {
                        it.fuelDomainToEntity()
                    }
                )
                ApiResult.Success(Unit)
            }

            is ApiResult.Error -> {
                ApiResult.Error(result.message, result.throwable)
            }
        }
    }
}