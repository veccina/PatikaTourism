-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Anamakine: localhost
-- Üretim Zamanı: 03 Şub 2024, 22:22:43
-- Sunucu sürümü: 8.0.31
-- PHP Sürümü: 7.4.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Veritabanı: `patikatourism`
--

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `facility`
--

CREATE TABLE `facility` (
  `facility_id` int NOT NULL,
  `hotel_id` int NOT NULL,
  `type` enum('Free Parking','Free Wifi','Swimming Pool','Fitness Center','Hotel Concierge','Spa','Room Service') COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `facility`
--

INSERT INTO `facility` (`facility_id`, `hotel_id`, `type`) VALUES
(1, 1, 'Free Parking'),
(2, 2, 'Free Wifi'),
(3, 3, 'Swimming Pool'),
(4, 4, 'Fitness Center'),
(5, 5, 'Hotel Concierge'),
(20, 8, 'Spa'),
(21, 8, 'Swimming Pool'),
(22, 8, 'Hotel Concierge'),
(23, 8, 'Fitness Center'),
(24, 8, 'Room Service'),
(25, 8, 'Free Wifi'),
(26, 8, 'Free Parking');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `hotel`
--

CREATE TABLE `hotel` (
  `hotel_id` int NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `city` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `region` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `address` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `telephone` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `star` varchar(255) COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `hotel`
--

INSERT INTO `hotel` (`hotel_id`, `name`, `city`, `region`, `address`, `email`, `telephone`, `star`) VALUES
(1, 'Grand Palace Hotel', 'Istanbul', 'Sultanahmet', '1 Sultan Street', 'grandpalace@example.com', '+9012345678', '5'),
(2, 'Alpine Retreat', 'Switzerland', 'Zermatt', '23 Alpine Rd', 'alpineretreat@example.com', '+4156789345', '2'),
(3, 'Sunny Beach Resort', 'Florida', 'Miami Beach', '456 Ocean View Drive', 'sunnybeach@example.com', '+7861234567', '4'),
(4, 'Eiffel Tower Suites', 'Paris', 'Champs-√Člys√©es', '7 Eiffel Tower Blvd', 'eiffeltower@example.com', '+3312345678', '3'),
(5, 'Wilderness Lodge', 'Canada', 'Jasper', '345 Pine Forest Road', 'wildernesslodge@example.com', '+7809876543', '4'),
(8, 'Antalya Hotel', 'Antalya', 'Türkiye', 'Antalya merkez', 'antalya@antalya.com', '02422222222', '5');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `lodging`
--

CREATE TABLE `lodging` (
  `lodging_id` int NOT NULL,
  `hotel_id` int NOT NULL,
  `type` enum('Ultra Everything','Everything','Room Breakfast','Full Lodging','Half Lodging','Only Bed','Full Credit') COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `lodging`
--

INSERT INTO `lodging` (`lodging_id`, `hotel_id`, `type`) VALUES
(1, 1, 'Ultra Everything'),
(2, 2, 'Everything'),
(3, 3, 'Room Breakfast'),
(4, 4, 'Full Lodging'),
(5, 5, 'Only Bed'),
(20, 8, 'Everything'),
(21, 8, 'Only Bed'),
(22, 8, 'Full Credit'),
(23, 8, 'Full Lodging'),
(24, 8, 'Half Lodging'),
(25, 8, 'Ultra Everything'),
(26, 8, 'Room Breakfast');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `period`
--

CREATE TABLE `period` (
  `period_id` int NOT NULL,
  `hotel_id` int NOT NULL,
  `winter_start` date NOT NULL,
  `winter_end` date NOT NULL,
  `summer_start` date NOT NULL,
  `summer_end` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `period`
--

INSERT INTO `period` (`period_id`, `hotel_id`, `winter_start`, `winter_end`, `summer_start`, `summer_end`) VALUES
(1, 1, '2024-12-01', '2025-02-28', '2024-06-01', '2024-08-31'),
(2, 2, '2024-12-01', '2025-03-31', '2024-07-01', '2024-09-30'),
(3, 3, '2025-01-01', '2025-03-31', '2024-07-01', '2024-09-30'),
(4, 4, '2025-01-01', '2025-04-30', '2024-06-01', '2024-08-31'),
(5, 5, '2024-12-01', '2025-03-31', '2024-06-01', '2024-08-31'),
(7, 8, '2024-02-04', '2024-12-30', '2024-06-01', '2024-12-30');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `price`
--

CREATE TABLE `price` (
  `price_id` int NOT NULL,
  `lodging_id` int NOT NULL,
  `room_id` int NOT NULL,
  `winter_adult_price` double NOT NULL,
  `winter_child_price` double NOT NULL,
  `summer_adult_price` double NOT NULL,
  `summer_child_price` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `price`
--

INSERT INTO `price` (`price_id`, `lodging_id`, `room_id`, `winter_adult_price`, `winter_child_price`, `summer_adult_price`, `summer_child_price`) VALUES
(1, 1, 1, 350, 90, 480, 130),
(2, 2, 2, 550, 130, 720, 180),
(3, 3, 3, 750, 180, 950, 220),
(4, 4, 4, 280, 70, 380, 90),
(5, 5, 5, 480, 120, 580, 140),
(15, 20, 9, 100, 100, 1010, 1010),
(16, 21, 9, 1010, 100, 1010, 1010),
(17, 22, 9, 100, 1010, 1010, 1010),
(18, 23, 9, 100, 100, 1010, 1010),
(19, 24, 9, 1010, 1010, 1010, 1010),
(20, 25, 9, 1010, 1010, 1010, 1010),
(21, 26, 9, 1010, 1010, 1010, 1010);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `reservation`
--

CREATE TABLE `reservation` (
  `reservation_id` int NOT NULL,
  `room_id` int NOT NULL,
  `contact_name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `contact_telephone` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `contact_email` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `note` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `adult_information` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `child_information` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `arrival` date NOT NULL,
  `departure` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `reservation`
--

INSERT INTO `reservation` (`reservation_id`, `room_id`, `contact_name`, `contact_telephone`, `contact_email`, `note`, `adult_information`, `child_information`, `arrival`, `departure`) VALUES
(1, 1, 'John Doe', '+1234567890', 'john@example.com', 'Special requests: None', 'Adult 1', 'Child 1', '2024-06-15', '2024-06-22'),
(2, 2, 'Alice Smith', '+9876543210', 'alice@example.com', 'Special requests: Vegetarian meals', 'Adult 1, Adult 2', 'Child 1', '2024-07-10', '2024-07-17'),
(3, 3, 'David Brown', '+1122334455', 'david@example.com', 'Special requests: Late check-in', 'Adult 1, Adult 2, Adult 3', 'Child 1', '2024-08-05', '2024-08-12'),
(4, 4, 'Eva White', '+3366998877', 'eva@example.com', 'Special requests: Airport transfer', 'Adult 1', 'Child 1', '2024-06-20', '2024-06-27'),
(5, 5, 'Michael Johnson', '+1555666777', 'michael@example.com', 'Special requests: Spa package', 'Adult 1, Adult 2', 'Child 1', '2024-06-30', '2024-07-07'),
(7, 9, 'Deneme', '5252525252', 'deneme@deneme.com', 'deneme', '\n', 'Yeti?kin 1\n\nAd?: deneme\n\nÜlkesi: deneme\n\nKimlik No: 123456789\n\n\n', '2024-06-15', '2024-06-20');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `room`
--

CREATE TABLE `room` (
  `room_id` int NOT NULL,
  `hotel_id` int NOT NULL,
  `period_id` int NOT NULL,
  `name` enum('Standard','Deluxe','Suite','Single','Double') COLLATE utf8mb4_general_ci NOT NULL,
  `number_of_beds` int NOT NULL,
  `item` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `square_meter` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `stock` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `room`
--

INSERT INTO `room` (`room_id`, `hotel_id`, `period_id`, `name`, `number_of_beds`, `item`, `square_meter`, `stock`) VALUES
(1, 1, 1, 'Standard', 2, 'City View', '28 sqm', 8),
(2, 2, 2, 'Deluxe', 3, 'Mountain View', '45 sqm', 14),
(3, 3, 3, 'Suite', 4, 'Beachfront', '70 sqm', 10),
(4, 4, 4, 'Single', 1, 'City View', '25 sqm', 12),
(5, 5, 5, 'Double', 2, 'Forest View', '35 sqm', 18),
(6, 3, 3, 'Double', 2, 'TV, Minibar, Console, Safe, Projector,', '35', 5),
(7, 1, 1, 'Single', 2, 'TV, Minibar, Console, Safe, Projector,', '25', 25),
(9, 8, 7, 'Double', 25, 'TV, Minibar, Console, Safe, Projector,', '25', 24);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `user`
--

CREATE TABLE `user` (
  `id` int NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `pass` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `role` enum('admin','agent') COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `user`
--

INSERT INTO `user` (`id`, `name`, `email`, `pass`, `role`) VALUES
(1, 'Admin', 'admin@admin.com', 'admin', 'admin'),
(2, 'John Smith', 'john@example.com', 'johnpassword', 'agent'),
(3, 'Alice Johnson', 'alice@example.com', 'alicepassword', 'agent'),
(4, 'David Brown', 'david@example.com', 'davidpassword', 'agent'),
(5, 'Eva White', 'eva@example.com', 'evapassword', 'agent'),
(9, 'deneme', 'deneme', 'deneme', 'agent');

--
-- Dökümü yapılmış tablolar için indeksler
--

--
-- Tablo için indeksler `facility`
--
ALTER TABLE `facility`
  ADD PRIMARY KEY (`facility_id`);

--
-- Tablo için indeksler `hotel`
--
ALTER TABLE `hotel`
  ADD PRIMARY KEY (`hotel_id`);

--
-- Tablo için indeksler `lodging`
--
ALTER TABLE `lodging`
  ADD PRIMARY KEY (`lodging_id`);

--
-- Tablo için indeksler `period`
--
ALTER TABLE `period`
  ADD PRIMARY KEY (`period_id`);

--
-- Tablo için indeksler `price`
--
ALTER TABLE `price`
  ADD PRIMARY KEY (`price_id`);

--
-- Tablo için indeksler `reservation`
--
ALTER TABLE `reservation`
  ADD PRIMARY KEY (`reservation_id`);

--
-- Tablo için indeksler `room`
--
ALTER TABLE `room`
  ADD PRIMARY KEY (`room_id`);

--
-- Tablo için indeksler `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- Dökümü yapılmış tablolar için AUTO_INCREMENT değeri
--

--
-- Tablo için AUTO_INCREMENT değeri `facility`
--
ALTER TABLE `facility`
  MODIFY `facility_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- Tablo için AUTO_INCREMENT değeri `hotel`
--
ALTER TABLE `hotel`
  MODIFY `hotel_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Tablo için AUTO_INCREMENT değeri `lodging`
--
ALTER TABLE `lodging`
  MODIFY `lodging_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- Tablo için AUTO_INCREMENT değeri `period`
--
ALTER TABLE `period`
  MODIFY `period_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Tablo için AUTO_INCREMENT değeri `price`
--
ALTER TABLE `price`
  MODIFY `price_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- Tablo için AUTO_INCREMENT değeri `reservation`
--
ALTER TABLE `reservation`
  MODIFY `reservation_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Tablo için AUTO_INCREMENT değeri `room`
--
ALTER TABLE `room`
  MODIFY `room_id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Tablo için AUTO_INCREMENT değeri `user`
--
ALTER TABLE `user`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
