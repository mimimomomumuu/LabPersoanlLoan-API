package api.lab3.service;


import api.lab3.request.CalculateLoanRequest;
import api.lab3.response.CalculateLoanResponse;

public interface LoanService {
    
    CalculateLoanResponse calculateLoanLimit(CalculateLoanRequest request);
}
