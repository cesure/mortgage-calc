package de.cesure

import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.datatype.jsr310.*
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.jackson.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import java.math.*
import java.time.*
import java.util.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    installFeatures()

    routing {
        get("/repaymentPlan") {
            val params = call.parameters

            val amount = BigDecimal(params["amount"].orEmpty())
            val interestStart = LocalDate.parse(params["interestStart"].orEmpty())
            val interestOnlyMonths = params["interestOnlyMonths"].orEmpty().toIntOrNull() ?: 0
            val paymentDay = params["paymentDay"]?.toIntOrNull() ?: 31
            val annuity = BigDecimal(params["annuity"].orEmpty())
            val interestRates = params["interestRates"].orEmpty()
                .split(",")
                .map { token -> token.split(":") }
                .map { LocalDate.parse(it.first()) to BigDecimal(it.last()) }
                .toMap()

            val mortgage = AdjustableRateMortgage(
                amount,
                interestStart,
                interestOnlyMonths,
                paymentDay,
                annuity,
                TreeMap(interestRates)
            )

            call.respond(mortgage.repaymentPlan())
        }
    }
}

fun Application.installFeatures() {
    install(Authentication) {
    }

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
            disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            registerModule(JavaTimeModule())
        }
    }

    install(Sessions) {
        header<Session>("Session")
    }
}

data class Session(val id: String)
