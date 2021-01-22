package com.github.cesure.mortgagecalc

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.serialization.json
import io.ktor.server.netty.EngineMain

fun main(args: Array<String>): Unit = EngineMain.main(args)

@Suppress("unused")
fun Application.main() {
    installFeatures()

    routing {
        get("/") {
            call.respondText {
                val asd = "ddd"
                val def = "fff"
                "Hello $asd $def!"
            }
        }
    }

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
