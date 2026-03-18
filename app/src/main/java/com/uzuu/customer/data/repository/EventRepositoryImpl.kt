package com.uzuu.customer.data.repository

import com.uzuu.customer.data.mapper.eventDtoToDomain
import com.uzuu.customer.data.remote.datasource.EventRemoteDataSource
import com.uzuu.customer.domain.model.Event
import com.uzuu.customer.domain.model.PagedResult
import com.uzuu.customer.domain.repository.EventRepository

class EventRepositoryImpl(
    private val eventRemote: EventRemoteDataSource
): EventRepository {
    override suspend fun getEvent(page: Int): PagedResult<Event> {
        val response = eventRemote.getEvent(page)
        val page = response.result

        return PagedResult(
            data = page.content.map { it.eventDtoToDomain() },
            page = page.number,
            totalPages = page.totalPages,
            totalElements = page.totalElements,
            isLast = page.last
        )
    }
}