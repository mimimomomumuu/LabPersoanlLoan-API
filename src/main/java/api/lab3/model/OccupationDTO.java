package api.lab3.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor //สร้าง Constructor ที่รับพารามิเตอร์ทุกตัว 
public class OccupationDTO {
    
    private int lovId;

    private String lovNameTH;

}

/*OccupationDTO(lov.getLovId(), lov.getLovNameTH() จะขึ้นขีดแดง 
 * เนื่องจากที่ Service  : คลาส OccupationDTO ของคุณยังไม่มี Constructor (ตัวสร้าง) ที่รับพารามิเตอร์ 2 ตัว
 * จึงต้องใส่ @AllArgsConstructor เพื่อสร้าง Constructor
 */