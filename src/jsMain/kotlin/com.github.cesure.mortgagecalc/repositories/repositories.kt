package com.github.cesure.mortgagecalc.repositories

import com.github.cesure.mortgagecalc.model.Mortgage
import com.github.cesure.mortgagecalc.model.MortgageSerializer
import dev.fritz2.binding.RootStore
import dev.fritz2.repositories.Resource
import dev.fritz2.repositories.localstorage.localStorageEntity

val mortgageResource = Resource({ 0 }, MortgageSerializer, Mortgage())

object MortgageStore : RootStore<Mortgage>(mortgageResource.emptyEntity) {

    private val localStorage = localStorageEntity(mortgageResource, "mortgage")

    val load = handle { mortgage ->

        console.log("##########")
        console.log(mortgage.toString())
        console.log("##########")

        localStorage.load(mortgage, 0)
    }

    val addOrUpdate = handle {

        console.log("**********")
        console.log(it.toString())
        console.log("**********")

        localStorage.addOrUpdate(it)
    }

    init {
        syncBy(addOrUpdate)
    }
}