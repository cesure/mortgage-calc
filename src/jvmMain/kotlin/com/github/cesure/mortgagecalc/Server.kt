package com.github.cesure.mortgagecalc

import com.github.cesure.mortgagecalc.model.RepaymentPlan
import com.github.cesure.mortgagecalc.model.serialization.MortgageSerializer
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.application.log
import io.ktor.features.ContentNegotiation
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.serialization.json
import io.ktor.server.netty.EngineMain

fun main(args: Array<String>): Unit = EngineMain.main(args)

@Suppress("unused")
fun Application.main() {
    installFeatures()

    routing {
        get("/api/repaymentPlan") {
            val mortgage = call.parameters["mortgage"]?.let {
                MortgageSerializer.read(it)
            }
            log.info("Got mortgage: $mortgage")

            val response = RepaymentPlan()
            call.respond(response)
        }
    }
}

fun Application.installFeatures() {
    install(ContentNegotiation) {
        json()
    }
}
