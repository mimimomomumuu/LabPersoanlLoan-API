package api.lab3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.lab3.request.CalculateLoanRequest;
import api.lab3.request.LoanApplicationRequest;
import api.lab3.response.CalculateLoanResponse;
import api.lab3.response.LoanInstallmentResponse;
import api.lab3.response.OccupationResponse;
import api.lab3.response.SubmitLoanResponse;
import api.lab3.service.LoanInstallmentService;
import api.lab3.service.LoanService;
import api.lab3.service.OccupationService;
import api.lab3.service.SubmitLoanService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@Slf4j
public class WebServiceController {
    
    @Autowired
    OccupationService occupationService;

    @Autowired
    LoanService loanService;

    @Autowired
    private LoanInstallmentService loanInstallmentService;

    @Autowired
    private SubmitLoanService submitLoanService;

    @GetMapping("/occupations")
    public ResponseEntity<OccupationResponse> getOccupations() {
        OccupationResponse response = occupationService.getOccupations();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/loan/calculate-limit")
    public ResponseEntity<CalculateLoanResponse> calculateLoanLimit(@RequestBody CalculateLoanRequest request) {
        // Log ว่าได้รับ Request อะไรมา
        log.info("Received request to calculate loan limit: {}", request);

        // เรียก Service
        CalculateLoanResponse response = loanService.calculateLoanLimit(request);
        
        // Log Response ที่จะส่งกลับ
        log.info("Sending loan calculation response: {}", response);
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/loan/installments")
    public LoanInstallmentResponse getLoanInstallments(@RequestBody CalculateLoanRequest request) {
        return loanInstallmentService.getLoanInstallmentDetails(request);
    }

    @PostMapping("/loan/submit")
    public ResponseEntity<SubmitLoanResponse> submitLoanApplication(@RequestBody LoanApplicationRequest request) {
        log.info("Received loan application request: {}", request);
        SubmitLoanResponse response = submitLoanService.submitLoanApplication(request);
        log.info("Sending loan application response: {}", response);
        return ResponseEntity.ok(response);
    }
    
}
