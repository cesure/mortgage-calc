<template>
  <div>
    <h1 class="text-4xl mb-6 font-bold text-gray-700 ">Mortgage Calc</h1>
    <MortgageCalcForm/>
  </div>
</template>

<script lang="ts">
import {Component, Vue} from 'vue-property-decorator';
import MortgageCalcForm from "@/components/MortgageCalcForm.vue";

@Component({
  components: {
    MortgageCalcForm,
  },
})
export default class MortgageCalc extends Vue {
}
</script>
