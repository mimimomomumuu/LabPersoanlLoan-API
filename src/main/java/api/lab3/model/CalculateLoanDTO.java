package api.lab3.model;

import java.util.List;

import lombok.Data;

@Data
public class CalculateLoanDTO {
    
    private int maxLoan;

    private int minLoan;

    private List<LoanDetailDTO> loanInstallmentDetails;
}
