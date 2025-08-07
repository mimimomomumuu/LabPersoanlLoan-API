package api.lab3.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name ="ACA_MST_CAR_INTERRATE_RATE")
@Data
public class CarInterrateRate {
    
    @Id //เป็นการกำหนดว่าข้อมูล Column เราจะใช้เป็น Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //ต้องการให้ id เจนข้อมูลอัตโนมัติ ให้เพิ่มขึ้นที่ละ 1 
    @Column(name = "ID")
    private int id;

    @Column(name = "PLOAN_SALARY_START")
    private int ploanSalaryStart;

    @Column(name = "PLOAN_SALARY_END")
    private int ploanSalaryEnd;

    @Column(name = "PLOAN_OCCUPATION_ID")
    private int ploanOccupationId;

    @Column(name = "CREDIT_LIMIT") 
    private BigDecimal creditLimit; 

    @Column(name = "NO_OF_INSTALLMENT")
    private int noOfInstallment;

    @Column(name = "INTERATE_RATE") 
    private BigDecimal interateRate; 

    @Column(name = "CREATED_DATE")
    private LocalDate createdDate; 

    @Column(name = "CREATED_BY") 
    private String createdBy;

    @Column(name = "UPDATED_DATE")
    private LocalDate updatedDate; 

    @Column(name = "UPDATED_BY") 
    private String updatedBy;


}
