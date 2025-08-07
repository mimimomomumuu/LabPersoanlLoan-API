package api.lab3.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name ="ACA_MST_LOV")
@Data
public class Lov {
    
    @Id //เป็นการกำหนดว่าข้อมูล Column เราจะใช้เป็น Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //ต้องการให้ id เจนข้อมูลอัตโนมัติ ให้เพิ่มขึ้นที่ละ 1 
    @Column(name = "LOV_ID")
    private int lovId;

    @Column(name = "LOV_TYPE")
    private String lovType;

    @Column(name = "LOV_ORDER")
    private Integer lovOrder;

    @Column(name = "LOV_NAME")
    private String lovName;

    @Column(name = "LOV_NAME_TH")
    private String lovNameTH;

    @Column(name = "LOV_DESCRIPTION")
    private String lovDescription;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "UPDATE_DATE")
    private LocalDate updateDate;
}
