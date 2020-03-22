<template>

  <form class="w-full max-w-6xl px-8 py-6 rounded-lg bg-white shadow-md">

    <div class="flex flex-wrap mb-6 -mx-4">
      <div class="w-full md:w-1/2 px-4">
        <label class="block mb-2 uppercase tracking-wide text-gray-700 text-xs font-bold" for="amount">
          Amount
        </label>
        <input class="appearance-none block w-full py-3 px-4 border rounded bg-gray-200 text-gray-700 leading-tight"
               id="amount" v-model="amount">
      </div>
      <div class="w-full md:w-1/2 px-4">
        <label class="block mb-2 uppercase tracking-wide text-gray-700 text-xs font-bold" for="annuity">
          Annuity
        </label>
        <input class="appearance-none block w-full py-3 px-4 border rounded bg-gray-200 text-gray-700 leading-tight"
               id="annuity" v-model="annuity">
      </div>
    </div>

    <div class="flex flex-wrap mb-6 -mx-4">
      <div class="w-full md:w-1/2 px-4">
        <label class="block mb-2 uppercase tracking-wide text-gray-700 text-xs font-bold" for="interestStart">
          Interests Start
        </label>
        <input class="appearance-none block w-full py-3 px-4 border rounded bg-gray-200 text-gray-700 leading-tight"
               id="interestStart" v-model="interestStart">
      </div>
      <div class="w-full md:w-1/2 px-4">
        <label class="block mb-2 uppercase tracking-wide text-gray-700 text-xs font-bold" for="paymentDay">
          Payment Day
        </label>
        <input class="appearance-none block w-full py-3 px-4 border rounded bg-gray-200 text-gray-700 leading-tight"
               id="paymentDay" v-model="paymentDay">
      </div>
    </div>

    <div class="flex flex-wrap mb-6 -mx-4">
      <div class="w-full md:w-1/2 px-4">
        <label class="block mb-2 uppercase tracking-wide text-gray-700 text-xs font-bold" for="interestRates">
          Interest Rate
        </label>
        <input class="appearance-none block w-full py-3 px-4 border rounded bg-gray-200 text-gray-700 leading-tight"
               id="interestRates" v-model="interestRates">
      </div>
      <div class="w-full md:w-1/2 px-4">
        <label class="block mb-2 uppercase tracking-wide text-gray-700 text-xs font-bold" for="interestOnlyMonths">
          Interest Only Months
        </label>
        <input class="appearance-none block w-full py-3 px-4 border rounded bg-gray-200 text-gray-700 leading-tight"
               id="interestOnlyMonths" v-model="interestOnlyMonths">
      </div>
    </div>

    <div class="flex flex-wrap mt-12 mb-6 -mx-4">
      <div class="w-full px-4">
        <b-button class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-3 px-4 rounded">
          Calculate
        </b-button>
      </div>
    </div>

  </form>
</template>

<script lang="ts">
  import {Component, Vue} from 'vue-property-decorator';

  @Component
  export default class MortgageCalcForm extends Vue {
  }
</script>
