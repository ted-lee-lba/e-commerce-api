CREATE SCHEMA `tmp_test` DEFAULT CHARACTER SET utf8mb4;

CREATE TABLE `accounts` (
  `account_id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(320) NOT NULL,
  `password` VARCHAR(60) NOT NULL,
  `account_type` VARCHAR(20) NOT NULL COMMENT 'ADMIN,USER,MERCHANT',
  `is_email_verified` TINYINT(4) NOT NULL,
  `status` VARCHAR(20) NOT NULL DEFAULT 'ACTIVE' COMMENT 'ACTIVE,SUSPENDED'
  `created_date` DATETIME NOT NULL,
  `created_by` VARCHAR(320) NOT NULL,
  `updated_date` DATETIME NOT NULL,
  `updated_by` VARCHAR(320) NOT NULL,
  PRIMARY KEY (`account_id`));

  CREATE TABLE `products` (
  `product_id` INT NOT NULL AUTO_INCREMENT,
  `account_id` INT NOT NULL,
  `product_code` VARCHAR(20) NOT NULL,
  `product_name` VARCHAR(255) NOT NULL,
  `product_descr` TEXT NOT NULL,
  `price` DECIMAL(19,2) NOT NULL DEFAULT 0,
  `created_date` DATETIME NOT NULL,
  `created_by` VARCHAR(320) NOT NULL,
  `updated_date` DATETIME NOT NULL,
  `updated_by` VARCHAR(320) NOT NULL,
  PRIMARY KEY (`product_id`),
  UNIQUE INDEX `product_code_UNIQUE` (`product_code` ASC),
  INDEX `FK_accounts_idx` (`account_id` ASC),
  CONSTRAINT `FK_accounts`
    FOREIGN KEY (`account_id`)
    REFERENCES `accounts` (`account_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `orders` (
  `order_id` INT NOT NULL AUTO_INCREMENT,
  `buyer_id` INT NOT NULL,
  `merchant_id` INT NOT NULL,
  `order_time` DATETIME NOT NULL,
  `order_status` VARCHAR(45) NOT NULL,
  `created_date` DATETIME NOT NULL,
  `created_by` VARCHAR(320) NOT NULL,
  `updated_date` DATETIME NOT NULL,
  `updated_by` VARCHAR(320) NOT NULL,
  PRIMARY KEY (`order_id`),
  INDEX `FK_accounts_1_idx` (`buyer_id` ASC),
  INDEX `FK_accounts_2_idx` (`merchant_id` ASC),
  CONSTRAINT `FK_accounts_1`
    FOREIGN KEY (`buyer_id`)
    REFERENCES `accounts` (`account_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_accounts_2`
    FOREIGN KEY (`merchant_id`)
    REFERENCES `accounts` (`account_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `order_products` (
  `order_product_id` INT NOT NULL AUTO_INCREMENT,
  `order_id` INT NOT NULL,
  `product_code` VARCHAR(20) NOT NULL,
  `product_name` VARCHAR(255) NOT NULL,
  `price` DECIMAL(19,2) NOT NULL,
  `quantity` INT(10) NOT NULL DEFAULT 0
  PRIMARY KEY (`order_product_id`),
  INDEX `FK_orders_idx` (`order_id` ASC),
  CONSTRAINT `FK_orders`
    FOREIGN KEY (`order_id`)
    REFERENCES `orders` (`order_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


INSERT INTO `accounts` (`username`, `password`, `account_type`, `is_email_verified`, `created_date`, `created_by`, `updated_date`, `updated_by`, `status`) 
VALUES ('admin', '$2a$10$2UlEoPiNE8HFSLii4zmFHeW7AzDTI.I0wXIKNIzRObPGkYQc22ehy', 'ROLE_ADMIN', 1, NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'ACTIVE');
