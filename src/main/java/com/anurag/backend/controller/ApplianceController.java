package com.anurag.backend.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.anurag.backend.entity.Appliance;
import com.anurag.backend.service.ApplianceService;

/**
 * Controller Class: ApplianceController
 * REST Controller exposing endpoints to the frontend.
 */
@RestController
@RequestMapping("/api/appliances")
@CrossOrigin(origins = "*")
public class ApplianceController {

    @Autowired
    private ApplianceService applianceService;

    // GET /api/appliances (supports optional ?userId=...)
    @GetMapping
    public List<Appliance> getAllAppliances(@RequestParam(required = false) Long userId) {
        if (userId != null) {
            return applianceService.getAppliancesByUser(userId);
        }
        return applianceService.getAllAppliances();
    }

    // GET /api/appliances/user/{userId}
    @GetMapping("/user/{userId}")
    public List<Appliance> getAppliancesByUser(@PathVariable Long userId) {
        return applianceService.getAppliancesByUser(userId);
    }

    // GET /api/appliances/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Appliance> getApplianceById(@PathVariable Long id) {
        Appliance appliance = applianceService.getApplianceById(id);
        if (appliance == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(appliance, HttpStatus.OK);
    }

    // POST /api/appliances
    @PostMapping
    public ResponseEntity<?> addAppliance(@RequestBody Appliance appliance) {
        try {
            Appliance saved = applianceService.saveAppliance(appliance);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error saving appliance: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // PUT /api/appliances/{id}
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAppliance(@PathVariable Long id, @RequestBody Appliance appliance) {
        try {
            Appliance updated = applianceService.updateAppliance(id, appliance);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error updating appliance: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE /api/appliances/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAppliance(@PathVariable Long id) {
        try {
            applianceService.deleteAppliance(id);
            return new ResponseEntity<>("Appliance deleted successfully", HttpStatus.OK);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error deleting appliance: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
