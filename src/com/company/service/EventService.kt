package com.company.service

import com.company.dto.EventDto

interface EventService {

    suspend fun getAllEventsByIssueNumber(issueNumber: Int): List<EventDto>
}
