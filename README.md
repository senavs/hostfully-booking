# Hostfully Booking

This is a property reservation API made using Java 17 and Spring Boot 3.2.2.

## Terminology

**Booking:** is when a guest selects a start and end date and submits a reservation on a
property.

**Block:** is when the property owner or manager selects a range of days during which no
guest can make a booking (e.g. the owner wants to use the property for themselves, or the
property manager needs to schedule the repainting of a few rooms).

## Requirements

1. Create a **booking** and **block**.
2. Cancel a **booking** and **block**.
3. Rebook a canceled **booking** and **block**.
4. Delete a **booking** and **block** from the system.
5. Get **booking** and **block** information.

## Database Architecture

![architecture design](assets%2Fhostfully-booking-architecture.jpg)

## Setup

You can configure this project by cloning the current repository and running Maven

```shell
git clone https://github.com/senavs/hostfully-booking.git

cd booking/
./mvnw spring-boot:run
```

## Tests

Integration tests were configure covering the following scenarios:

**Person**
1. Register a new person into the system.

**Property**
1. List all property that a person owns.
2. Register a new property into the system and link it to its owner.

**Reservation**
1. List all reservations for a property.
2. List all reservations that are not marked as deleted.
3. Book a property.
4. Book a property, but there is another reservation in the same date range.
5. Book a property, but it is blocked by the property owner.
6. Soft delete a reservation (mark it as deleted).
7. Hard delete a reservation (remove it from database).
8. Rebook a property using a reservation previously marked as deleted.
9. Rebook a property using a reservation previously marked as deleted, but there is another reservation in the same date range.

## Collection

You can download Postman Collection [here](assets%2FHostfully.postman_collection.json).

## Improvements
- Implement **user authentication** for update or register property and its reservations.
- Implement **security** request validations in param to avoid **SQL Injection**.
- Add **pagination** to `findAll` queries using `PagingAndSortingRepository` Spring interface.
- Configure **Docker Image** and **Docker Compose** for straight forward local/production setup and container **orchestration**, **scalability** and **availability**.
- Configure **server-side cache** for most requested endpoints, using Redis as example.
- Configure database replicas or a data warehouse to not overload the unique instance of the database. 