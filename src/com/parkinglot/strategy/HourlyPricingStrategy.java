package com.parkinglot.strategy;

import com.parkinglot.model.Ticket;
import com.parkinglot.model.VehicleType;

import java.util.EnumMap;
import java.util.Map;

public class HourlyPricingStrategy implements PricingStrategy {

    private final Map<VehicleType, Double> hourlyRates = new EnumMap<>(VehicleType.class);

    public HourlyPricingStrategy() {
        hourlyRates.put(VehicleType.MOTORCYCLE, 10.0);
        hourlyRates.put(VehicleType.CAR, 20.0);
        hourlyRates.put(VehicleType.TRUCK, 35.0);
    }

    @Override
    public double calculateFee(Ticket ticket) {
        double rate = hourlyRates.get(ticket.getVehicle().getType());
        long hours = ticket.getDurationInHours();
        return rate * hours;
    }
}
