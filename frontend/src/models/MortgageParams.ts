export interface MortgageParams {
  amount: number | null;
  interestStart: Date;
  interestOnlyMonths: number;
  paymentDay: number;
  annuity: number;
  interestRates: InterestRate[];
}

interface InterestRate {
  date: Date;
  rate: number;
}
