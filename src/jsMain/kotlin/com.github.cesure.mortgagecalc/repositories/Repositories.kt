package com.github.cesure.mortgagecalc.repositories

import com.github.cesure.mortgagecalc.model.Mortgage
import com.github.cesure.mortgagecalc.model.MortgageSerializer
import com.github.cesure.mortgagecalc.model.MortgageValidator
import dev.fritz2.binding.RootStore
import dev.fritz2.remote.getBody
import dev.fritz2.remote.http
import dev.fritz2.repositories.Resource
import dev.fritz2.repositories.localstorage.localStorageEntity
import org.w3c.dom.url.URLSearchParams

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

object RepaymentPlanStore : RootStore<String>("") {

    private val repaymentPlanApi = http("/api/repaymentPlan").acceptJson().contentType("application/json")

    val addRepaymentPlan = handle { _, s: String ->

        val asd = URLSearchParams("")
        asd.append("foo", s)
        asd.append("ding", "dong")

        console.log(asd)
        console.log(asd.toString())

        repaymentPlanApi.get("?$asd").getBody()
    }
}
