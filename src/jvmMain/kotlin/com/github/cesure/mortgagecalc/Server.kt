package com.github.cesure.mortgagecalc

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.netty.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

@Suppress("unused")
fun Application.main() {
    installFeatures()

    routing {
        get("/api/repaymentPlan") {
            call.respond(emptyMap<String, Any>())
        }
    }
}

fun Application.installFeatures() {
    install(ContentNegotiation) {
        json()
    }
}
