package com.company.service

import com.company.model.Event

interface EventService {

    fun getAllEventsByIssueNumber(issueNumber: Int): List<Event>
}
