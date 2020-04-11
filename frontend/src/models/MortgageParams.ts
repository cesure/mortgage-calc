export interface MortgageParams {
  amount: number;
  interestStart: Date;
  interestOnlyMonths: number;
  paymentDay: number;
  annuity: number;
  interestRates: InterestRate[];
}

export interface MortgageParamsJSON {
  amount: number;
  interestStart: string;
  interestOnlyMonths: number;
  paymentDay: number;
  annuity: number;
  interestRates: InterestRateJson[];
}

export interface InterestRate {
  date: Date;
  rate: number;
}

export interface InterestRateJson {
  date: string;
  rate: number;
}
