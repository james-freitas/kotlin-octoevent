package com.company

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.html.*
import kotlinx.html.*
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import kotlin.test.*
import io.ktor.server.testing.*

class ApplicationTest {
    @Test
    fun testRoot() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("This is root endpoint.", response.content)
            }
        }
    }

    @Test  // GET /issues/1000/events
    fun testGettingEventsByIssueNumber1000() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/issues/1000/events").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertNotNull(response.content)
            }
        }
    }
}
