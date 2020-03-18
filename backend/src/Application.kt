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

    routing {
        get("/repaymentPlan") {
            val interestStart = LocalDate.of(2020, 1, 24)
            val interestsOnlyMonth = 1

            val mortgage = AdjustableRateMortgage(
                amount = BigDecimal(83500),
                interestStart = interestStart,
                interestsOnlyMonths = interestsOnlyMonth,
                paymentDay = 30,
                annuity = BigDecimal("278.34"),
                interestRates = TreeMap(mapOf(interestStart to BigDecimal(2)))
            )

            call.respond(mortgage.repaymentPlan())
        }
    }
}

data class Session(val id: String)
