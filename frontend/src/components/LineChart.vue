<script lang="ts">
import {ChartData, ChartDataSets, ChartOptions} from "chart.js";
import {Line} from "vue-chartjs";
import {Component, Prop, Vue, Watch,} from "vue-property-decorator";

@Component({
  extends: Line,
})
export default class LineChart extends Vue {
  @Prop() chartDataSets!: ChartDataSets;
  @Prop() options!: ChartOptions;
  @Prop({default: "128em"}) height!: string;

  @Watch("chartDataSets", {immediate: true, deep: true})
  onChartDataChanged(): void {
    this.renderChart({datasets: [this.chartDataSets]}, this.options)
  }

  mounted() {
    this.renderChart({datasets: [this.chartDataSets]}, this.options)
  }

  public renderChart!: (chartData: ChartData, options: any) => void;
}
</script>
