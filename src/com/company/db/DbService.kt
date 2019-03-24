package com.company.com.company.db

import org.jetbrains.exposed.sql.Database

object DbService {
    init {
        Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
            driver = "org.h2.Driver")
    }
}