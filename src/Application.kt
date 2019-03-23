package com.company

import com.company.model.Event
import com.company.service.EventServiceImpl
import com.fasterxml.jackson.databind.*
import io.ktor.jackson.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.features.ContentNegotiation
import java.lang.Integer.parseInt
import java.lang.NumberFormatException


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }

    val client = HttpClient(Apache) {
    }

    routing {

        get("/") {
            call.respondText { "This is root endpoint." }
        }

        get("/jsontest") {
            data class DataFormat(val format: String, val enabled: Boolean){}
            val json = DataFormat("Json", true)
            call.respond(json)
        }

        get("/issues/{issueNumber}/events") {
            val issueAsString = call.parameters["issueNumber"]

            try {
                val issueNumber = parseInt(issueAsString)
                val eventService = EventServiceImpl()
                val eventList = eventService.getAllEventsByIssueNumber(issueNumber)
                call.respond(eventList)

            } catch (e: NumberFormatException) {
                val result = emptyList<Event>()
                call.respond(result)
            }
        }
    }
}

