package com.company.service

import com.company.com.company.model.Event
import com.company.dto.EventDto

interface EventService {

    suspend fun getAllEventsByIssueNumber(issueNumber: Int): List<EventDto>

    suspend fun addEvent(event: Event): EventDto

    suspend fun getEventById(id: Int): EventDto?
}
