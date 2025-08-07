package api.lab3.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import api.lab3.entity.SystemConfig;

public interface SystemConfigRepository extends JpaRepository<SystemConfig, Integer> {
    
    Optional<SystemConfig> findByModuleCodeAndConfigName(String moduleCode, String configName);
}
