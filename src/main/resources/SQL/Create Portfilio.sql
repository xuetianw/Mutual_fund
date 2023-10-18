CREATE DATABASE IF NOT EXISTS mutual_fund;

DROP TABLE IF EXISTS `portfolio`;


CREATE TABLE `portfolio` (
  `portfolio_id` INT NOT NULL AUTO_INCREMENT,
  `customer_id` int,
  `mf_name` VARCHAR(64),
  `mf_fund_house` VARCHAR(64) NOT NULL,
  `mf_units_available` INT DEFAULT 0,
  `currency` VARCHAR(64) NOT NULL,
  `transaction_date` DATE,
  PRIMARY KEY (`portfolio_id`),
  FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`)
);




INSERT INTO `portfolio`(`portfolio_id`,`customer_id`,`mf_name`,`mf_fund_house`,`mf_units_available`,`currency`,`transaction_date`)

VALUES

('1', '1', 'A growth-oriented mutual fund', "mf_fund_house", "1", 2.34, '1222-01-02')
