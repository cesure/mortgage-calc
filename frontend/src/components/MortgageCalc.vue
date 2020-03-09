<template>
  <div class="mortgage-calc">
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

<style scoped>

</style>
