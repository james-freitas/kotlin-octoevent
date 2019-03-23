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

    @Test
    fun testJsonTestEndpoint() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/jsontest").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("{\n" +
                        "  \"format\" : \"Json\",\n" +
                        "  \"enabled\" : true\n" +
                        "}", response.content)
            }
        }
    }


    @Test  // GET /issues/1/events
    fun testGettingEventsByIssueNumber1() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/issues/1/events").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertNotNull(response.content)
            }
        }
    }

}
