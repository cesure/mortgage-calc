export interface MortgageParams {
  amount: number;
  interestStart: Date;
  interestOnlyMonths: number;
  paymentDay: number;
  annuity: number;
  interestRates: InterestRate[];
}

interface InterestRate {
  date: string;
  rate: number;
}
