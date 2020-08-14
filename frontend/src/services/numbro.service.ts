import numbro from "numbro";
import languageDeDe from "numbro/dist/languages/de-DE.min.js";

numbro.registerLanguage(languageDeDe, true);

class NumbroService {

  readonly format: numbro.Format = {
    mantissa: 2,
    thousandSeparated: true,
    spaceSeparated: true
  };

  readonly percentagetFormat: numbro.Format = {
    output: "percent",
    mantissa: 2,
    trimMantissa: true,
    thousandSeparated: true
  };

  formatCurrency(value: number): string {
    return numbro(value).formatCurrency(this.format);
  }

  formatNumber(value: number): string {
    return numbro(value).format();
  }

  formatPercentage(value: number): string {
    return numbro(value).format(this.percentagetFormat);
  }

  unformatNumber(value: string): number | null {
    const num: number = numbro.unformat(value, this.format);
    return isNaN(num) ? null : num;
  }
}

export const numbroService = new NumbroService();
