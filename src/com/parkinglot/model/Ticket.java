package com.parkinglot.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Ticket {
    private final String ticketId;
    private final Vehicle vehicle;
    private final ParkingSpot spot;
    private final LocalDateTime entryTime;
    private LocalDateTime exitTime;

    public Ticket(String ticketId, Vehicle vehicle, ParkingSpot spot, LocalDateTime entryTime) {
        this.ticketId = ticketId;
        this.vehicle = vehicle;
        this.spot = spot;
        this.entryTime = entryTime;
    }

    public void markExit(LocalDateTime exitTime) {
        this.exitTime = exitTime;
    }

    public long getDurationInHours() {
        LocalDateTime end = exitTime != null ? exitTime : LocalDateTime.now();
        long minutes = ChronoUnit.MINUTES.between(entryTime, end);
        // round up to the nearest hour, minimum 1 hour
        return Math.max(1, (long) Math.ceil(minutes / 60.0));
    }

    public String getTicketId() {
        return ticketId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ParkingSpot getSpot() {
        return spot;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }
}
