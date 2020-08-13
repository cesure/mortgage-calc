<template>
  <input v-model="valueFormatted" @blur="isInputActive = false" @focus="isInputActive = true">
</template>

<script lang="ts">
import Decimal from "decimal.js";
import {Component, Prop, Vue} from "vue-property-decorator";
import {numbroService} from "@/services/numbro.service";

@Component({})
export default class PercentageInput extends Vue {
  @Prop() public value!: Decimal;

  isInputActive = false;
  strValue: string | null = null;

  get valueFormatted() {
    if (this.isInputActive) {
      // show the actual user input or if no input was given yet then show the value formatted without percent sign
      return this.strValue || numbroService.formatNumber(this.value.mul(100).toNumber());
    } else {
      // input is not active: show formatted as percentage
      return numbroService.formatPercentage(this.value.toNumber());
    }
  }

  set valueFormatted(inputValue: string) {
    // save user input
    this.strValue = inputValue;

    // unformat the input, e.g. convert commas into dots etc. and cast into a number
    const unformatted = numbroService.unformatNumber(inputValue) || 0;
    const decimal = new Decimal(unformatted).div(100);

    // update value with user input converted to a decimal
    this.$emit('input', decimal);
  }
}
</script>
