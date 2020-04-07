<template>
  <input v-model="valueFormatted" @blur="isInputActive = false" @focus="isInputActive = true">
</template>

<script lang="ts">
import Decimal from "decimal.js";
import {Component, Prop, Vue} from "vue-property-decorator";
import {numbroService} from "@/services/numbro.service";

@Component({})
export default class CurrencyInput extends Vue {
  @Prop() public value!: Decimal;

  isInputActive = false;
  strValue: string | null = null;

  get valueFormatted(): string {
    const valueAsNumber = this.value.toNumber();
    if (this.isInputActive) {
      // show the actual user input or if no input was given yet then show the value formatted without currency sign
      return this.strValue || numbroService.formatNumber(valueAsNumber);
    } else {
      // input is not active: show formatted with currency sign
      return numbroService.formatCurrency(valueAsNumber);
    }
  }

  set valueFormatted(inputValue: string) {
    // save user input
    this.strValue = inputValue;

    // unformat the input, e.g. convert commas into dots etc. and cast into a number
    const unformatted = numbroService.unformatNumber(inputValue) || 0;
    const decimal = new Decimal(unformatted);

    // update value with user input converted to a decimal
    this.$emit('input', decimal);
  }
}
</script>
