package com.parkinglot.factory;

import com.parkinglot.model.ParkingSpot;
import com.parkinglot.model.ParkingSpotType;


public class ParkingSpotFactory {

    public static ParkingSpot createSpot(int floorNumber, int spotNumber, ParkingSpotType type) {
        String id = "F" + floorNumber + "-" + type.name().charAt(0) + spotNumber;
        return new ParkingSpot(id, type, floorNumber);
    }
}
