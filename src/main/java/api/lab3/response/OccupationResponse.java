package api.lab3.response;

import java.util.List;

import api.lab3.model.OccupationDTO;
import lombok.Data;

@Data
public class OccupationResponse extends BaseResponse  {
    
    private List<OccupationDTO> data;

    private int minSalary;

    private int maxSalary;
}
