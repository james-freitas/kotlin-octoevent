package com.company.service

import com.company.model.Event
import java.util.*

class EventServiceImpl: EventService {

    private var eventsDB: List<Event> = listOf(
        Event("open", Date(), 1),
        Event("closed", Date(), 1),
        Event("closed", Date(), 2),
        Event("closed", Date(), 2)
    )

    override fun getAllEventsByIssueNumber(issueNumber: Int): List<Event> {
        return eventsDB.filter { e -> e.issueNumber == issueNumber }
    }
}
