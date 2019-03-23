package com.company.service

import com.company.dto.EventDto
import com.company.model.Event
import java.text.SimpleDateFormat
import java.util.*

class EventServiceImpl: EventService {

    private var eventsDB: List<Event> = listOf(
        Event("open", Date(), 1),
        Event("closed", Date(), 1),
        Event("closed", Date(), 2),
        Event("closed", Date(), 2)
    )

    override fun getAllEventsByIssueNumber(issueNumber: Int): List<EventDto> {
        val format = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
        return eventsDB
            .filter { e -> e.issueNumber == issueNumber }
            .map { e -> EventDto(e.action, format.format(e.createdAt), e.issueNumber) }
    }
}
