package api.lab3.service;

import api.lab3.request.CalculateLoanRequest;
import api.lab3.response.LoanInstallmentResponse;

public interface LoanInstallmentService {
    
    public LoanInstallmentResponse getLoanInstallmentDetails(CalculateLoanRequest request);
}
