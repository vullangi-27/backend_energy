package com.anurag.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entity Class: Appliance
 * Maps directly to the 'appliances' table in MySQL.
 */
@Entity
@Table(name = "appliances")
public class Appliance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "power_watts", nullable = false)
    private double powerWatts;

    @Column(name = "hours_per_day", nullable = false)
    private double hoursPerDay;

    @Column(name = "days_used", nullable = false)
    private int daysUsed;

    @Column(name = "electricity_rate", nullable = false)
    private double electricityRate;

    @Column(name = "energy_consumed")
    private double energyConsumed;

    @Column(name = "estimated_cost")
    private double estimatedCost;

    @Column(name = "user_id")
    private Long userId;

    // Default Constructor
    public Appliance() {
    }

    // Parameterized Constructor
    public Appliance(String name, double powerWatts, double hoursPerDay, int daysUsed, double electricityRate) {
        this.name = name;
        this.powerWatts = powerWatts;
        this.hoursPerDay = hoursPerDay;
        this.daysUsed = daysUsed;
        this.electricityRate = electricityRate;
    }

    // Calculation method:
    // Energy (kWh) = (Power in Watts * Hours per day * Days) / 1000
    // Cost (₹) = Energy * Electricity Rate
    public void calculateEnergyAndCost() {
        double energy = (this.powerWatts * this.hoursPerDay * this.daysUsed) / 1000.0;
        this.energyConsumed = Math.round(energy * 100.0) / 100.0;

        double cost = this.energyConsumed * this.electricityRate;
        this.estimatedCost = Math.round(cost * 100.0) / 100.0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPowerWatts() {
        return powerWatts;
    }

    public void setPowerWatts(double powerWatts) {
        this.powerWatts = powerWatts;
    }

    public double getHoursPerDay() {
        return hoursPerDay;
    }

    public void setHoursPerDay(double hoursPerDay) {
        this.hoursPerDay = hoursPerDay;
    }

    public int getDaysUsed() {
        return daysUsed;
    }

    public void setDaysUsed(int daysUsed) {
        this.daysUsed = daysUsed;
    }

    public double getElectricityRate() {
        return electricityRate;
    }

    public void setElectricityRate(double electricityRate) {
        this.electricityRate = electricityRate;
    }

    public double getEnergyConsumed() {
        return energyConsumed;
    }

    public void setEnergyConsumed(double energyConsumed) {
        this.energyConsumed = energyConsumed;
    }

    public double getEstimatedCost() {
        return estimatedCost;
    }

    public void setEstimatedCost(double estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
