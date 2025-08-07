package api.lab3.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class LoanDetailDTO {
    private int noOfInstallment;
    private BigDecimal creditLimit; 
    private BigDecimal interestRate;
}
