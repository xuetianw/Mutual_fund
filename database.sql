CREATE DATABASE  IF NOT EXISTS `wallet_db`;
USE `wallet_db`;

DROP TABLE IF EXISTS `transaction`;
DROP TABLE IF EXISTS `wallet`;
DROP TABLE IF EXISTS `customer`;

CREATE TABLE `customer` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `fname` VARCHAR(64) NOT NULL,
  `lname` VARCHAR(64) NOT NULL,
  `pwd` VARCHAR(64) NOT NULL,
  `email` VARCHAR(64) NOT NULL,
  `verification_status` VARCHAR(64) NOT NULL DEFAULT 'NOT_VERIFIED',
  `birth_date` DATE NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `wallet` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `customer_id` INT NOT NULL,
  `balance` DECIMAL(10,2) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
);

CREATE TABLE `transaction` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `type_id` INT NOT NULL,
  `description` VARCHAR(64) NOT NULL,
  `wallet_id` INT NOT NULL,
  `customer_id` INT NOT NULL,
  `amount` DECIMAL(10,2) NOT NULL,
  `date` DATE NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`wallet_id`) REFERENCES `wallet` (`id`),
  FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
);