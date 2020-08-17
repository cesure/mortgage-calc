import dayjs from "dayjs";
import utc from "dayjs/plugin/utc";
import Decimal from "decimal.js";
import {MortgageParams, MortgageParamsDto} from "@/models/MortgageParams";

dayjs.extend(utc);

class DtoService {

  decodeMortgageParams(dto: MortgageParamsDto): MortgageParams {
    return Object.assign({}, dto, {
      amount: new Decimal(dto.amount),
      interestStart: this.parseDate(dto.interestStart),
      annuity: new Decimal(dto.annuity),
      downPaymentRate: new Decimal(dto.downPaymentRate),
      interestRate: new Decimal(dto.interestRate)
    });
  }

  encodeMortgageParams(params: MortgageParams): MortgageParamsDto {
    return Object.assign({}, params, {
      amount: params.amount.toString(),
      interestStart: this.formatDate(params.interestStart),
      annuity: params.annuity.toString(),
      downPaymentRate: params.downPaymentRate.toString(),
      interestRate: params.interestRate.toString()
    });
  }

  parseDate(value: string): Date {
    return dayjs.utc(value).startOf('day').toDate()
  }

  formatDate(date: Date): string {
    return dayjs.utc(date).format("YYYY-MM-DD")
  }
}

export const dtoService = new DtoService();
