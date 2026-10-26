package com.parkinglot.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ParkingFloor {
    private final int floorNumber;
    private final List<ParkingSpot> spots = new ArrayList<>();

    public ParkingFloor(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public void addSpot(ParkingSpot spot) {
        spots.add(spot);
    }

    public Optional<ParkingSpot> findFreeSpot(VehicleType vehicleType) {
        return spots.stream()
                .filter(ParkingSpot::isFree)
                .filter(spot -> spot.canFit(vehicleType))
                .findFirst();
    }

    public boolean isFull() {
        return spots.stream().noneMatch(ParkingSpot::isFree);
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public long freeSpotCount() {
        return spots.stream().filter(ParkingSpot::isFree).count();
    }
}
