package com.parkinglot.strategy;

import com.parkinglot.model.Ticket;


public interface PricingStrategy {
    double calculateFee(Ticket ticket);
}
