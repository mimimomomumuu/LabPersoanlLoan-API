package api.lab3.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.lab3.entity.Lov;
import api.lab3.entity.SystemConfig;
import api.lab3.model.OccupationDTO;
import api.lab3.repository.LovRepository;
import api.lab3.repository.SystemConfigRepository;
import api.lab3.response.OccupationResponse;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class OccupationServiceImpl implements OccupationService {

    @Autowired
    LovRepository lovRepository;

    @Autowired
    SystemConfigRepository systemConfigRepository;

    @Override
    public OccupationResponse getOccupations() {
        OccupationResponse response = new OccupationResponse();

        try {
            // 1. ดึงข้อมูลอาชีพจาก LovRepository
            // "OCCUPATION" คือ LOV_TYPE สำหรับประเภทอาชีพ
            List<Lov> lovs = lovRepository.findByLovTypeOrderByLovOrder("OCCUPATION");

            // แปลง Lov Entity เป็น OccupationDTO
            List<OccupationDTO> occupations = lovs.stream()
                    .map(lov -> new OccupationDTO(lov.getLovId(), lov.getLovNameTH()))
                    .collect(Collectors.toList());
                    /*ถ้าไม่ใส่ @AllArgsConstructor ที่ DTO
                     * จะต้อง ใส่เป็น 
                     * .map(lov -> {
                        OccupationDTO dto = new OccupationDTO(); // ใช้ Default Constructor
                        dto.setLovId(lov.getLovId());
                        dto.setLovNameTH(lov.getLovNameTH());
                        return dto;
                        })
                     */

            response.setData(occupations);

            // 2. ดึงค่า MIN_SALARY และ MAX_SALARY จาก SystemConfigRepository
            // ใช้เมธอด getSystemConfigValue ที่เราสร้างไว้ใน Service เดียวกัน
            int minSalary = getSystemConfigValue("PRESONAL_LONE", "MIN_SALARY");
            int maxSalary = getSystemConfigValue("PRESONAL_LONE", "MAX_SALARY");

            response.setMinSalary(minSalary);
            response.setMaxSalary(maxSalary);

            response.setResponseCode("0000");
            response.setResponseMessage("Success");

        } catch (Exception e) {
            // Log error สำหรับ Debugging
            System.err.println("Error fetching occupations or system config: " + e.getMessage());
            response.setResponseCode("0004"); // หรือโค้ด Error อื่นๆ ที่เหมาะสม
            response.setResponseMessage("Failed to retrieve occupation data or system configuration.");
            // อาจจะกำหนดค่าเริ่มต้นให้ minSalary, maxSalary เพื่อไม่ให้ Frontend พัง
            response.setMinSalary(15000); // หรือค่า Default ที่เหมาะสม
            response.setMaxSalary(9999999); // หรือค่า Default ที่เหมาะสม
        }
        return response;
    }

@Override
public int getSystemConfigValue(String moduleCode, String configName) {
    // 1. ใช้ .orElseThrow() เพื่อจัดการกรณีที่ไม่พบข้อมูล
    SystemConfig systemConfig = systemConfigRepository
            .findByModuleCodeAndConfigName(moduleCode, configName)
            .orElseThrow(() -> new IllegalArgumentException(
                "System config '" + configName + "' for module '" + moduleCode + "' not found."
            ));

    String configValue = systemConfig.getConfigValue();
    try {
        // 2. ลองแปลงค่าเป็น int ถ้าไม่สำเร็จจะถูกจับใน NumberFormatException
        return Integer.parseInt(configValue);
    } catch (NumberFormatException e) {
        // 3. หากแปลงค่าไม่ได้ ให้ throw Exception เพื่อส่งสัญญาณข้อผิดพลาด
        // การส่ง Exception ต้นฉบับ (e) เข้าไปใน IllegalArgumentException เป็นสิ่งที่ถูกต้อง
        throw new IllegalArgumentException(
            "System config '" + configName + "' is not a valid number: " + configValue, e
        );
    }

    /*IllegalArgumentException = กรณีที่การตั้งค่า Config ไม่ถูกต้องจนทำให้ระบบอยู่ในสถานะที่ผิดปกติ
     * RuntimeException = Exception ที่ใช้งานง่ายและไม่ต้องกังวลเรื่องการเลือกประเภทที่ซับซ้อน
     * throw new RuntimeException()
    */
}
}
    /* การจัดการ Error
     * STATEMENT, RUNNING (ข้อมูลสำคัญ):
     * 
     * System.err.println(): ใช้ไม่ได้เด็ดขาด
     * เพราะจะทำให้โปรแกรมทำงานต่อไปโดยมีข้อมูลผิดพลาด ซึ่งเป็นอันตรายต่อระบบ
     * 
     * throw Exception: นี่คือวิธีที่ถูกต้องที่สุด
     * เพราะเป็นการบอกอย่างชัดเจนว่าเมธอดไม่สามารถทำงานต่อได้
     * และต้องการให้ระบบหยุดเพื่อแก้ไขปัญหา
     * 
     * return Response DTO: เป็นวิธีที่ไม่ถูกต้อง เพราะ Service Layer ไม่ควรสร้าง
     * Response DTO และการทำเช่นนั้นจะทำให้ปัญหาดูไม่ร้ายแรงเท่าที่ควร
     * 
     * MIN_SALARY (ข้อมูลเสริม):
     * 
     * return Response DTO: ใช้ไม่ได้ เพราะเมธอด getSystemConfigValue() อยู่ใน
     * Service Layer และไม่มี Response DTO ให้ใช้งาน
     * 
     * System.err.println(): ใช้ได้แต่ไม่แนะนำ เพราะถึงแม้จะทำให้โค้ดทำงานต่อได้
     * แต่ก็เป็นวิธีจัดการ Error ที่ไม่เป็นระบบและอาจทำให้เกิดความสับสนในภายหลัง
     * 
     * throw Exception: นี่คือวิธีที่ถูกต้องที่สุด แต่ต้องใช้ร่วมกับ try-catch
     * ในเมธอดที่เรียกใช้ (เช่น getOccupations()) เพื่อให้เมธอดนั้นสามารถจัดการกับ
     * Exception ที่ถูก throw มา โดยการกำหนดค่า Default และทำงานต่อไปได้
     */

