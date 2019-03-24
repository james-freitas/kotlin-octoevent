package com.company.service

import com.company.com.company.model.Events
import com.company.com.company.service.DatabaseFactory.dbQuery
import com.company.dto.EventDto
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.joda.time.format.DateTimeFormat


class EventServiceImpl: EventService {

    override suspend fun getAllEventsByIssueNumber(issueNumber: Int): List<EventDto> = dbQuery {

        Events.select {
            (Events.issueNumber eq issueNumber)
        }.mapNotNull { toEventDto(it) }
    }

    private fun toEventDto(event: ResultRow): EventDto? {

        val dateTimeFormat = DateTimeFormat.forPattern("dd/MM/yyyy hh:mm:ss")

        return EventDto(
            id = event[Events.id],
            action = event[Events.action],
            createdAt = event[Events.createdAt].toString(dateTimeFormat),
            issueNumber = event[Events.issueNumber]
        )
    }
}
