package com.company

import com.company.com.company.di.dependencyInjectionModule
import com.company.com.company.model.Event
import com.company.com.company.service.DatabaseFactory
import com.company.service.EventService
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.jackson.jackson
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import org.koin.ktor.ext.inject
import org.koin.ktor.ext.installKoin
import java.lang.Integer.parseInt


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }

    install(DefaultHeaders)
    install(CallLogging)
    installKoin(listOf(dependencyInjectionModule))

    DatabaseFactory.init()

    val mapper = jacksonObjectMapper().apply {
        setSerializationInclusion(JsonInclude.Include.NON_NULL)
    }

    val client = HttpClient(Apache) {
    }

    routing {

        val eventService by inject<EventService>()

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
                call.respond(eventService.getAllEventsByIssueNumber(issueNumber))

            } catch (e: NumberFormatException) {
                val result = emptyList<Event>()
                call.respond(result)
            }
        }
    }
}
