package api.lab3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import api.lab3.entity.Lov;

public interface LovRepository extends JpaRepository<Lov, Integer> {
    
    List<Lov> findByLovTypeOrderByLovOrder(String lovType);
}

/*`findByLovType`: เงื่อนไขในการกรอง (Filtering Condition)

    * บอกให้ Spring Data JPA ค้นหาเฉพาะ `Lov` Entity ที่มีค่าในคอลัมน์ **`LOV_TYPE` ตรงกับ `lovType` Parameter** ที่ส่งเข้ามา

    * **ตัวอย่าง:** หากส่ง `"OCCUPATION"` เข้าไป จะดึงเฉพาะรายการที่ระบุว่าเป็นประเภท "อาชีพ" เท่านั้น



* `OrderByLovOrder`: นี่คือ คำสั่งในการจัดเรียง (Sorting Instruction)

    * บอกให้ Spring Data JPA เรียงลำดับผลลัพธ์ที่ได้จากการค้นหา **ตามคอลัมน์ `LOV_ORDER`**

    * ส่วนนี้เป็นเพียง **"Keyword" ในชื่อเมธอด** ไม่ใช่ Parameter ที่เราส่งค่าเข้าไป แต่ Spring Data JPA จะใช้มันเพื่อสร้าง `ORDER BY LOV_ORDER ASC` ใน SQL Query โดยอัตโนมัติ



* `String lovType`: นี่คือ **ค่า (Value)** ที่คุณจะส่งเข้าไปเพื่อใช้เป็นเงื่อนไขในการกรอง `LOV_TYPE` ในส่วน `WHERE` clause ของ SQL

**สรุป:** เมธอดนี้จะดึงข้อมูล `Lov` ทั้งหมดที่เป็นประเภทตามที่ระบุ (เช่น "OCCUPATION") และจัดเรียงข้อมูลเหล่านั้นตามลำดับของ `LOV_ORDER` เพื่อให้ได้ผลลัพธ์ที่เรียงอย่างถูกต้อง (เช่น เพื่อใช้ใน Dropdown)
 */
