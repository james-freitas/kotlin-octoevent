package com.company.service

import com.company.dto.EventDto

interface EventService {

    fun getAllEventsByIssueNumber(issueNumber: Int): List<EventDto>
}
