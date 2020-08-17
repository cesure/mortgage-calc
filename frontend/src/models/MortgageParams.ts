import Decimal from "decimal.js";

export interface MortgageParams {
  amount: Decimal;
  interestStart: Date;
  interestOnlyMonths: number;
  paymentDay: number;
  useAnnuity: boolean;
  annuity: Decimal;
  downPaymentRate: Decimal;
  interestRate: Decimal;
}

export interface MortgageParamsDto {
  amount: string;
  interestStart: string;
  interestOnlyMonths: number;
  paymentDay: number;
  useAnnuity: boolean;
  annuity: string;
  downPaymentRate: string;
  interestRate: string;
}
