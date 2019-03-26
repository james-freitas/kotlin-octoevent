package com.company.service

import com.company.com.company.model.Event
import com.company.com.company.model.Events
import com.company.com.company.service.DatabaseFactory.dbQuery
import com.company.dto.EventDto
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat


class EventServiceImpl: EventService {

    val dateFormatOut = DateTimeFormat.forPattern("dd/MM/yyyy hh:mm:ss")
    val dateFormatIn = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")

    override suspend fun addEvent(event: Event): EventDto {

        var key = 0
        dbQuery {
            key = (Events.insert {
                it[action] = event.action
                it[createdAt] =  DateTime.parse(event.issue.created_at, dateFormatIn)
                it[issueNumber] = event.issue.number
            } get Events.id)!!
        }
        return getEventById(key)!!
    }

    override suspend fun getEventById(id: Int): EventDto? = dbQuery {
        Events.select {
            (Events.id eq id)
        }.mapNotNull { toEventDto(it) }
            .singleOrNull()
    }

    override suspend fun getAllEventsByIssueNumber(issueNumber: Int): List<EventDto> = dbQuery {

        Events.select {
            (Events.issueNumber eq issueNumber)
        }.mapNotNull { toEventDto(it) }
    }

    private fun toEventDto(event: ResultRow): EventDto? {

        return EventDto(
            id = event[Events.id],
            action = event[Events.action],
            createdAt = event[Events.createdAt].toString(dateFormatOut),
            issueNumber = event[Events.issueNumber]
        )
    }


}
