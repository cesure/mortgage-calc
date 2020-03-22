package de.cesure

import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.datatype.jsr310.*
import com.fasterxml.jackson.module.kotlin.*
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
            val interestStart = LocalDate.parse(params["interestStart"].orEmpty().take(10))
            val interestOnlyMonths = params["interestOnlyMonths"].orEmpty().toInt()
            val paymentDay = params["paymentDay"].orEmpty().toInt()
            val annuity = BigDecimal(params["annuity"].orEmpty())
            val interestRates = params.getAll("interestRates[]").orEmpty().toInterestRates()

            val mortgage = AdjustableRateMortgage(
                amount,
                interestStart,
                interestOnlyMonths,
                paymentDay,
                annuity,
                interestRates
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

private data class Session(val id: String)

private data class InterestRate(val date: LocalDate, val rate: BigDecimal)

private fun List<String>.toInterestRates(): TreeMap<LocalDate, BigDecimal> {
    val mapper = jacksonObjectMapper().registerModule(JavaTimeModule())
    return this.map { json ->
        mapper.readValue<InterestRate>(json).let { ir -> ir.date to ir.rate }
    }.toMap(TreeMap())
}

