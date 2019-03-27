package com.company

import com.company.com.company.model.Events
import com.company.com.company.service.DatabaseFactory
import com.company.dto.EventDto
import com.google.gson.Gson
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication
import org.assertj.core.api.Assertions.assertThat
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
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
            Events.deleteAll()
            Events.insert {
                it[id] = 1
                it[action] = "open"
                it[issueNumber] = 1
                it[createdAt] = DateTime.now()
            }
            Events.insert {
                it[id] = 2
                it[action] = "closed"
                it[issueNumber] = 1
                it[createdAt] = DateTime.now().plusDays(1)
            }
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

    @Test
    fun testGettingEventsByIssueNumber() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/issues/1/events").apply {

                val eventDtoList = Gson().fromJson(response.content, Array<EventDto>::class.java).asList()
                assertEquals(HttpStatusCode.OK, response.status())
                assertNotNull(response.content)
                assertThat(eventDtoList).hasSize(2)
            }
        }
    }

    @Test
    fun testPostingEvents() {
        withTestApplication ({ module(testing = true) }) {

            handleRequest(HttpMethod.Post, "/payload"){
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody("{ \"action\":\"opened\", \"issue\":{ \"number\":5, \"created_at\":\"2019-03-24T21:40:18Z\" } }")
            }.apply {
                val eventCreated = Gson().fromJson(response.content, EventDto::class.java)
                assertEquals(HttpStatusCode.Created, response.status())
                assertNotNull(response.content)
                assertThat("opened").isEqualTo(eventCreated.action)
                assertThat(5).isEqualTo(eventCreated.issueNumber)
                assertThat("2019-03-24T21:40:18Z").isEqualTo(eventCreated.createdAt)
            }
        }
    }
}
