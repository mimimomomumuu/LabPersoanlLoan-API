package api.lab3.service;

import api.lab3.request.LoanApplicationRequest;
import api.lab3.response.SubmitLoanResponse;

public interface SubmitLoanService {
    
    SubmitLoanResponse submitLoanApplication(LoanApplicationRequest request);
}
