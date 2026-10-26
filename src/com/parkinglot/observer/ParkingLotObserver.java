package com.parkinglot.observer;


public interface ParkingLotObserver {
    void onFloorFull(int floorNumber);
    void onSpotFreed(int floorNumber);
}
