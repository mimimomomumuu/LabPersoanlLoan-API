package api.lab3.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class LoanInstallmentDTO {
    private int noOfInstallment;
    private BigDecimal interestRate;
}
