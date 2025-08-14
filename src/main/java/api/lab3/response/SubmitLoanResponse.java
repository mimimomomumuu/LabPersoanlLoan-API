package api.lab3.response;

import api.lab3.model.SubmitLoanDTO;
import lombok.Data;

@Data
public class SubmitLoanResponse extends BaseResponse {
    
    private SubmitLoanDTO data;

}
