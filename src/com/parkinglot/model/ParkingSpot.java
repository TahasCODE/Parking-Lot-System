package com.parkinglot.model;

public class ParkingSpot {
    private final String id;
    private final ParkingSpotType type;
    private final int floorNumber;
    private boolean free = true;
    private Vehicle currentVehicle;

    public ParkingSpot(String id, ParkingSpotType type, int floorNumber) {
        this.id = id;
        this.type = type;
        this.floorNumber = floorNumber;
    }

    public int getFloorNumber() {
        return floorNumber;
    }


    public boolean canFit(VehicleType vehicleType) {
        switch (vehicleType) {
            case MOTORCYCLE:
                return true; // fits in SMALL, MEDIUM, or LARGE
            case CAR:
                return type == ParkingSpotType.MEDIUM || type == ParkingSpotType.LARGE;
            case TRUCK:
                return type == ParkingSpotType.LARGE;
            default:
                return false;
        }
    }

    public void assignVehicle(Vehicle vehicle) {
        this.currentVehicle = vehicle;
        this.free = false;
    }

    public void removeVehicle() {
        this.currentVehicle = null;
        this.free = true;
    }

    public boolean isFree() {
        return free;
    }

    public String getId() {
        return id;
    }

    public ParkingSpotType getType() {
        return type;
    }

    public Vehicle getCurrentVehicle() {
        return currentVehicle;
    }
}
