# Parking Lot System — LLD Practice Project

A small, self-contained Java implementation of the classic "design a parking lot"
interview question. No frameworks — just plain OOP, so the design decisions stay
front and center.

## How to run it

This project has no build tool config on purpose (keep it simple) — just compile
and run with the JDK directly:

```bash
cd parking-lot
find src -name "*.java" > sources.txt
mkdir -p out
javac -d out @sources.txt
java -cp out com.parkinglot.Main
```

You should see output like:

```
Parked CAR [PB-1234] -> ticket T1 at spot F1-M2
Fee for ticket T1: $20.0
[Display Board] Floor 1 is now FULL.
```

## Design overview

```
Vehicle ---parks-into---> ParkingSpot ---belongs-to---> ParkingFloor
                                                              |
                                                       ParkingLot (Singleton)
                                                        - holds all floors
                                                        - issues Tickets
                                                        - uses a PricingStrategy
                                                        - notifies Observers
```

### Patterns used, and why

- **Singleton — `ParkingLot`**
  There should only ever be one parking lot coordinating spot assignment.
  If two instances existed, two cars could be assigned the same spot at
  the same time. `getInstance()` guarantees a single shared instance.

- **Strategy — `PricingStrategy` / `HourlyPricingStrategy` / `FlatRatePricingStrategy`**
  Pricing rules change independently of everything else (promotions, vehicle-type
  rates, peak pricing). By coding `ParkingLot` against the `PricingStrategy`
  interface instead of a concrete class, you can swap in a new pricing model
  with zero changes to `ParkingLot`, `Ticket`, or anything else.

- **Factory — `ParkingSpotFactory`**
  Centralizes how a `ParkingSpot`'s ID is generated and how it's constructed.
  If spot creation ever needs to get more complex (e.g. reading configuration,
  assigning sensors), only the factory changes — callers are unaffected.

- **Observer — `ParkingLotObserver` / `DisplayBoardObserver`**
  `ParkingLot` doesn't need to know who cares about "floor full" or "spot freed"
  events. Any number of observers (a display board, a notification service, a
  logger) can subscribe without `ParkingLot` being changed.

### SOLID principles reflected here

- **Single Responsibility**: `Ticket` only tracks a parking session;
  `PricingStrategy` only calculates fees; `ParkingLot` only coordinates —
  none of them do each other's jobs.
- **Open/Closed**: new pricing models (`FlatRatePricingStrategy`) or new
  observers can be added without modifying `ParkingLot`.
- **Dependency Inversion**: `ParkingLot` depends on the `PricingStrategy` and
  `ParkingLotObserver` interfaces, not concrete implementations.

## Ideas to extend this in the remaining time

Pick 1–2 of these to make the project feel more complete before adding it to
your resume — you don't need all of them:

- [ ] Support **multiple floors** with a smarter search (nearest free spot).
- [ ] Add a **reservation** feature (reserve a spot ahead of time).
- [ ] Persist tickets to a file or an in-memory DB instead of a `HashMap`.
- [ ] Wrap it in a tiny **Spring Boot REST API** (`POST /park`, `POST /unpark/{ticketId}`)
      — this would also tie it back to your REST/Swagger experience.
- [ ] Add a `CompactSpotAllocationStrategy` vs `NearestFirstAllocationStrategy`
      as a second Strategy pattern, for *which* spot to assign, not just pricing.
- [ ] Write a few JUnit tests (`ParkingLotTest`) — you already have TDD experience,
      this would round the project out nicely and gives you another "used JUnit" bullet.

## Suggested resume bullet

> Designed and implemented a Parking Lot System in Java, applying Singleton,
> Strategy, Factory, and Observer design patterns and SOLID principles to
> support extensible vehicle types, pricing rules, and spot-availability
> notifications.
