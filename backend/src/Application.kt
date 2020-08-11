package de.cesure

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.features.ContentNegotiation
import io.ktor.http.content.defaultResource
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.jackson.jackson
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.sessions.Sessions
import io.ktor.sessions.header
import java.math.BigDecimal
import java.time.LocalDate

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
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
            val annuity = BigDecimal(params["annuity"].orEmpty())
            val interestRate = BigDecimal(params["interestRate"].orEmpty())

            val mortgage = AdjustableRateMortgage(
                amount,
                interestStart,
                interestOnlyMonths,
                paymentDay,
                annuity,
                interestRate
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
