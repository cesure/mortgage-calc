import {MortgageParams} from "@/models/MortgageParams";

class StorageService {

  public static readonly MORTGAGE_PARAMS_KEY = "mortgage_params";

  storeMortgageParams(params: MortgageParams) {
    const parsed = JSON.stringify(params);
    localStorage.setItem(StorageService.MORTGAGE_PARAMS_KEY, parsed);
  }

  loadMortgageParams(): MortgageParams | null {
    const json = localStorage.getItem(StorageService.MORTGAGE_PARAMS_KEY);

    if (json) {
      return JSON.parse(json)
    } else {
      return null
    }
  }
}

export const storageService = new StorageService();
