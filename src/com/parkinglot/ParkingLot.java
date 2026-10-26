package com.parkinglot;

import com.parkinglot.model.*;
import com.parkinglot.observer.ParkingLotObserver;
import com.parkinglot.strategy.PricingStrategy;

import java.time.LocalDateTime;
import java.util.*;


public class ParkingLot {

    private static ParkingLot instance;

    private final List<ParkingFloor> floors = new ArrayList<>();
    private final Map<String, Ticket> activeTickets = new HashMap<>();
    private final List<ParkingLotObserver> observers = new ArrayList<>();
    private PricingStrategy pricingStrategy;
    private int ticketCounter = 1;

    private ParkingLot() {
    }

    public static synchronized ParkingLot getInstance() {
        if (instance == null) {
            instance = new ParkingLot();
        }
        return instance;
    }

    public void addFloor(ParkingFloor floor) {
        floors.add(floor);
    }

    public void setPricingStrategy(PricingStrategy strategy) {
        this.pricingStrategy = strategy;
    }

    public void addObserver(ParkingLotObserver observer) {
        observers.add(observer);
    }

    // Entry gate: find a free spot anywhere in the lot and issue a ticket.
    public Optional<Ticket> parkVehicle(Vehicle vehicle) {
        for (ParkingFloor floor : floors) {
            Optional<ParkingSpot> spotOpt = floor.findFreeSpot(vehicle.getType());
            if (spotOpt.isPresent()) {
                ParkingSpot spot = spotOpt.get();
                spot.assignVehicle(vehicle);

                String ticketId = "T" + (ticketCounter++);
                Ticket ticket = new Ticket(ticketId, vehicle, spot, LocalDateTime.now());
                activeTickets.put(ticketId, ticket);

                if (floor.isFull()) {
                    notifyFloorFull(floor.getFloorNumber());
                }
                return Optional.of(ticket);
            }
        }
        return Optional.empty(); // lot is full
    }

    // Exit gate: close out the ticket, free the spot, calculate the fee.
    public double unparkVehicle(String ticketId) {
        Ticket ticket = activeTickets.get(ticketId);
        if (ticket == null) {
            throw new IllegalArgumentException("No active ticket found: " + ticketId);
        }

        ticket.markExit(LocalDateTime.now());
        double fee = pricingStrategy.calculateFee(ticket);

        ParkingSpot spot = ticket.getSpot();
        int floorNumber = spot.getFloorNumber();
        spot.removeVehicle();
        activeTickets.remove(ticketId);

        notifySpotFreed(floorNumber);
        return fee;
    }

    private void notifyFloorFull(int floorNumber) {
        observers.forEach(o -> o.onFloorFull(floorNumber));
    }

    private void notifySpotFreed(int floorNumber) {
        observers.forEach(o -> o.onSpotFreed(floorNumber));
    }
}
