package com.company

import com.fasterxml.jackson.databind.*
import io.ktor.jackson.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.features.CallLogging
import io.ktor.features.Compression
import io.ktor.features.DefaultHeaders
import io.ktor.features.ContentNegotiation



fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    install(DefaultHeaders)
    install(Compression)
    install(CallLogging)
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
    }
}

