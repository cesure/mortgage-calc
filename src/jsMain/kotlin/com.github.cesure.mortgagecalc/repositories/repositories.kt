package com.github.cesure.mortgagecalc.repositories

import com.github.cesure.mortgagecalc.model.Mortgage
import dev.fritz2.binding.RootStore

val mortgageStore = object : RootStore<Mortgage>(Mortgage()) {}

//data class PersonQuery(val namePrefix: String? = null)
//
//val queryStore = object : RootStore<RepaymentPlan>(RepaymentPlan()) {
//    val rest = localStorageQuery<Person, String, PersonQuery>(personResource, "your prefix") { entities, query ->
//        if (query.namePrefix != null) entities.filter { it.name.startsWith(query.namePrefix) }
//        else entities
//    }
//
//    val query = handle(execute = rest::query)
//
//    init {
//        query(PersonQuery())
//    }
//}
