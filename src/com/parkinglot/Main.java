package com.parkinglot;

import com.parkinglot.factory.ParkingSpotFactory;
import com.parkinglot.model.*;
import com.parkinglot.observer.DisplayBoardObserver;
import com.parkinglot.strategy.HourlyPricingStrategy;

import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        ParkingLot lot = ParkingLot.getInstance();
        lot.setPricingStrategy(new HourlyPricingStrategy());
        lot.addObserver(new DisplayBoardObserver());

        // Build a tiny floor: 1 small, 1 medium, 1 large spot.
        ParkingFloor floor1 = new ParkingFloor(1);
        floor1.addSpot(ParkingSpotFactory.createSpot(1, 1, ParkingSpotType.SMALL));
        floor1.addSpot(ParkingSpotFactory.createSpot(1, 2, ParkingSpotType.MEDIUM));
        floor1.addSpot(ParkingSpotFactory.createSpot(1, 3, ParkingSpotType.LARGE));
        lot.addFloor(floor1);

        // Park a car.
        Vehicle car = new Vehicle("PB-1234", VehicleType.CAR);
        Optional<Ticket> ticketOpt = lot.parkVehicle(car);

        if (ticketOpt.isPresent()) {
            Ticket ticket = ticketOpt.get();
            System.out.println("Parked " + car + " -> ticket " + ticket.getTicketId()
                    + " at spot " + ticket.getSpot().getId());

            // Simulate leaving immediately and paying.
            double fee = lot.unparkVehicle(ticket.getTicketId());
            System.out.println("Fee for ticket " + ticket.getTicketId() + ": $" + fee);
        } else {
            System.out.println("Sorry, the lot is full.");
        }

        // Fill up the floor to demonstrate the "floor full" notification.
        lot.parkVehicle(new Vehicle("PB-0001", VehicleType.MOTORCYCLE));
        lot.parkVehicle(new Vehicle("PB-0002", VehicleType.CAR));
        lot.parkVehicle(new Vehicle("PB-0003", VehicleType.TRUCK)); // this should trigger "floor full"
    }
}
