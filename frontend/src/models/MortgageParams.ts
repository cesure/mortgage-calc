export interface MortgageParams {
  amount: number;
  interestStart: Date;
  interestOnlyMonths: number;
  paymentDay: number;
  annuity: number;
  interestRate: number;
}

export interface MortgageParamsJSON {
  amount: number;
  interestStart: string;
  interestOnlyMonths: number;
  paymentDay: number;
  annuity: number;
  interestRate: number;
}
