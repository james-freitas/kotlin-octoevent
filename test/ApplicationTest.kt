package com.company

import com.company.com.company.model.EventDao
import com.company.com.company.service.DatabaseFactory
import com.company.dto.EventDto
import com.google.gson.Gson
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.Before
import java.util.logging.Logger
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ApplicationTest {

    @Before
    fun setUp() {
        DatabaseFactory.init()
        transaction {
            EventDao.deleteAll()
        }
    }

    @Test
    fun testRoot() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("This is root endpoint.", response.content)
            }
        }
    }

    @Test  // GET /issues/1/events
    fun testGettingEventsByIssueNumber() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/issues/1/events").apply {

                val eventDtoList = Gson().fromJson(response.content, Array<EventDto>::class.java).asList()
                assertEquals(HttpStatusCode.OK, response.status())
                assertNotNull(response.content)
                assertThat(eventDtoList).hasSize(1)
            }
        }
    }

    @Test
    fun testPostingEvents() {
        withTestApplication ({ module(testing = true) }) {
            handleRequest(HttpMethod.Post, "/payload").apply {
                Logger.getLogger(Test::class.java.name).info("Posting being called..")
                assertEquals(HttpStatusCode.Created, response.status())
            }
        }
    }
}
