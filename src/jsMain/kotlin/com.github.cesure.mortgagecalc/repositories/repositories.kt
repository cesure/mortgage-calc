package com.github.cesure.mortgagecalc.repositories

import com.github.cesure.mortgagecalc.model.Mortgage
import com.github.cesure.mortgagecalc.model.RepaymentPlan
import dev.fritz2.binding.RootStore
import dev.fritz2.repositories.localstorage.localStorageQuery

val mortgageStore = object : RootStore<Mortgage>(Mortgage()) {}


data class PersonQuery(val namePrefix: String? = null)

val queryStore = object : RootStore<RepaymentPlan>(emptyList()) {
    val rest = localStorageQuery<Person, String, PersonQuery>(personResource, "your prefix") { entities, query ->
        if (query.namePrefix != null) entities.filter { it.name.startsWith(query.namePrefix) }
        else entities
    }

    val query = handle(execute = rest::query)
    val delete = handle<String>(execute = rest::delete)

    init {
        query(PersonQuery())
    }
}
