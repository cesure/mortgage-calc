package com.github.cesure.mortgagecalc

import com.github.cesure.mortgagecalc.model.Mortgage
import dev.fritz2.binding.RootStore

object MortgageStore : RootStore<Mortgage>(Mortgage())
