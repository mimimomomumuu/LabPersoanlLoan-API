package api.lab3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import api.lab3.entity.LoanRequest;

@Repository
public interface LoanRequestRepository extends JpaRepository<LoanRequest, Integer> {
    // Spring Data JPA จะสร้างเมธอดพื้นฐานให้โดยอัตโนมัติ
}
