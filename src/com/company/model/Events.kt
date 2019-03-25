package com.company.com.company.model

import org.jetbrains.exposed.sql.Table
import java.util.*


object Events : Table() {
    val id = integer("id").primaryKey()
    val action = varchar("action", 150)
    val issueNumber = integer("issueNumber")
    val createdAt = date("createdAt")
}

data class Event(
    val id: Int,
    val action: String,
    val issueNumber: Int,
    val createdAt: Date
)

data class NewEvent(
    val id: Int?,
    val action: String,
    val issueNumber: Int
)