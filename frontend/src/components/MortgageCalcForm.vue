<template>
  <form>
    <input v-model="amount">
    <input v-model="interestStart">
    <input v-model="interestsOnlyMonths">
    <input v-model="paymendDay">
    <input v-model="annuity">
    <input v-model="interestRates">
  </form>
</template>

<script lang="ts">
  import {Component, Vue} from 'vue-property-decorator';

  @Component
  export default class MortgageCalcForm extends Vue {
  }
</script>

<style scoped>

</style>
