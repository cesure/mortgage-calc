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

  readonly numbroFormat = {
    mantissa: 2,
    thousandSeparated: true,
    spaceSeparated: true
  };

  constructor() {
    numbro.registerLanguage(languageDe, true);
  }

  formatCurrency(value: number): string {
    return numbro(value).formatCurrency(this.numbroFormat);
  }

  formatNumber(value: number): string {
    return numbro(value).format();
  }

  unformatNumber(value: string): number | null {
    const num: number = numbro.unformat(value, this.numbroFormat);
    return isNaN(num) ? null : num;
  }
}

export const numbroService = new NumbroService();
