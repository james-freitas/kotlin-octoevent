package com.company.com.company.model

import org.jetbrains.exposed.sql.Table

object EventDao : Table() {
    val id = integer("id").primaryKey()
    val action = varchar("action", 150)
    val createdAt = date("createdAt")
    val issueNumber = integer("issueNumber")
}