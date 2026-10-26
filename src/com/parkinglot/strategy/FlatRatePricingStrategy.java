package com.parkinglot.strategy;

import com.parkinglot.model.Ticket;


public class FlatRatePricingStrategy implements PricingStrategy {

    private final double flatRate;

    public FlatRatePricingStrategy(double flatRate) {
        this.flatRate = flatRate;
    }

    @Override
    public double calculateFee(Ticket ticket) {
        return flatRate;
    }
}
