package api.lab3.response;

import java.util.Optional;

import api.lab3.model.CalculateLoanDTO;
import lombok.Data;

@Data
public class CalculateLoanResponse extends BaseResponse {
    
    private Optional<CalculateLoanDTO> loanDetails;

}