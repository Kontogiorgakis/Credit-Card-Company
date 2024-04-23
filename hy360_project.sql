-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Εξυπηρετητής: 127.0.0.1
-- Χρόνος δημιουργίας: 25 Ιαν 2022 στις 16:25:07
-- Έκδοση διακομιστή: 10.4.21-MariaDB
-- Έκδοση PHP: 7.3.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Βάση δεδομένων: `hy360_project`
--

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `civilians`
--

CREATE TABLE `civilians` (
  `civilian_id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  `account_number` varchar(20) NOT NULL,
  `exp_date` varchar(20) NOT NULL,
  `credit_limit` int(11) DEFAULT NULL,
  `amount_due` double DEFAULT NULL,
  `available_balance` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Άδειασμα δεδομένων του πίνακα `civilians`
--

INSERT INTO `civilians` (`civilian_id`, `name`, `account_number`, `exp_date`, `credit_limit`, `amount_due`, `available_balance`) VALUES
(1, 'Giorgos', '1111111111111111', '2022-10-10', 1000, 700, 3000),
(2, 'Manos', '1111111111111112', '2023-10-10', 1000, 700, 3000),
(3, 'Mpampis', '1111111111111113', '2024-10-10', 1000, 700, 3000),
(4, 'Giannis', '1111111111111114', '2024-11-11', 1000, 700, 3000),
(5, 'Kostas', '1111111111111115', '2023-1-1', 1000, 700, 3000);

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `companies`
--

CREATE TABLE `companies` (
  `company_id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  `account_number` varchar(20) NOT NULL,
  `exp_date` varchar(20) NOT NULL,
  `credit_limit` int(11) DEFAULT NULL,
  `amount_due` double DEFAULT NULL,
  `available_balance` double DEFAULT NULL,
  `employee_name` varchar(40) NOT NULL,
  `employee_identity` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Άδειασμα δεδομένων του πίνακα `companies`
--

INSERT INTO `companies` (`company_id`, `name`, `account_number`, `exp_date`, `credit_limit`, `amount_due`, `available_balance`, `employee_name`, `employee_identity`) VALUES
(1, 'Microsoft', '1211111111111111', '2022-10-1', 10000, 7000, 30000, 'null', 'null'),
(2, 'Tesla', '1311111111111112', '2023-10-1', 10000, 7000, 30000, 'null', 'null'),
(3, 'Apple', '1411111111111113', '2024-10-1', 10000, 7000, 30000, 'null', 'null'),
(4, 'Amazon', '1511111111111114', '2024-11-1', 10000, 7000, 30000, 'null', 'null'),
(5, 'Samsung', '1611111111111115', '2023-1-1', 10000, 7000, 30000, 'null', 'null');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `employees`
--

CREATE TABLE `employees` (
  `employee_id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  `account_number` varchar(20) NOT NULL,
  `exp_date` varchar(20) NOT NULL,
  `credit_limit` int(11) DEFAULT NULL,
  `amount_due` double DEFAULT NULL,
  `available_balance` double DEFAULT NULL,
  `company` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Άδειασμα δεδομένων του πίνακα `employees`
--

INSERT INTO `employees` (`employee_id`, `name`, `account_number`, `exp_date`, `credit_limit`, `amount_due`, `available_balance`, `company`) VALUES
(1, 'Maria', '1211111111111222', '2022-10-1', 1000, 700, 3000, 'Microsoft'),
(2, 'Georgia', '1211111111111322', '2022-11-1', 1000, 700, 3000, 'Tesla'),
(3, 'Grigoris', '1211111111111422', '2022-6-1', 997, 700.3, 3000, 'Apple'),
(4, 'Mixalis', '1211111111111522', '2022-8-1', 1000, 700, 3000, 'Amazon'),
(5, 'Panos', '1211111111111622', '2022-3-1', 1000, 700, 3000, 'Samsung'),
(6, 'miso', '5423512341234123', '2016-1-1', 600, 740, 3000, 'Apple');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `merchants`
--

CREATE TABLE `merchants` (
  `merchant_id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  `account_number` varchar(20) NOT NULL,
  `supply` double DEFAULT NULL,
  `total_profit` double DEFAULT NULL,
  `amount_due` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Άδειασμα δεδομένων του πίνακα `merchants`
--

INSERT INTO `merchants` (`merchant_id`, `name`, `account_number`, `supply`, `total_profit`, `amount_due`) VALUES
(1, 'Ioanna', '1234111111111119', 200, 0, 500),
(2, 'Thanasis', '1234511111111119', 200, 0, 500),
(3, 'Katerina', '1234611111111119', 200, 0, 500),
(4, 'Nikos', '1234711111111119', 200, 0, 500),
(5, 'Sotiris', '1234811111111119', 200, 403, 475);

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `transactions`
--

CREATE TABLE `transactions` (
  `transaction_id` int(11) NOT NULL,
  `product_name` varchar(40) NOT NULL,
  `customer_name` varchar(30) NOT NULL,
  `customer_property` varchar(30) NOT NULL,
  `merchant_name` varchar(30) NOT NULL,
  `transaction_date` varchar(20) NOT NULL,
  `transaction_amount` double NOT NULL,
  `transaction_type` varchar(20) NOT NULL,
  `returned` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Άδειασμα δεδομένων του πίνακα `transactions`
--

INSERT INTO `transactions` (`transaction_id`, `product_name`, `customer_name`, `customer_property`, `merchant_name`, `transaction_date`, `transaction_amount`, `transaction_type`, `returned`) VALUES
(1, 'default', 'miso', 'employee', 'Sotiris', '2022-01-26', 100, 'credit', 0),
(2, 'kafe', 'Grigoris', 'employee', 'Sotiris', '2022-01-05', 3, 'credit', 0),
(3, 'oplo', 'miso', 'employee', 'Sotiris', '2022-01-12', 300, 'credit', 0);

--
-- Ευρετήρια για άχρηστους πίνακες
--

--
-- Ευρετήρια για πίνακα `civilians`
--
ALTER TABLE `civilians`
  ADD PRIMARY KEY (`civilian_id`),
  ADD UNIQUE KEY `account_number` (`account_number`);

--
-- Ευρετήρια για πίνακα `companies`
--
ALTER TABLE `companies`
  ADD PRIMARY KEY (`company_id`),
  ADD UNIQUE KEY `account_number` (`account_number`);

--
-- Ευρετήρια για πίνακα `employees`
--
ALTER TABLE `employees`
  ADD PRIMARY KEY (`employee_id`);

--
-- Ευρετήρια για πίνακα `merchants`
--
ALTER TABLE `merchants`
  ADD PRIMARY KEY (`merchant_id`),
  ADD UNIQUE KEY `account_number` (`account_number`);

--
-- Ευρετήρια για πίνακα `transactions`
--
ALTER TABLE `transactions`
  ADD PRIMARY KEY (`transaction_id`);

--
-- AUTO_INCREMENT για άχρηστους πίνακες
--

--
-- AUTO_INCREMENT για πίνακα `civilians`
--
ALTER TABLE `civilians`
  MODIFY `civilian_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT για πίνακα `companies`
--
ALTER TABLE `companies`
  MODIFY `company_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT για πίνακα `employees`
--
ALTER TABLE `employees`
  MODIFY `employee_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT για πίνακα `merchants`
--
ALTER TABLE `merchants`
  MODIFY `merchant_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT για πίνακα `transactions`
--
ALTER TABLE `transactions`
  MODIFY `transaction_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
