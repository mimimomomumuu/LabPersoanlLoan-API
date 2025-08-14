package api.lab3.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name ="REQ_LOAN_REQUEST")
@Data
public class LoanRequest {
    
    @Id //เป็นการกำหนดว่าข้อมูล Column เราจะใช้เป็น Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //ต้องการให้ id เจนข้อมูลอัตโนมัติ ให้เพิ่มขึ้นที่ละ 1 
    @Column(name = "ID")
    private int id;

    @Column(name = "REQ_CUST_ID")
    private Integer reqCustId;

    @Column(name = "REQ_LOAN_TYPE")
    private Integer reqLoanType;

    @Column(name = "GENDER")
    private String gender;

    @Column(name = "DATE_OF_BIRTH")
    private LocalDate dateOfBirth;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LASTNAME")
    private String lastName;

    @Column(name = "MOBILE_NO")
    private String mobileNo;

    @Column(name = "CITIZEN_ID")
    private String citizenId;

    @Column(name = "CAR_MAKE_ID")
    private Integer carMakeId;

    @Column(name = "CAR_MODEL_ID")
    private Integer carModelId;

    @Column(name = "OCCUPATION_ID")
    private Integer occupationId;

    @Column(name = "SALARY")
    private BigDecimal salary;

    @Column(name = "INTERATE_RATE")
    private BigDecimal interateRate;

    @Column(name = "NO_OF_INSTALLMENT")
    private Integer noOfInstallment;

    @Column(name = "REQ_LOAN_DATETIME")
    private LocalDateTime reqLoanDatetime;

    @Column(name = "LOAN_AMOUNT")
    private BigDecimal loanAmount;

    // @PrePersist กำหนดค่าเริ่มต้นให้กับ Field ต่างๆ ที่เราต้องการให้มีค่าอัตโนมัติก่อนการบันทึกครั้งแรก
    @PrePersist
    public void prePersist() {
        if (reqLoanDatetime == null) {
            reqLoanDatetime = LocalDateTime.now();
        }
        // กำหนดค่าอื่นๆ ที่ต้องการ hardcode เช่น REQ_LOAN_TYPE, CAR_MAKE_ID, CAR_MODEL_ID
        if (reqLoanType == null) {
            reqLoanType = 2;
        }
        if (carMakeId == null) {
            carMakeId = 1;
        }
        if (carModelId == null) {
            carModelId = 1;
        }
    }

    //การใช้ @PrePersist ใน Entity Class ช่วยลดภาระงานของ Service Layer ได้อย่างมาก เพราะ Logic บางส่วนถูกย้ายมาจัดการที่ Entity โดยตรง


}
