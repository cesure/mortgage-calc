import axios, {AxiosResponse} from "axios";

import {RepaymentPlan} from "@/components/RepaymentPlanList.vue";
import {MortgageParams} from "@/models/MortgageParams";

class ApiService {

  getRepaymentPlan(params: MortgageParams): Promise<AxiosResponse<RepaymentPlan>> {
    return axios.get<RepaymentPlan>('http://localhost:8080/repaymentPlan', {params})
  }
}

export const apiService = new ApiService()
