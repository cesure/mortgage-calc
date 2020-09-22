<template>
  <div id="mortgage-calc-form">
    <form @submit.prevent="getRepaymentPlan" class="w-full max-w-6xl px-8 py-6 rounded-lg bg-white shadow-md">

      <div class="flex flex-wrap mb-6 -mx-4">
        <div class="w-full md:w-1/2 px-4">
          <label class="block mb-2 uppercase tracking-wide text-gray-700 text-xs font-bold" for="amount">
            Amount
          </label>
          <CurrencyInput
            id="amount"
            class="appearance-none block w-full py-3 px-4 border rounded bg-gray-200 text-gray-700 leading-tight"
            v-model="mortgageParams.amount">
          </CurrencyInput>
        </div>
        <div class="w-full md:w-1/2 px-4">
          <label class="block mb-2 uppercase tracking-wide text-gray-700 text-xs font-bold"
                 :for="this.mortgageParams.useAnnuity ? 'annuity' : 'downPaymentRate'">
            <span class="cursor-pointer hover:underline" @click="switchUseAnnuity()"
                  :class="{ 'text-gray-500': !this.mortgageParams.useAnnuity }">Annuity</span>
            |
            <span class="cursor-pointer hover:underline" @click="switchUseAnnuity()"
                  :class="{ 'text-gray-500': this.mortgageParams.useAnnuity }">Down Payment Rate</span>
          </label>
          <CurrencyInput v-show="this.mortgageParams.useAnnuity"
                         id="annuity"
                         class="appearance-none block w-full py-3 px-4 border rounded bg-gray-200 text-gray-700 leading-tight"
                         v-model="mortgageParams.annuity">
          </CurrencyInput>
          <PercentageInput
            v-show="!this.mortgageParams.useAnnuity"
            id="downPaymentRate"
            class="appearance-none block w-full py-3 px-4 border rounded bg-gray-200 text-gray-700 leading-tight"
            v-model="mortgageParams.downPaymentRate">
          </PercentageInput>
        </div>
      </div>

      <div class="flex flex-wrap mb-6 -mx-4">
        <div class="w-full md:w-1/2 px-4">
          <label class="block mb-2 uppercase tracking-wide text-gray-700 text-xs font-bold" for="interestStart">
            Interest Start
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
                 v-model.number="mortgageParams.paymentDay">
        </div>
      </div>

      <div class="flex flex-wrap mb-6 -mx-4">
        <div class="w-full md:w-1/2 px-4">
          <label class="block mb-2 uppercase tracking-wide text-gray-700 text-xs font-bold" for="interestRate">
            Interest Rate
          </label>
          <PercentageInput
            id="interestRate"
            class="appearance-none block w-full py-3 px-4 border rounded bg-gray-200 text-gray-700 leading-tight"
            v-model="mortgageParams.interestRate">
          </PercentageInput>
        </div>
        <div class="w-full md:w-1/2 px-4">
          <label class="block mb-2 uppercase tracking-wide text-gray-700 text-xs font-bold" for="interestOnlyMonths">
            Interest Only Months
          </label>
          <input
            id="interestOnlyMonths" type="number" min="0"
            class="appearance-none block w-full py-3 px-4 border rounded bg-gray-200 text-gray-700 leading-tight"
            v-model.number="mortgageParams.interestOnlyMonths">
        </div>
      </div>

      <div class="flex flex-wrap mb-6">
        <ExtraRepayments/>
      </div>

      <div class="flex flex-wrap -mx-4">
        <div class="w-full px-4">
          <button class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-3 px-4 rounded">
            Calculate
          </button>
        </div>
      </div>

    </form>

    <div v-if="repaymentPlan" class="w-full max-w-6xl px-8 py-6 mt-6 rounded-lg bg-white shadow-md text-gray-700">
      <dl>
        <div class="sm:grid sm:grid-cols-3 sm:gap-2">
          <dt class="text-sm font-bold text-gray-700">Number of payments</dt>
          <dd class="text-sm sm:col-span-2">{{ repaymentPlan.numberOfPayments }} payments</dd>
          <dt class="text-sm font-bold text-gray-700">Total payments</dt>
          <dd class="text-sm sm:col-span-2">{{ repaymentPlan.totalPayments | formatCurrency }}</dd>
          <dt class="text-sm font-bold text-gray-700">Interest payments</dt>
          <dd class="text-sm sm:col-span-2">{{ repaymentPlan.interestPayments | formatCurrency }}</dd>
          <dt class="text-sm font-bold text-gray-700">Last payment date</dt>
          <dd class="text-sm sm:col-span-2">{{ repaymentPlan.lastPaymentDate | formatDate }}</dd>
        </div>
      </dl>
    </div>

    <AmortisationChart v-if="repaymentPlan" :repayment-plan="repaymentPlan"/>

    <RepaymentPlanList v-if="repaymentPlan" :entries="repaymentPlan.entries"/>
  </div>
</template>

<script lang="ts">
import dayjs from 'dayjs';
import utc from 'dayjs/plugin/utc';
import Decimal from "decimal.js";
import {Component, Vue} from 'vue-property-decorator';
import AmortisationChart from "@/components/AmortisationChart.vue";
import CurrencyInput from "@/components/CurrencyInput.vue";
import ExtraRepayments from "@/components/ExtraRepayments.vue";
import PercentageInput from "@/components/PercentageInput.vue";
import RepaymentPlanList, {RepaymentPlan} from '@/components/RepaymentPlanList.vue';
import {MortgageParams} from '@/models/MortgageParams';
import {apiService} from '@/services/api.service';
import {numbroService} from "@/services/numbro.service";
import {storageService} from "@/services/storage.service";

dayjs.extend(utc);

@Component({
  components: {AmortisationChart, CurrencyInput, ExtraRepayments, PercentageInput, RepaymentPlanList},
  filters: {
    formatCurrency(value: number) {
      return numbroService.formatCurrency(value);
    },
    formatDate(d: string) {
      const options = {weekday: 'long', year: 'numeric', month: 'long', day: 'numeric'};
      return new Date(d).toLocaleDateString(undefined, options)
    }
  }
})
export default class MortgageCalcForm extends Vue {

  repaymentPlan: RepaymentPlan | null = null;

  private mortgageParams: MortgageParams = {
    amount: new Decimal(0),
    interestStart: this.today(),
    interestOnlyMonths: 0,
    paymentDay: 1,
    useAnnuity: true,
    annuity: new Decimal(0),
    downPaymentRate: new Decimal(0),
    interestRate: new Decimal(0)
  };

  mounted(): void {
    const storedParams = storageService.loadMortgageParams()
    if (storedParams) {
      this.mortgageParams = storedParams;
    }
  }

  switchUseAnnuity(): void {
    this.mortgageParams.useAnnuity = !this.mortgageParams.useAnnuity;
  }

  getRepaymentPlan(): void {
    apiService.getRepaymentPlan(this.mortgageParams).then(
      response => (this.repaymentPlan = response.data)
    );

    storageService.storeMortgageParams(this.mortgageParams);
  }

  today(): Date {
    return dayjs().utc().startOf('day').toDate();
  }

  toDayjs(s: string | Date): dayjs.Dayjs {
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
