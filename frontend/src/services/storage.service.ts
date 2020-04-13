import dayjs from 'dayjs';
import {MortgageParams, MortgageParamsJSON} from "@/models/MortgageParams";

function parseDate(value: string): Date {
  return dayjs.utc(value).startOf('day').toDate()
}

function decodeMortgageParams(json: MortgageParamsJSON): MortgageParams {
  return Object.assign({}, json, {
    interestStart: parseDate(json.interestStart),
    interestRates: json.interestRates.map(rateJson =>
      Object.assign({}, rateJson, {
        date: parseDate(rateJson.date)
      })
    )
  });
}

class StorageService {

  public static readonly MORTGAGE_PARAMS_KEY = "mortgage_params";

  storeMortgageParams(params: MortgageParams) {
    const parsed = JSON.stringify(params);
    localStorage.setItem(StorageService.MORTGAGE_PARAMS_KEY, parsed);
  }

  loadMortgageParams(): MortgageParams | null {
    const json = localStorage.getItem(StorageService.MORTGAGE_PARAMS_KEY);

    if (json) {
      return decodeMortgageParams(JSON.parse(json));
    } else {
      return null;
    }
  }
}

export const storageService = new StorageService();
