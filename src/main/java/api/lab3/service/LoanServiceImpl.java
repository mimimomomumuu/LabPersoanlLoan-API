package api.lab3.service;

import api.lab3.entity.CarInterrateRate;
import api.lab3.model.CalculateLoanDTO;
import api.lab3.model.LoanDetailDTO; 
import api.lab3.repository.CarInterrateRateRepository;
import api.lab3.request.CalculateLoanRequest;
import api.lab3.response.CalculateLoanResponse;

import java.math.BigDecimal; 
import java.util.List; 
import java.util.Optional;
import java.util.stream.Collectors; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional; 

@Service
@Transactional 
public class LoanServiceImpl implements LoanService {

    @Autowired
    CarInterrateRateRepository carInterrateRateRepository;

    @Autowired
    OccupationService occupationService;

    @Override
    public CalculateLoanResponse calculateLoanLimit(CalculateLoanRequest request) {
        CalculateLoanResponse response = new CalculateLoanResponse();

        try {
            // 1. ดึงค่า Config ที่เกี่ยวข้องกับการคำนวณวงเงินสินเชื่อ
            // ดึงค่า Min/Max Salary จาก DB ที่สะกดผิด (PRESONAL_LONE)
            int minSalary = occupationService.getSystemConfigValue("PRESONAL_LONE", "MIN_SALARY");
            int maxSalary = occupationService.getSystemConfigValue("PRESONAL_LONE", "MAX_SALARY");
            // ดึงค่า Min/Max Loan จาก DB ที่สะกดถูก (PERSONAL_LONE)
            int minLoanConfig = occupationService.getSystemConfigValue("PERSONAL_LONE", "MIN_LONE");
            int maxLoanConfig = occupationService.getSystemConfigValue("PERSONAL_LONE", "MAX_LONE");

            // 2. ตรวจสอบรายได้ของผู้ใช้ (income) ว่าอยู่ในช่วงที่กำหนดหรือไม่
            if (request.getIncome() < minSalary || request.getIncome() > maxSalary) {
                response.setResponseCode("0004");
                response.setResponseMessage("รายได้ไม่อยู่ในช่วงที่สามารถกู้ยืมได้");
                response.setLoanDetails(Optional.empty()); // ไม่มีข้อมูลเลย //Optional ถูกใช้แทน null เพื่อช่วยป้องกัน NullPointerException
                return response;
            }

            // 3. ค้นหา Credit Limit และรายละเอียดงวดผ่อนชำระทั้งหมด
            // Repository จะคืนค่าเป็น List เพราะมีหลายแถวที่ตรงกัน
            List<CarInterrateRate> carInterrateRates = carInterrateRateRepository
                    .findByPloanOccupationIdAndPloanSalaryStartLessThanEqualAndPloanSalaryEndGreaterThanEqual(
                            request.getLovId(), request.getIncome(), request.getIncome()
                    );

            if (carInterrateRates.isEmpty()) {
                response.setResponseCode("0004");
                response.setResponseMessage("ไม่พบข้อมูลวงเงินสินเชื่อสำหรับเกณฑ์ที่กำหนด");
                response.setLoanDetails(Optional.empty());
                return response;
            }

            // 4. คำนวณ Max Loan และสร้าง DTO สำหรับรายละเอียดงวดผ่อนชำระ
            // ดึงค่า creditLimit จากแถวแรกมาใช้ เนื่องจากทุกแถวมีค่าเท่ากัน
            BigDecimal creditLimitValue = carInterrateRates.get(0).getCreditLimit();

            // คำนวณวงเงินตามเกณฑ์ของลูกค้า: income * creditLimitValue
            // ต้องแปลงค่าให้เป็น BigDecimal ก่อนเพื่อการคำนวณที่ถูกต้อง
            BigDecimal calculatedCreditLimit = BigDecimal.valueOf(request.getIncome()).multiply(creditLimitValue);

            // Max Loan คือค่าที่น้อยที่สุดระหว่าง calculatedCreditLimit กับ maxLoanConfig
            int finalMaxLoan = Math.min(calculatedCreditLimit.intValue(), maxLoanConfig);

            
            // สร้าง List ของ LoanDetailDTO โดยแปลงจาก CarInterrateRate
            List<LoanDetailDTO> loanInstallmentDetails = carInterrateRates.stream()
                    .map(rate -> {
                        LoanDetailDTO dto = new LoanDetailDTO();
                        dto.setNoOfInstallment(rate.getNoOfInstallment());
                        dto.setCreditLimit(rate.getCreditLimit());
                        dto.setInterestRate(rate.getInterateRate());
                        return dto;
                    })
                    .collect(Collectors.toList());

            // 5. สร้าง CalculateLoanDTO เพื่อรวบรวมข้อมูลทั้งหมด
            CalculateLoanDTO calculateLoanDTO = new CalculateLoanDTO();
            calculateLoanDTO.setMinLoan(minLoanConfig);
            calculateLoanDTO.setMaxLoan(finalMaxLoan);
            calculateLoanDTO.setLoanInstallmentDetails(loanInstallmentDetails);

            // 6. กำหนด Response ให้เป็น Success
            response.setResponseCode("0000");
            response.setResponseMessage("Success");
            response.setLoanDetails(Optional.of(calculateLoanDTO));

        } catch (Exception e) {
            // หากเกิดข้อผิดพลาดอื่น ๆ ที่ไม่คาดคิด
            System.err.println("Error calculating loan limit: " + e.getMessage()); // เพิ่ม Log
            response.setResponseCode("5000");
            response.setResponseMessage("เกิดข้อผิดพลาดในระบบที่ไม่คาดคิด");
            response.setLoanDetails(Optional.empty());
        }

        return response;
    }
}