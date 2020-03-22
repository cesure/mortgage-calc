<template>
  <div class="px-6 py-4">
    <MortgageCalc/>
  </div>
</template>

<script lang="ts">
  import {Component, Vue} from 'vue-property-decorator';
  import MortgageCalc from "@/components/MortgageCalc.vue";

  @Component({
    components: {
      MortgageCalc,
    },
  })
  export default class App extends Vue {
  }
</script>
