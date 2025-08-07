package api.lab3.request;

import lombok.Data;

@Data
public class CalculateLoanRequest {
    
    private int lovId;

    private int income;
}