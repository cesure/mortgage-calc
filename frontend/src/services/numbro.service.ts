import numbro from "numbro";

const languageDe: numbro.NumbroLanguage = {
  languageTag: "de-DE",
  delimiters: {
    thousands: ".",
    decimal: ",",
    thousandsSize: 3
  },
  abbreviations: {
    thousand: "k",
    million: "m",
    billion: "b",
    trillion: "t"
  },
  ordinal: function () {
    return ".";
  },
  spaceSeparated: true,
  currency: {
    symbol: "€",
    position: "postfix",
    code: "EUR"
  },
  currencyFormat: {
    totalLength: 4,
    thousandSeparated: true
  },
  formats: {
    fourDigits: {
      totalLength: 4,
      spaceSeparated: true,
      average: true
    },
    fullWithTwoDecimals: {
      output: "currency",
      mantissa: 2,
      spaceSeparated: true,
      thousandSeparated: true
    },
    fullWithTwoDecimalsNoCurrency: {
      mantissa: 2,
      thousandSeparated: true
    },
    fullWithNoDecimals: {
      output: "currency",
      spaceSeparated: true,
      thousandSeparated: true,
      mantissa: 0
    }
  }
};

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

  constructor() {
    numbro.registerLanguage(languageDe, true);
  }

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
