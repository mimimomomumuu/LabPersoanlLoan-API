package api.lab3.response;

import java.util.List;

import api.lab3.model.LoanInstallmentDTO;
import lombok.Data;

@Data
public class LoanInstallmentResponse extends BaseResponse {
    
    private List<LoanInstallmentDTO> data;
}
