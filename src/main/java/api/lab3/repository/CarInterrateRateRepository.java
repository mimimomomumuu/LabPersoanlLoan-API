package api.lab3.repository;

import api.lab3.entity.CarInterrateRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarInterrateRateRepository extends JpaRepository<CarInterrateRate, Integer> {

    List<CarInterrateRate> findByPloanOccupationIdAndPloanSalaryStartLessThanEqualAndPloanSalaryEndGreaterThanEqual(int occupationId, int incomeStart, int incomeEnd);
}

/*
findByPloanOccupationIdAnd...

เงื่อนไขแรก: ค้นหาข้อมูลที่คอลัมน์ PLOAN_OCCUPATION_ID ตรงกับ occupationId ที่ส่งมา

ตัวอย่าง: หากผู้ใช้เลือก "พนักงานบริษัทเอกชน" ที่มี id เป็น 1, เมธอดจะหาข้อมูลที่มี PLOAN_OCCUPATION_ID = 1

...AndPloanSalaryStartLessThanEqual...

เงื่อนไขที่สอง: ค้นหาข้อมูลที่คอลัมน์ PLOAN_SALARY_START น้อยกว่าหรือเท่ากับ incomeStart

ตัวอย่าง: ถ้าผู้ใช้มีรายได้ 40,000 บาท, เมธอดจะหาแถวที่ PLOAN_SALARY_START <= 40000

...AndPloanSalaryEndGreaterThanEqual

เงื่อนไขที่สาม: ค้นหาข้อมูลที่คอลัมน์ PLOAN_SALARY_END มากกว่าหรือเท่ากับ incomeEnd

ตัวอย่าง: ถ้าผู้ใช้มีรายได้ 40,000 บาท, เมธอดจะหาแถวที่ PLOAN_SALARY_END >= 40000

สรุป: เมธอดนี้จะค้นหาข้อมูลที่ รหัสอาชีพ ตรงกับที่เลือก และ รายได้ต่อเดือน อยู่ในช่วงที่กำหนด เพื่อหาข้อมูลวงเงินสินเชื่อที่เหมาะสมสำหรับผู้ใช้ครับ
 */
