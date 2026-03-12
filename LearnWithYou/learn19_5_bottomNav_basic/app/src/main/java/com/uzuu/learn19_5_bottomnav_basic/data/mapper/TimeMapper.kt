package com.uzuu.learn19_5_bottomnav_basic.data.mapper

import com.uzuu.learn19_5_bottomnav_basic.data.local.entity.TimeEntity
import com.uzuu.learn19_5_bottomnav_basic.domain.model.Times

fun TimeEntity.toDomain() : Times {
    return Times(
        id =  id,
        timeStart = timeStart,
        timeEnd = timeEnd
    )
}

fun Times.toEntity() : TimeEntity {
    return TimeEntity(
        id =  id,
        timeStart = timeStart,
        timeEnd = timeEnd
    )
}