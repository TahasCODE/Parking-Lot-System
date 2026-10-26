package com.parkinglot.observer;

public class DisplayBoardObserver implements ParkingLotObserver {

    @Override
    public void onFloorFull(int floorNumber) {
        System.out.println("[Display Board] Floor " + floorNumber + " is now FULL.");
    }

    @Override
    public void onSpotFreed(int floorNumber) {
        System.out.println("[Display Board] A spot just opened up on floor " + floorNumber + ".");
    }
}
