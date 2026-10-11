package com.anurag.backend.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.anurag.backend.entity.Appliance;
import com.anurag.backend.repository.ApplianceRepository;

/**
 * Service Class: ApplianceService
 * Contains business logic and invokes calculation before saving.
 */
@Service
public class ApplianceService {

    @Autowired
    private ApplianceRepository applianceRepository;

    // 1. Fetch all appliances
    public List<Appliance> getAllAppliances() {
        return applianceRepository.findAll();
    }

    // 1b. Fetch appliances by User ID
    public List<Appliance> getAppliancesByUser(Long userId) {
        if (userId != null) {
            return applianceRepository.findByUserId(userId);
        }
        return applianceRepository.findAll();
    }

    // 2. Fetch single appliance by ID
    public Appliance getApplianceById(Long id) {
        return applianceRepository.findById(id).orElse(null);
    }

    // 3. Add appliance with validation and calculation
    public Appliance saveAppliance(Appliance appliance) {
        validateAppliance(appliance);
        appliance.calculateEnergyAndCost();
        return applianceRepository.save(appliance);
    }

    // 4. Update appliance
    public Appliance updateAppliance(Long id, Appliance updatedDetails) {
        Appliance existingAppliance = applianceRepository.findById(id).orElse(null);
        if (existingAppliance == null) {
            throw new RuntimeException("Appliance not found with id: " + id);
        }

        validateAppliance(updatedDetails);

        existingAppliance.setName(updatedDetails.getName());
        existingAppliance.setPowerWatts(updatedDetails.getPowerWatts());
        existingAppliance.setHoursPerDay(updatedDetails.getHoursPerDay());
        existingAppliance.setDaysUsed(updatedDetails.getDaysUsed());
        existingAppliance.setElectricityRate(updatedDetails.getElectricityRate());
        if (updatedDetails.getUserId() != null) {
            existingAppliance.setUserId(updatedDetails.getUserId());
        }

        // Recalculate with updated inputs
        existingAppliance.calculateEnergyAndCost();

        return applianceRepository.save(existingAppliance);
    }

    // 5. Delete appliance
    public void deleteAppliance(Long id) {
        if (!applianceRepository.existsById(id)) {
            throw new RuntimeException("Appliance not found with id: " + id);
        }
        applianceRepository.deleteById(id);
    }

    private void validateAppliance(Appliance appliance) {
        if (appliance == null) {
            throw new IllegalArgumentException("Appliance details cannot be empty.");
        }
        if (appliance.getName() == null || appliance.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Appliance name cannot be empty.");
        }
        if (appliance.getPowerWatts() <= 0) {
            throw new IllegalArgumentException("Power in watts must be greater than 0.");
        }
        if (appliance.getHoursPerDay() < 0 || appliance.getHoursPerDay() > 24) {
            throw new IllegalArgumentException("Hours per day must be between 0 and 24.");
        }
        if (appliance.getDaysUsed() <= 0) {
            throw new IllegalArgumentException("Days used must be greater than 0.");
        }
        if (appliance.getElectricityRate() <= 0) {
            throw new IllegalArgumentException("Electricity rate must be greater than 0.");
        }
    }
}
