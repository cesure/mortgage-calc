package com.github.cesure.mortgagecalc

import com.github.cesure.mortgagecalc.model.Mortgage
import dev.fritz2.binding.RootStore
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object MortgageStore : RootStore<Mortgage>(
    Mortgage(
        amount = 100_000 * 100,
        annuity = 1_234 * 100,
        interestStart = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
        paymentDay = 30,
        interestRate = 175,
        interestOnlyMonths = 1,
    )
)
