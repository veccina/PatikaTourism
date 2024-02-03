
# Patika Tourism Project

This project is a hotel reservation and facility management system for Patika Tourism. The project is written in Java.

## Structure

The project is structured into several packages, each containing classes related to a specific aspect of the system.

### Dao Classes Overview


### FacilityDao
- **Purpose**: Manages facilities available in hotels, such as pools, gyms, and restaurants.
- **Main Functions**: Lists all facilities, retrieves facilities by hotel ID, and searches facilities by type.

### HotelDao
- **Purpose**: Handles hotel information, including hotel details and contact information.
- **Main Functions**: Lists all hotels, adds new hotels, updates hotel information, and searches hotels based on various criteria like name and location.

### PeriodDao
- **Purpose**: Manages booking periods, delineating seasons or specific booking time frames.
- **Main Functions**: Retrieves all periods, adds and deletes periods, and searches for periods by hotel ID.

### PriceDao
- **Purpose**: Deals with pricing information related to rooms and lodgings during different seasons or periods.
- **Main Functions**: Lists prices, retrieves prices by room or lodging ID, and searches prices based on stay and room IDs.

### ReservationDao
- **Purpose**: Manages reservations made by users for rooms within hotels.
- **Main Functions**: Retrieves all reservations, adds and deletes reservations, and searches reservations by various identifiers like room ID.

### RoomDao
- **Purpose**: Handles room details within hotels, including room types, capacities, and amenities.
- **Main Functions**: Lists all rooms, retrieves rooms by hotel ID or availability dates, and searches rooms by name or ID.

### StayDao
- **Purpose**: Manages information related to stays, which could be linked to long-term bookings or special packages.
- **Main Functions**: Lists stays, adds, deletes, and updates stay information, and searches stays by hotel ID and type.

### UserDao
- **Purpose**: Manages user accounts, including guests, hotel staff, and administrators.
- **Main Functions**: Lists all users, adds new users, and searches users by name, email, or role.

## Database Connection
All DAO classes utilize `Db.getInstance()` for database connection, ensuring a single shared connection instance is used across the system. This approach optimizes resource usage and maintains consistency in data access patterns.

## Error Handling
The DAO classes are designed to handle SQL exceptions gracefully, ensuring the system's stability and providing meaningful error messages for troubleshooting.

## Usage
To utilize a DAO class, instantiate it and call the desired method. For example, to list all hotels:

```java
HotelDao hotelDao = new HotelDao();
ArrayList<Hotel> hotels = hotelDao.getList();
```

For adding a new entity, such as a hotel, create an instance of the entity, set its properties, and call the add method:

```java
Hotel newHotel = new Hotel();
// Set hotel properties
newHotel.setName("Hotel Name");
newHotel.setCity("City");
// Add hotel
hotelDao.add(newHotel);
```

## Conclusion
The DAO layer is crucial for separating the system's business logic from the data access logic, providing a clear and maintainable codebase. Each DAO class is dedicated to a specific entity, ensuring modularity and ease of maintenance.


### Entity Classes Overview
### Facility
- **Description**: Represents facilities within hotels like pools, gyms, and restaurants.
- **Attributes**: `facilityID`, `hotelID`, `type`.

### Hotel
- **Description**: Manages details about hotels in the system.
- **Attributes**: `hotelID`, `name`, `city`, `region`, `address`, `email`, `telephone`, `star`.

### Period
- **Description**: Defines booking periods or seasons with specific start and end dates.
- **Attributes**: `periodID`, `hotelID`, `winterStart`, `winterEnd`, `summerStart`, `summerEnd`.

### Price
- **Description**: Contains pricing information for rooms and lodgings across different seasons.
- **Attributes**: `priceID`, `lodgingID`, `roomID`, `winterAdultPrice`, `winterChildPrice`, `summerAdultPrice`, `summerChildPrice`.

### Reservation
- **Description**: Manages reservations made by users, including booking details and contact information.
- **Attributes**: `reservationID`, `roomID`, `contactName`, `contactTelephone`, `contactEmail`, `note`, `adultInformation`, `childInformation`, `arrival`, `departure`.

### Room
- **Description**: Details about rooms within hotels, including type, capacity, and amenities.
- **Attributes**: `roomID`, `hotelID`, `lodgingID`, `periodID`, `name`, `numberOfBeds`, `item`, `squareMeter`, `stock`.

### Stay
- **Description**: Represents stays or lodgings, potentially for special packages or extended durations.
- **Attributes**: `lodgingID`, `hotelID`, `type`.

### User
- **Description**: Manages user accounts within the system, including roles and access rights.
- **Attributes**: `id`, `name`, `email`, `pass`, `role`.

## Conclusion
The entity classes form the backbone of the Hotel Management System, encapsulating the essential data and relationships within the system. They provide a structured approach to managing hotel operations, from booking management to user interactions.


### Core Classes Overview

The `core` package contains utility classes that provide various functionalities used throughout the project.

- `CMBItem`: This class is used for managing comb items within the system.
- `Db`: This class is responsible for managing the connection to the database.
- `Helper`: This class provides various utility methods used throughout the project.


### Business Classes Overview

This document provides an overview of the classes within the Hotel Management System, detailing their purposes, key functionalities, and interactions with other components.

#### `HotelManager`

- **Purpose**: Manages hotel-related operations including CRUD operations for hotels.
- **Key Functionalities**: Adding, updating, deleting hotels, and managing associated entities like facilities, rooms, and stays to ensure data integrity.

#### `PeriodManager`

- **Purpose**: Handles period-related operations, focusing on managing seasonal periods affecting pricing and availability.
- **Key Functionalities**: Adding, updating, deleting periods, and retrieving period lists.

#### `PriceManager`

- **Purpose**: Manages pricing information for rooms and stays.
- **Key Functionalities**: Adding, updating, deleting prices, and providing methods to retrieve price lists associated with rooms or stays.

#### `ReservationManager`

- **Purpose**: Manages reservations, including room bookings and availability.
- **Key Functionalities**: Adding, deleting reservations, updating room stocks, and retrieving reservation lists.

#### `RoomManager`

- **Purpose**: Handles room-related functionalities, including room information and availability.
- **Key Functionalities**: Adding, updating, deleting rooms, and ensuring no duplicate room types for a hotel.

#### `StayManager`

- **Purpose**: Manages stays, representing customer accommodations.
- **Key Functionalities**: Adding, updating, deleting stays, and managing associated prices.

#### `UserManager`

- **Purpose**: Manages user accounts, roles, and authentication within the system.
- **Key Functionalities**: Adding, updating, deleting users, and providing user search functionalities based on various criteria.

#### `FacilityManager`

- **Purpose**: Manages hotel facilities, including amenities, services, or physical features.
- **Key Functionalities**: Adding, updating, deleting facilities, and providing facility search and retrieval functionalities.

Each manager class interacts with a corresponding DAO (Data Access Object) to perform database operations, ensuring data integrity and consistency across the system. The system is designed to handle complex operations related to hotel management, including room bookings, pricing strategies, facility management, and user authentication, providing a comprehensive solution for managing hotel operations effectively.