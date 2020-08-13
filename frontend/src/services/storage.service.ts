import {MortgageParams} from "@/models/MortgageParams";
import {dtoService} from "@/services/dto.service";

class StorageService {

  public static readonly MORTGAGE_PARAMS_KEY = "mortgage_params";

  storeMortgageParams(params: MortgageParams) {
    const json = JSON.stringify(dtoService.encodeMortgageParams(params));
    localStorage.setItem(StorageService.MORTGAGE_PARAMS_KEY, json);
  }

  loadMortgageParams(): MortgageParams | null {
    const json = localStorage.getItem(StorageService.MORTGAGE_PARAMS_KEY);

    if (json) {
      return dtoService.decodeMortgageParams(JSON.parse(json));
    } else {
      return null;
    }
  }
}

export const storageService = new StorageService();
