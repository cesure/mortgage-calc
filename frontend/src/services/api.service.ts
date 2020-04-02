import axios, {AxiosResponse} from "axios";

import {RepaymentPlan} from "@/components/RepaymentPlanList.vue";
import {MortgageParams} from "@/models/MortgageParams";

class ApiService {

  getRepaymentPlan(params: MortgageParams): Promise<AxiosResponse<RepaymentPlan>> {
    return axios.get<RepaymentPlan>('/repaymentPlan', {params})
  }
}

export const apiService = new ApiService();
