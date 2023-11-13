-- MySQL Script generated by MySQL Workbench
-- Fri Nov  3 14:36:23 2023
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema materip
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `materip` ;

-- -----------------------------------------------------
-- Schema materip
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `materip` DEFAULT CHARACTER SET utf8 ;
USE `materip` ;

-- -----------------------------------------------------
-- Table `materip`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `materip`.`users` ;

CREATE TABLE IF NOT EXISTS `materip`.`users` (
  `id` VARCHAR(20) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `nickname` VARCHAR(45) NOT NULL,
    `tel` VARCHAR(30) NOT NULL,
  `birth` DATE NULL,
  `gender` TINYINT NOT NULL,
  `admin` TINYINT NULL DEFAULT 0,
  `join_date` TIMESTAMP NULL DEFAULT now(),
  `modified_at` TIMESTAMP NULL DEFAULT now(),
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `materip`.`travel_spots`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `materip`.`travel_spots` ;

CREATE TABLE IF NOT EXISTS `materip`.`travel_spots` (
  `id` INT NOT NULL,
  `mapX` FLOAT NULL,
  `mapY` FLOAT NULL,
  `name` VARCHAR(45) NULL,
  `description` VARCHAR(45) NULL,
  `area_code` INT NULL,
  `sigungu_code` INT NULL,
  `content_type_id` INT NULL,
  `address` VARCHAR(500) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `materip`.`board`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `materip`.`board` ;

CREATE TABLE IF NOT EXISTS `materip`.`board` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NULL,
  `contents` VARCHAR(5000) NULL,
  `created_at` TIMESTAMP NULL,
  `modified_at` TIMESTAMP NULL,
  `author` VARCHAR(20) NULL,
  `hits` INT NULL DEFAULT 0,
  `board_type` ENUM('review', 'recruitment', 'notice') NOT NULL,
  `travel_spot` INT NULL,
  `travel_date` TIMESTAMP NULL,
  PRIMARY KEY (`id`),
  INDEX `users_to_article_idx` (`author` ASC) VISIBLE,
  INDEX `users_to_travel_spots_idx` (`travel_spot` ASC) VISIBLE)
ENGINE = InnoDB
COMMENT = '        ';


-- -----------------------------------------------------
-- Table `materip`.`comments`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `materip`.`comments` ;

CREATE TABLE IF NOT EXISTS `materip`.`comments` (
  `sequence` INT NOT NULL AUTO_INCREMENT,
  `article_id` INT NULL,
  `author` VARCHAR(45) NULL,
  `contents` VARCHAR(300) NOT NULL,
  `created_at` TIMESTAMP NULL,
  `modified_at` TIMESTAMP NULL,
  PRIMARY KEY (`sequence`),
  INDEX `users_comments_idx` (`author` ASC) VISIBLE,
  INDEX `articles_commnets_idx` (`article_id` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `materip`.`userlikes`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `materip`.`userlikes` ;

CREATE TABLE IF NOT EXISTS `materip`.`userlikes` (
  `sequence` INT NOT NULL AUTO_INCREMENT,
  `id` VARCHAR(20) NULL,
  `liked_by` VARCHAR(20) NULL,
  PRIMARY KEY (`sequence`),
  INDEX `userlikes_idx` (`liked_by` ASC, `id` ASC) VISIBLE)
ENGINE = InnoDB
COMMENT = '    ';


-- -----------------------------------------------------
-- Table `materip`.`files`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `materip`.`files` ;

CREATE TABLE IF NOT EXISTS `materip`.`files` (
  `board_id` INT NULL,
  `save_folder` VARCHAR(45) NULL,
  `origin_file` VARCHAR(45) NULL,
  `save_file` VARCHAR(45) NULL,
  INDEX `board_id_mapping_idx` (`board_id` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `materip`.`participants`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `materip`.`participants` ;

CREATE TABLE IF NOT EXISTS `materip`.`participants` (
  `board_id` INT NOT NULL,
  `user_id` VARCHAR(45) NULL,
  INDEX `board_id_mapping_idx` (`board_id` ASC) VISIBLE,
  INDEX `user_id_idx` (`user_id` ASC) VISIBLE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
