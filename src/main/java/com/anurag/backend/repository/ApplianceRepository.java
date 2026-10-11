package com.anurag.backend.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.anurag.backend.entity.Appliance;

/**
 * Repository Interface: ApplianceRepository
 * Provides CRUD operations using Spring Data JPA.
 */
@Repository
public interface ApplianceRepository extends JpaRepository<Appliance, Long> {
    List<Appliance> findByUserId(Long userId);
}
