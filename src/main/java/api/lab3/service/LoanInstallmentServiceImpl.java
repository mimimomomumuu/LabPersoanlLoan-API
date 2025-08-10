package api.lab3.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.lab3.entity.CarInterrateRate;
import api.lab3.model.LoanInstallmentDTO;
import api.lab3.repository.CarInterrateRateRepository;
import api.lab3.request.CalculateLoanRequest;
import api.lab3.response.LoanInstallmentResponse;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class LoanInstallmentServiceImpl implements LoanInstallmentService {
    
    @Autowired
    private CarInterrateRateRepository carInterrateRateRepository;


    public LoanInstallmentResponse getLoanInstallmentDetails(CalculateLoanRequest request) {
        LoanInstallmentResponse response = new LoanInstallmentResponse();
        
        try {
            // 1. ค้นหาข้อมูลจาก Repository เดิม
            List<CarInterrateRate> carInterrateRates = carInterrateRateRepository
                    .findByPloanOccupationIdAndPloanSalaryStartLessThanEqualAndPloanSalaryEndGreaterThanEqual(
                            request.getLovId(), request.getIncome(), request.getIncome()
                    );
            
            if (carInterrateRates.isEmpty()) {
                response.setResponseCode("0004");
                response.setResponseMessage("ไม่พบข้อมูลงวดผ่อนชำระสำหรับเกณฑ์ที่กำหนด");
                response.setData(null);
                return response;
            }

            // 2. แปลง Entity เป็น DTO
            List<LoanInstallmentDTO> installmentDetails = carInterrateRates.stream()
                    .map(rate -> {
                        LoanInstallmentDTO dto = new LoanInstallmentDTO();
                        dto.setNoOfInstallment(rate.getNoOfInstallment());
                        dto.setInterestRate(rate.getInterateRate());
                        return dto;
                    })
                    .collect(Collectors.toList());

            // 3. กำหนด Response
            response.setResponseCode("0000");
            response.setResponseMessage("Success");
            response.setData(installmentDetails);

        } catch (Exception e) {
            response.setResponseCode("5000");
            response.setResponseMessage("เกิดข้อผิดพลาดในระบบที่ไม่คาดคิด");
            response.setData(null);
        }
        
        return response;
    }
}
