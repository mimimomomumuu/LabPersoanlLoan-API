package api.lab3.request;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class LoanApplicationRequest {

    // ข้อมูลจากหน้า 1
    private Integer occupationId;
    private BigDecimal salary;

    // ข้อมูลจากหน้า 2
    private BigDecimal desiredLoan;
    private BigDecimal interestRate;
    private Integer noOfInstallment;

    // ข้อมูลจากหน้า 3
    private String firstName;
    private String lastName;
    private String gender;
    private String dateOfBirth; // ใช้ String เพื่อให้ตรงกับข้อมูลที่ส่งมาจาก Frontend
    private String mobileNo;
    private String citizenId;

}