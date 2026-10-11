package com.anurag.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.anurag.backend.dto.AuthResponse;
import com.anurag.backend.dto.LoginRequest;
import com.anurag.backend.dto.RegisterRequest;
import com.anurag.backend.entity.Appliance;
import com.anurag.backend.repository.UserRepository;
import com.anurag.backend.service.ApplianceService;
import com.anurag.backend.service.UserService;

@SpringBootTest
class BackendApplicationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplianceService applianceService;

    @Test
    void contextLoads() {
    }

    @Test
    void testApplianceCalculation() {
        Appliance appliance = new Appliance("Test Fan", 100, 5, 30, 8.0);
        Appliance saved = applianceService.saveAppliance(appliance);

        assertNotNull(saved.getId());
        // Energy = (100 * 5 * 30) / 1000 = 15 kWh
        assertEquals(15.0, saved.getEnergyConsumed());
        // Cost = 15 * 8 = 120
        assertEquals(120.0, saved.getEstimatedCost());

        // Cleanup
        applianceService.deleteAppliance(saved.getId());
    }

    @Test
    void testUserRegistrationAndLogin() {
        String uniqueUser = "user_" + System.currentTimeMillis();
        String uniqueEmail = uniqueUser + "@test.com";

        // 1. Test Registration
        RegisterRequest registerReq = new RegisterRequest(uniqueUser, uniqueEmail, "password123", "Test User");
        AuthResponse regResponse = userService.register(registerReq);

        assertTrue(regResponse.isSuccess());
        assertEquals(uniqueUser, regResponse.getUsername());
        assertNotNull(regResponse.getUserId());

        // 2. Test Login with Username
        LoginRequest loginByUsername = new LoginRequest(uniqueUser, "password123");
        AuthResponse loginResponse1 = userService.login(loginByUsername);
        assertTrue(loginResponse1.isSuccess());
        assertEquals("Login successful", loginResponse1.getMessage());

        // 3. Test Login with Email
        LoginRequest loginByEmail = new LoginRequest(uniqueEmail, "password123");
        AuthResponse loginResponse2 = userService.login(loginByEmail);
        assertTrue(loginResponse2.isSuccess());

        // 4. Test Login with Wrong Password
        LoginRequest wrongPassReq = new LoginRequest(uniqueUser, "wrongPassword");
        assertThrows(IllegalArgumentException.class, () -> userService.login(wrongPassReq));

        // 5. Test Duplicate Registration
        assertThrows(IllegalArgumentException.class, () -> userService.register(registerReq));

        // Cleanup
        userRepository.deleteById(regResponse.getUserId());
    }

    @Test
    void testPerUserApplianceIsolation() {
        Long user1Id = 9991L;
        Long user2Id = 9992L;

        Appliance app1 = new Appliance("User1 Lamp", 60, 4, 30, 8.0);
        app1.setUserId(user1Id);
        Appliance savedApp1 = applianceService.saveAppliance(app1);

        Appliance app2 = new Appliance("User2 Heater", 2000, 2, 30, 8.0);
        app2.setUserId(user2Id);
        Appliance savedApp2 = applianceService.saveAppliance(app2);

        // Fetch User 1's appliances
        var user1Appliances = applianceService.getAppliancesByUser(user1Id);
        assertTrue(user1Appliances.stream().anyMatch(a -> a.getId().equals(savedApp1.getId())));
        assertFalse(user1Appliances.stream().anyMatch(a -> a.getId().equals(savedApp2.getId())));

        // Fetch User 2's appliances
        var user2Appliances = applianceService.getAppliancesByUser(user2Id);
        assertTrue(user2Appliances.stream().anyMatch(a -> a.getId().equals(savedApp2.getId())));
        assertFalse(user2Appliances.stream().anyMatch(a -> a.getId().equals(savedApp1.getId())));

        // Cleanup
        applianceService.deleteAppliance(savedApp1.getId());
        applianceService.deleteAppliance(savedApp2.getId());
    }
}
