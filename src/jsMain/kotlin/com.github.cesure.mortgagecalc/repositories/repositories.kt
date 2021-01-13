package com.github.cesure.mortgagecalc.repositories

import com.github.cesure.mortgagecalc.model.Mortgage
import com.github.cesure.mortgagecalc.model.MortgageSerializer
import com.github.cesure.mortgagecalc.model.MortgageValidator
import dev.fritz2.binding.RootStore
import dev.fritz2.repositories.Resource
import dev.fritz2.repositories.localstorage.localStorageEntity

val mortgageResource = Resource({ 0 }, MortgageSerializer, Mortgage())

object MortgageStore : RootStore<Mortgage>(mortgageResource.emptyEntity) {

    private val localStorage = localStorageEntity(mortgageResource, "mortgage")

    val load = handle { mortgage ->
        localStorage.load(mortgage, 0)
    }

    val addOrUpdate = handle {
        localStorage.addOrUpdate(it)
    }

    val validator = MortgageValidator()

    val updateWithValidation = handle<Mortgage> { old, new ->
        if (validator.isValid(new, Unit)) new else old
    }

    init {
        syncBy(addOrUpdate)
    }
}
