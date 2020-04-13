<template>
  <input v-model="valueFormatted" @blur="isInputActive = false" @focus="isInputActive = true">
</template>

<script lang="ts">
  import {Component, Prop, Vue} from "vue-property-decorator";
  import {numbroService} from "@/services/numbro.service";

  @Component({})
  export default class CurrencyInput extends Vue {
    @Prop() public value!: number;

    isInputActive = false;

    get valueFormatted() {
      if (this.value == null) {
        return "";
      }

      if (this.isInputActive) {
        return numbroService.formatNumber(this.value);
      } else {
        return numbroService.formatCurrency(this.value);
      }
    }

    set valueFormatted(inputValue: string) {
      this.$emit('input', numbroService.unformatNumber(inputValue) || 0.0);
    }
  }
</script>
