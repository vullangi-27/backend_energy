-- ========================================================
-- Smart Energy Consumption Management System
-- Database Creation & Setup Script
-- Compatible with MySQL Server 8.0+
-- ========================================================

-- 1. Create database if not exists
CREATE DATABASE IF NOT EXISTS `smart_energy_db`
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE `smart_energy_db`;

-- 2. Create 'users' table
CREATE TABLE IF NOT EXISTS `users` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(255) NOT NULL UNIQUE,
    `email` VARCHAR(255) NOT NULL UNIQUE,
    `password` VARCHAR(255) NOT NULL,
    `full_name` VARCHAR(255) DEFAULT NULL,
    `role` VARCHAR(50) DEFAULT 'USER',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 3. Create 'appliances' table
CREATE TABLE IF NOT EXISTS `appliances` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `power_watts` DOUBLE NOT NULL,
    `hours_per_day` DOUBLE NOT NULL,
    `days_used` INT NOT NULL,
    `electricity_rate` DOUBLE NOT NULL,
    `energy_consumed` DOUBLE NOT NULL,
    `estimated_cost` DOUBLE NOT NULL,
    `user_id` BIGINT DEFAULT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_appliance_user` FOREIGN KEY (`user_id`) 
        REFERENCES `users` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 4. Insert Default Users (SHA-256 hashed passwords)
-- Password for 'minnu' is 'minnu' -> SHA-256: 3effdfd8559546fb28eb5dad07358373aa2b593d62f9f4b764863344d09ad7bd
-- Password for 'demo' is 'demo123' -> SHA-256: d3ad9315b7be5dd53b31a273b3b3aba5defe700808305aa16a3062b76658a791
INSERT INTO `users` (`username`, `email`, `password`, `full_name`, `role`)
VALUES 
    ('minnu', 'vullangiminnu@gmail.com', '3effdfd8559546fb28eb5dad07358373aa2b593d62f9f4b764863344d09ad7bd', 'Vullangi Minnu', 'USER'),
    ('demo', 'demo@smartenergy.com', 'd3ad9315b7be5dd53b31a273b3b3aba5defe700808305aa16a3062b76658a791', 'Demo User', 'USER')
ON DUPLICATE KEY UPDATE `full_name` = VALUES(`full_name`);

-- 5. Seed sample appliances for user 'minnu'
-- Ceiling Fan: 75W, 6 hrs/day, 30 days, Rate 8 -> Energy: 13.50 kWh, Cost: ₹108.00
-- Refrigerator: 200W, 24 hrs/day, 30 days, Rate 8 -> Energy: 144.00 kWh, Cost: ₹1152.00
-- Air Conditioner: 1500W, 8 hrs/day, 30 days, Rate 8 -> Energy: 360.00 kWh, Cost: ₹2880.00
-- LED TV: 100W, 4 hrs/day, 30 days, Rate 8 -> Energy: 12.00 kWh, Cost: ₹96.00
SET @minnu_id = (SELECT `id` FROM `users` WHERE `username` = 'minnu' LIMIT 1);

INSERT INTO `appliances` (`name`, `power_watts`, `hours_per_day`, `days_used`, `electricity_rate`, `energy_consumed`, `estimated_cost`, `user_id`)
SELECT 'Ceiling Fan', 75, 6, 30, 8, 13.5, 108.0, @minnu_id
WHERE NOT EXISTS (SELECT 1 FROM `appliances` WHERE `user_id` = @minnu_id AND `name` = 'Ceiling Fan');

INSERT INTO `appliances` (`name`, `power_watts`, `hours_per_day`, `days_used`, `electricity_rate`, `energy_consumed`, `estimated_cost`, `user_id`)
SELECT 'Refrigerator', 200, 24, 30, 8, 144.0, 1152.0, @minnu_id
WHERE NOT EXISTS (SELECT 1 FROM `appliances` WHERE `user_id` = @minnu_id AND `name` = 'Refrigerator');

INSERT INTO `appliances` (`name`, `power_watts`, `hours_per_day`, `days_used`, `electricity_rate`, `energy_consumed`, `estimated_cost`, `user_id`)
SELECT 'Air Conditioner', 1500, 8, 30, 8, 360.0, 2880.0, @minnu_id
WHERE NOT EXISTS (SELECT 1 FROM `appliances` WHERE `user_id` = @minnu_id AND `name` = 'Air Conditioner');

INSERT INTO `appliances` (`name`, `power_watts`, `hours_per_day`, `days_used`, `electricity_rate`, `energy_consumed`, `estimated_cost`, `user_id`)
SELECT 'LED TV', 100, 4, 30, 8, 12.0, 96.0, @minnu_id
WHERE NOT EXISTS (SELECT 1 FROM `appliances` WHERE `user_id` = @minnu_id AND `name` = 'LED TV');

-- Show summary of created database
SHOW TABLES;
