<template>
  <div id="amortisation-chart" class="w-full max-w-6xl px-8 py-6 mt-6 rounded-lg bg-white shadow-md">
    <LineChart :chart-data-sets="chartDataSets" :options="options"></LineChart>
  </div>
</template>

<script lang="ts">
import {ChartDataSets, ChartOptions} from "chart.js";
import {Component, Prop, Vue, Watch} from "vue-property-decorator";
import LineChart from "@/components/LineChart.vue";
import {RepaymentPlan, RepaymentPlanEntry} from '@/components/RepaymentPlanList.vue';

@Component({
  components: {LineChart}
})
export default class AmortisationChart extends Vue {
  @Prop() public repaymentPlan!: RepaymentPlan;

  private chartDataSets: ChartDataSets =
    {
      label: 'Amount left',
      data: [],
      borderWidth: 2,
      borderColor: '#f00',
      fill: false,
      pointRadius: 0,
      lineTension: 0,
    }

  private options: ChartOptions = {
    responsive: true,
    legend: {
      display: false,
    },
    scales: {
      xAxes: [{
        type: 'time',
        time: {
          parser: "YYYY-MM-DD",
          tooltipFormat: "YYYY-MM-DD",
        },
        scaleLabel: {
          display: true,
          labelString: 'Date',
        }
      }],
      yAxes: [{
        scaleLabel: {
          display: true,
          labelString: 'Amount left',
        }
      }]
    }
  }

  @Watch('repaymentPlan', {immediate: true})
  onRepaymentPlanChange(val: RepaymentPlan): void {
    const newData = val.entries.map((entry: RepaymentPlanEntry) => {
      return {
        x: entry.date,
        y: entry.amountLeft
      }
    })

    this.chartDataSets = Object.assign({}, this.chartDataSets, {
      data: newData
    })
  }
}
</script>
