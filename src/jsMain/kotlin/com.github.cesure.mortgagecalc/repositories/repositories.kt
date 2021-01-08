package com.github.cesure.mortgagecalc.repositories

import com.github.cesure.mortgagecalc.model.Mortgage
import dev.fritz2.binding.RootStore

val mortgageStore = object : RootStore<Mortgage>(Mortgage()) {}
