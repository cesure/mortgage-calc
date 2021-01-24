package com.github.cesure.mortgagecalc.model.validation

import com.github.cesure.mortgagecalc.model.Decimal
import com.github.cesure.mortgagecalc.model.L
import com.github.cesure.mortgagecalc.model.Mortgage
import dev.fritz2.identification.inspect
import dev.fritz2.validation.ValidationMessage
import dev.fritz2.validation.Validator

class MortgageValidator : Validator<Mortgage, Message, Unit>() {

    override fun validate(data: Mortgage, metadata: Unit): List<Message> {
        val msgs = mutableListOf<Message>()
        val inspector = inspect(data)

        val amount = inspector.sub(L.Mortgage.totalAmount)
        if (amount.data < Decimal(0)) {
            msgs.add(Message(amount.id, "Amount must be larger or equal 0"))
        }

        val annuity = inspector.sub(L.Mortgage.annuity)
        if (annuity.data < Decimal(0)) {
            msgs.add(Message(annuity.id, "Annuity must be larger or equal 0"))
        }

        val paymentDay = inspector.sub(L.Mortgage.paymentDay)
        if (paymentDay.data !in 1..31) {
            msgs.add(Message(paymentDay.id, "Payment day must be between 1 and 31"))
        }

        val interestRate = inspector.sub(L.Mortgage.interestRate)
        if (interestRate.data < Decimal(0)) {
            msgs.add(Message(interestRate.id, "Interest Rate must be larger or equal 0"))
        }

        return msgs
    }
}

data class Message(val id: String, val text: String) : ValidationMessage {

    override fun isError() = true
}
