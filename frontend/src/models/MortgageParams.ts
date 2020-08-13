import Decimal from "decimal.js";

export interface MortgageParams {
  amount: Decimal;
  interestStart: Date;
  interestOnlyMonths: number;
  paymentDay: number;
  annuity: Decimal;
  interestRate: Decimal;
}

export interface MortgageParamsDto {
  amount: string;
  interestStart: string;
  interestOnlyMonths: number;
  paymentDay: number;
  annuity: string;
  interestRate: string;
}
