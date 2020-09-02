<template>
  <div id="repayment-plan">
    <table class="mt-6 w-full max-w-6xl table-auto rounded-lg bg-white shadow-md">
      <tr class="text-gray-700">
        <th class="text-left pt-6 pl-8 pr-4 pb-2">Date</th>
        <th class="text-left pt-6 px-4 pb-2">Down Payment</th>
        <th class="text-left pt-6 px-4 pb-2">Interest Payment</th>
        <th class="text-left pt-6 pl-4 pr-8 pb-2">Amount left</th>
      </tr>
      <tr class="text-gray-700" v-for="(entry, index) in entries" :key="entry.date">
        <td class="pl-8 pr-4"
            :class="{'pb-2': index !== entries.length - 1, 'pb-6': index === entries.length - 1}">
          {{ entry.date | formatDate }}
        </td>
        <td class="px-4"
            :class="{'pb-2': index !== entries.length - 1, 'pb-6': index === entries.length - 1}">
          {{ entry.repayment.downPayment | formatCurrency }}
        </td>
        <td class="px-4"
            :class="{'pb-2': index !== entries.length - 1, 'pb-6': index === entries.length - 1}">
          {{ entry.repayment.interestPayment | formatCurrency }}
        </td>
        <td class="pl-4 pr-8"
            :class="{'pb-2': index !== entries.length - 1, 'pb-6': index === entries.length - 1}">
          {{ entry.amountLeft | formatCurrency }}
        </td>
      </tr>
    </table>
  </div>
</template>

<script lang="ts">
import {Component, Prop, Vue} from "vue-property-decorator";
import {numbroService} from '@/services/numbro.service';

export interface Repayment {
  interestPayment: number;
  downPayment: number;
}

export interface RepaymentPlanEntry {
  date: string;
  repayment: Repayment;
  amountLeft: number;
}

export interface RepaymentPlan {
  entries: RepaymentPlanEntry[];
  totalAmountPaid: number;
  numberOfPayments: number;
  lastPaymentDate: string;
}

@Component({
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
export default class RepaymentPlanList extends Vue {
  @Prop() public entries!: RepaymentPlanEntry[];
}
</script>
