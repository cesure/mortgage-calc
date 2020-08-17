import axios, {AxiosResponse} from "axios";

import {RepaymentPlan} from "@/components/RepaymentPlanList.vue";
import {MortgageParams} from "@/models/MortgageParams";
import {dtoService} from "@/services/dto.service";

class ApiService {

  getRepaymentPlan(params: MortgageParams): Promise<AxiosResponse<RepaymentPlan>> {
    return axios.get<RepaymentPlan>('/repaymentPlan', {params: dtoService.encodeMortgageParams(params)})
  }
}

export const apiService = new ApiService();
