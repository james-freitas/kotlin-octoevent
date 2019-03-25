package com.company.com.company.model

import org.joda.time.DateTime

data class Event(
    val id: Int,
    val action: String,
    val issueNumber: Int,
    val createdAt: DateTime
)