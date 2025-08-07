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
@Table(name ="ACA_MST_SYSTEM_CONFIG")
@Data
public class SystemConfig {
    
    @Id //เป็นการกำหนดว่าข้อมูล Column เราจะใช้เป็น Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //ต้องการให้ id เจนข้อมูลอัตโนมัติ ให้เพิ่มขึ้นที่ละ 1 
    @Column(name = "ID")
    private int id;

    @Column(name = "MODULE_CODE")
    private String moduleCode;

    @Column(name = "CONFIG_NAME")
    private String configName;

    @Column(name = "CONFIG_VALUE")
    private String configValue;

    @Column(name = "CONFIG_DESC")
    private String configDesc;

    @Column(name = "READ_ONLY")
    private Boolean readOnly;

    @Column(name = "REGULAR_EXP")
    private String regularExp;

    @Column(name = "UPD_BY")
    private String upBy;

    @Column(name = "UPD_DATE")
    private LocalDate updateDate;


}
