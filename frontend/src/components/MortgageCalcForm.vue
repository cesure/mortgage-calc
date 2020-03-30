<template>
  <div id="mortgage-calc-form">
    <form @submit.prevent="getRepaymentPlan" class="w-full max-w-6xl px-8 py-6 rounded-lg bg-white shadow-md">

      <div class="flex flex-wrap mb-6 -mx-4">
        <div class="w-full md:w-1/2 px-4">
          <label class="block mb-2 uppercase tracking-wide text-gray-700 text-xs font-bold" for="amount">
            Amount
          </label>
          <input
            id="amount" type="number" step="0.01"
            class="appearance-none block w-full py-3 px-4 border rounded bg-gray-200 text-gray-700 leading-tight"
            v-model="mortgageParams.amount">
        </div>
        <div class="w-full md:w-1/2 px-4">
          <label class="block mb-2 uppercase tracking-wide text-gray-700 text-xs font-bold" for="annuity">
            Annuity
          </label>
          <input
            id="annuity" type="number" step="0.01"
            class="appearance-none block w-full py-3 px-4 border rounded bg-gray-200 text-gray-700 leading-tight"
            v-model="mortgageParams.annuity">
        </div>
      </div>

      <div class="flex flex-wrap mb-6 -mx-4">
        <div class="w-full md:w-1/2 px-4">
          <label class="block mb-2 uppercase tracking-wide text-gray-700 text-xs font-bold" for="interestStart">
            Interests Start
          </label>
          <input
            id="interestStart" type="date" required="required"
            class="appearance-none block w-full py-3 px-4 border rounded bg-gray-200 text-gray-700 leading-tight"
            :value="mortgageParams.interestStart && toDayjs(mortgageParams.interestStart).format('YYYY-MM-DD')"
            @input="mortgageParams.interestStart = toDayjs($event.target.value).toDate()">
        </div>
        <div class="w-full md:w-1/2 px-4">
          <label class="block mb-2 uppercase tracking-wide text-gray-700 text-xs font-bold" for="paymentDay">
            Payment Day
          </label>
          <input id="paymentDay" type="number" min="1" max="31"
                 class="appearance-none block w-full py-3 px-4 border rounded bg-gray-200 text-gray-700 leading-tight"
                 v-model="mortgageParams.paymentDay">
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
                  type="date" required="required"
                  class="appearance-none block w-full py-3 px-4 border rounded bg-gray-200 text-gray-700 leading-tight"
                  :value="interestRate.date && toDayjs(interestRate.date).format('YYYY-MM-DD')"
                  @input="interestRate.date = toDayjs($event.target.value).toDate()">
              </div>
              <div class="w-full md:w-2/3">
                <input
                  type="number" step="0.01"
                  class="appearance-none block w-full py-3 px-4 border rounded bg-gray-200 text-gray-700 leading-tight"
                  v-model="interestRate.rate">
              </div>
            </div>
          </div>
        </div>
        <div class="w-full md:w-1/2 px-4">
          <label class="block mb-2 uppercase tracking-wide text-gray-700 text-xs font-bold" for="interestOnlyMonths">
            Interest Only Months
          </label>
          <input
            id="interestOnlyMonths" type="number" min="0"
            class="appearance-none block w-full py-3 px-4 border rounded bg-gray-200 text-gray-700 leading-tight"
            v-model="mortgageParams.interestOnlyMonths">
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
  import dayjs from 'dayjs';
  import utc from 'dayjs/plugin/utc';
  import {Component, Vue} from 'vue-property-decorator';
  import RepaymentPlanList, {RepaymentPlan} from '@/components/RepaymentPlanList.vue';
  import {MortgageParams} from '@/models/MortgageParams';
  import {apiService} from '@/services/api.service';

  dayjs.extend(utc);

  @Component({
    components: {RepaymentPlanList}
  })
  export default class MortgageCalcForm extends Vue {

    repaymentPlan: RepaymentPlan | null = null;

    private mortgageParams: MortgageParams = {
      amount: 0,
      interestStart: dayjs.utc().startOf('day').toDate(),
      interestOnlyMonths: 0,
      paymentDay: 1,
      annuity: 0,
      interestRates: [{date: dayjs().utc().startOf('day').toDate(), rate: 1.0}]
    };

    getRepaymentPlan() {
      apiService.getRepaymentPlan(this.mortgageParams).then(
        response => (this.repaymentPlan = response.data)
      )
    }

    toDayjs(s: string | Date) {
      return dayjs.utc(s).startOf('day');
    }
  }
</script>

<style>
  input[type=date] {
    -moz-appearance: textfield;
    -webkit-appearance: textfield;
  }

  input[type=date]::-webkit-inner-spin-button {
    -webkit-appearance: none;
    display: none;
  }

  input[type=number] {
    -moz-appearance: textfield;
  }

  input[type=number]::-webkit-inner-spin-button {
    -webkit-appearance: none;
    display: none;
  }

</style>
