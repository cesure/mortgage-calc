package com.github.cesure.mortgagecalc

import com.github.cesure.mortgagecalc.model.Decimal
import com.github.cesure.mortgagecalc.model.Payment
import com.github.cesure.mortgagecalc.model.PaymentType
import com.github.cesure.mortgagecalc.model.repaymentPlan
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
import kotlinx.datetime.LocalDate

fun main(args: Array<String>): Unit = EngineMain.main(args)

@Suppress("unused")
fun Application.main() {
    installFeatures()

    routing {
        get("/api/repaymentPlan") {
            val mortgage = call.parameters["mortgage"]?.let {
                MortgageSerializer.read(it)
            }
            log.info("Received mortgage: $mortgage")

            val payment = Payment(
                type = PaymentType.PAYOUT,
                date = LocalDate(2020, 1, 1),
                amount = Decimal(10_000)
            )
            call.respond(
                repaymentPlan(payment)
            )
        }
    }
}

fun Application.installFeatures() {
    install(ContentNegotiation) {
        json()
    }
}
