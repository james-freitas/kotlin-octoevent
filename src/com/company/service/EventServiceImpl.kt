package com.company.service

import com.company.com.company.model.Event
import com.company.com.company.model.EventDao
import com.company.com.company.service.DatabaseFactory.dbQuery
import com.company.dto.EventDto
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat


class EventServiceImpl: EventService {

    val dateTimeFormat = DateTimeFormat.forPattern("dd/MM/yyyy hh:mm:ss")

    override fun addEvent(event: Event): EventDto {
        return EventDto(1, "closed", DateTime.now().toString(dateTimeFormat), 1)
    }

    override suspend fun getAllEventsByIssueNumber(issueNumber: Int): List<EventDto> = dbQuery {

        EventDao.select {
            (EventDao.issueNumber eq issueNumber)
        }.mapNotNull { toEventDto(it) }
    }

    private fun toEventDto(event: ResultRow): EventDto? {

        return EventDto(
            id = event[EventDao.id],
            action = event[EventDao.action],
            createdAt = event[EventDao.createdAt].toString(dateTimeFormat),
            issueNumber = event[EventDao.issueNumber]
        )
    }
}
