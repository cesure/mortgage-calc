package de.cesure

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.http.content.*
import io.ktor.jackson.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.netty.*
import io.ktor.sessions.*
import java.math.BigDecimal
import java.time.LocalDate

fun main(args: Array<String>): Unit = EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@JvmOverloads
fun Application.module(testing: Boolean = false) {
    installFeatures()

    routing {
        static {
            resources("static")
            defaultResource("static/index.html")
        }

        get("/repaymentPlan") {
            val params = call.parameters

            val amount = BigDecimal(params["amount"].orEmpty())
            val interestStart = LocalDate.parse(params["interestStart"].orEmpty().take(10))
            val interestOnlyMonths = params["interestOnlyMonths"].orEmpty().toInt()
            val paymentDay = params["paymentDay"].orEmpty().toInt()
            val useAnnuity = params["useAnnuity"]?.toBoolean() ?: true
            val annuity = params["annuity"]?.takeIf { useAnnuity }?.let { BigDecimal(it) }
            val downPaymentRate = params["downPaymentRate"]?.takeUnless { useAnnuity }?.let { BigDecimal(it) }
            val interestRate = BigDecimal(params["interestRate"].orEmpty())

            val mortgage = Mortgage(
                amount,
                interestStart,
                interestOnlyMonths,
                paymentDay,
                annuity,
                downPaymentRate,
                interestRate,
                emptyList()
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
