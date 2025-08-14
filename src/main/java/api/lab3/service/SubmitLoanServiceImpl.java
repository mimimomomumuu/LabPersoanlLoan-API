package api.lab3.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.lab3.entity.LoanRequest;
import api.lab3.model.SubmitLoanDTO;
import api.lab3.repository.LoanRequestRepository;
import api.lab3.request.LoanApplicationRequest;
import api.lab3.response.SubmitLoanResponse;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Slf4j
@Service

public class SubmitLoanServiceImpl implements SubmitLoanService {
    
    @Autowired
    private LoanRequestRepository loanRequestRepository;

    @Override
    public SubmitLoanResponse submitLoanApplication(LoanApplicationRequest request) {
        log.info("Starting loan application submission for citizenId: {}", request.getCitizenId());
        
        // 1. สร้าง Entity จาก DTO และทำการแปลงค่า
        LoanRequest loanRequestEntity = new LoanRequest();

        // นำข้อมูลจาก DTO ที่มาจาก Frontend มาใส่ใน Entity
            // --- ข้อมูลจากหน้า 1 ---
        loanRequestEntity.setOccupationId(request.getOccupationId());
        loanRequestEntity.setSalary(request.getSalary());
            //---------------

            // --- ข้อมูลจากหน้า 2 ---
        loanRequestEntity.setLoanAmount(request.getDesiredLoan()); // นำ desiredLoan มาใช้เป็น loanAmount
        loanRequestEntity.setInterateRate(request.getInterestRate());
        loanRequestEntity.setNoOfInstallment(request.getNoOfInstallment());
            //---------------

            // --- ข้อมูลจากหน้า 3 ---
        loanRequestEntity.setFirstName(request.getFirstName());
        loanRequestEntity.setLastName(request.getLastName());
        loanRequestEntity.setGender(request.getGender());
        
            // แปลง String dateOfBirth จาก DTO เป็น LocalDate
        try {
            loanRequestEntity.setDateOfBirth(LocalDate.parse(request.getDateOfBirth()));
        } catch (DateTimeParseException e) {
            log.error("Failed to parse dateOfBirth: {}", request.getDateOfBirth(), e);
            // ถ้าแปลงไม่ได้ อาจจะส่ง Response error กลับไป
            return createErrorResponse("0001", "Invalid date format");
        }
        
        loanRequestEntity.setMobileNo(request.getMobileNo());
        loanRequestEntity.setCitizenId(request.getCitizenId());
            //---------------

        // 💡 ไม่ต้อง hardcode ค่า REQ_LOAN_TYPE, CAR_MAKE_ID, CAR_MODEL_ID ที่นี่
        // เนื่องจาก Entity Class มี @PrePersist method ที่จัดการค่าเริ่มต้นเหล่านี้โดยอัตโนมัติแล้ว

        // ⭐ บันทึกข้อมูลครั้งแรก โดยให้ REQ_CUST_ID เป็นค่า Placeholder
        //    เนื่องจาก REQ_CUST_ID ไม่ให้ null
        loanRequestEntity.setReqCustId(-1); // ใช้ค่าหลอก -1 ชั่วคราว

        /*การใช้ 0 เป็นค่า Placeholder แทน -1
            สามารถทำได้ โค้ดจะทำงานได้เหมือนกัน แต่การใช้ -1 อาจจะดีกว่าในแง่ของความหมาย
            0: ในหลายระบบ 0 อาจจะมีความหมายว่าเป็น ID ที่ถูกต้องหรือมีอยู่จริง ซึ่งอาจทำให้เกิดความสับสนหรือข้อผิดพลาดได้หากมี ID ที่เป็น 0
            -1: การใช้ -1 จะสื่อความหมายชัดเจนกว่าว่าเป็น ค่าชั่วคราว หรือ ค่าที่ไม่ถูกต้อง ทำให้ง่ายต่อการ Debug และตรวจสอบว่าข้อมูลไหนที่ถูก Hardcode ไว้
            ดังนั้น การใช้ -1 จึงเป็นที่นิยมมากกว่าเมื่อต้องการใช้ค่า Placeholder */

        // 2. บันทึก Entity ลงในฐานข้อมูล
        LoanRequest savedEntity = loanRequestRepository.save(loanRequestEntity);
        
        // 3. นำ ID ที่ได้มาอัปเดต REQ_CUST_ID และบันทึกอีกครั้ง
        savedEntity.setReqCustId(savedEntity.getId());
        loanRequestRepository.save(savedEntity);

        // 4. สร้าง Response DTO และส่งคืน
        SubmitLoanDTO data = new SubmitLoanDTO();
        data.setReqId(savedEntity.getId());

        SubmitLoanResponse response = new SubmitLoanResponse();
        response.setResponseCode("0000");
        response.setResponseMessage("Success");
        response.setData(data);

        log.info("Successfully submitted loan application with reqId: {}", savedEntity.getId());
        return response;
    }

    private SubmitLoanResponse createErrorResponse(String code, String message) {
        SubmitLoanResponse response = new SubmitLoanResponse();
        response.setResponseCode(code);
        response.setResponseMessage(message);
        return response;
    }
}
