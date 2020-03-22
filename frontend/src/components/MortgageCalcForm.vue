<template>
  <div id="mortgage-calc-form">
    <form @submit.prevent="getRepaymentPlan" class="w-full max-w-6xl px-8 py-6 rounded-lg bg-white shadow-md">

      <div class="flex flex-wrap mb-6 -mx-4">
        <div class="w-full md:w-1/2 px-4">
          <label class="block mb-2 uppercase tracking-wide text-gray-700 text-xs font-bold" for="amount">
            Amount
          </label>
          <input class="appearance-none block w-full py-3 px-4 border rounded bg-gray-200 text-gray-700 leading-tight"
                 id="amount" type="number" step="0.01" v-model="mortgageParams.amount">
        </div>
        <div class="w-full md:w-1/2 px-4">
          <label class="block mb-2 uppercase tracking-wide text-gray-700 text-xs font-bold" for="annuity">
            Annuity
          </label>
          <input class="appearance-none block w-full py-3 px-4 border rounded bg-gray-200 text-gray-700 leading-tight"
                 id="annuity" type="number" step="0.01" v-model="mortgageParams.annuity">
        </div>
      </div>

      <div class="flex flex-wrap mb-6 -mx-4">
        <div class="w-full md:w-1/2 px-4">
          <label class="block mb-2 uppercase tracking-wide text-gray-700 text-xs font-bold" for="interestStart">
            Interests Start
          </label>
          <input class="appearance-none block w-full py-3 px-4 border rounded bg-gray-200 text-gray-700 leading-tight"
                 id="interestStart"
                 :value="mortgageParams.interestStart && mortgageParams.interestStart.toISOString().split('T')[0]"
                 @input="mortgageParams.interestStart = $event.target.value">
        </div>
        <div class="w-full md:w-1/2 px-4">
          <label class="block mb-2 uppercase tracking-wide text-gray-700 text-xs font-bold" for="paymentDay">
            Payment Day
          </label>
          <input class="appearance-none block w-full py-3 px-4 border rounded bg-gray-200 text-gray-700 leading-tight"
                 id="paymentDay" type="number" min="1" max="31" v-model="mortgageParams.paymentDay">
        </div>
      </div>

      <div class="flex flex-wrap mb-6 -mx-4">
        <div class="w-full md:w-1/2 px-4">
          <label class="block mb-2 uppercase tracking-wide text-gray-700 text-xs font-bold" for="interestRates">
            Interest Rate
          </label>
          <div class="appearance-none block w-full mb-6" id="interestRates">
            <div class="flex" v-for="(interestRate, index) in mortgageParams.interestRates" :key="index">
              <div class="w-full md:w-1/3 pr-2">
                <input
                  required="required"
                  class="appearance-none block w-full py-3 px-4 border rounded bg-gray-200 text-gray-700 leading-tight"
                  v-model="interestRate.date">
              </div>
              <div class="w-full md:w-2/3">
                <input
                  class="appearance-none block w-full py-3 px-4 border rounded bg-gray-200 text-gray-700 leading-tight"
                  type="number" step="0.01"
                  v-model="interestRate.rate">
              </div>
            </div>
          </div>
        </div>
        <div class="w-full md:w-1/2 px-4">
          <label class="block mb-2 uppercase tracking-wide text-gray-700 text-xs font-bold" for="interestOnlyMonths">
            Interest Only Months
          </label>
          <input class="appearance-none block w-full py-3 px-4 border rounded bg-gray-200 text-gray-700 leading-tight"
                 id="interestOnlyMonths" type="number" min="0" v-model="mortgageParams.interestOnlyMonths">
        </div>
      </div>

      <div class="flex flex-wrap -mx-4">
        <div class="w-full px-4">
          <button class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-3 px-4 rounded">
            Calculate
          </button>
        </div>
      </div>

    </form>

    <RepaymentPlanList v-if="repaymentPlan" :repaymentPlan="repaymentPlan"/>
  </div>
</template>

<script lang="ts">
  import {Component, Vue} from 'vue-property-decorator';
  import RepaymentPlanList, {RepaymentPlan} from "@/components/RepaymentPlanList.vue";
  import {MortgageParams} from "@/models/MortgageParams";
  import {apiService} from "@/services/api.service";

  @Component({
    components: {RepaymentPlanList}
  })
  export default class MortgageCalcForm extends Vue {

    repaymentPlan: RepaymentPlan | null = null;

    private mortgageParams: MortgageParams = {
      amount: 0,
      interestStart: new Date(),
      interestOnlyMonths: 0,
      paymentDay: 1,
      annuity: 0,
      interestRates: [{date: new Date().toISOString().split('T')[0], rate: 1.0}]
    };

    getRepaymentPlan() {
      apiService.getRepaymentPlan(this.mortgageParams).then(
        response => (this.repaymentPlan = response.data)
      )
    }
  }
</script>

<style>
  input[type=number] {
    -moz-appearance: textfield;
  }

  input[type=number]::-webkit-inner-spin-button {
    -webkit-appearance: none;
    display: none;
  }

</style>
