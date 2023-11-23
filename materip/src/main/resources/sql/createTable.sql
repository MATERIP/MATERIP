-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema materip
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema materip
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `materip` DEFAULT CHARACTER SET utf8 ;
USE `materip` ;

-- -----------------------------------------------------
-- Table `materip`.`attraction_description`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `materip`.`attraction_description` ;

CREATE TABLE IF NOT EXISTS `materip`.`attraction_description` (
  `content_id` INT NOT NULL,
  `homepage` VARCHAR(100) NULL DEFAULT NULL,
  `overview` VARCHAR(10000) NULL DEFAULT NULL,
  `telname` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`content_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `materip`.`attraction_detail`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `materip`.`attraction_detail` ;

CREATE TABLE IF NOT EXISTS `materip`.`attraction_detail` (
  `content_id` INT NOT NULL,
  `cat1` VARCHAR(3) NULL DEFAULT NULL,
  `cat2` VARCHAR(5) NULL DEFAULT NULL,
  `cat3` VARCHAR(9) NULL DEFAULT NULL,
  `created_time` VARCHAR(14) NULL DEFAULT NULL,
  `modified_time` VARCHAR(14) NULL DEFAULT NULL,
  `booktour` VARCHAR(5) NULL DEFAULT NULL,
  PRIMARY KEY (`content_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `materip`.`attraction_info`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `materip`.`attraction_info` ;

CREATE TABLE IF NOT EXISTS `materip`.`attraction_info` (
  `content_id` INT NOT NULL,
  `content_type_id` INT NULL DEFAULT NULL,
  `title` VARCHAR(100) NULL DEFAULT NULL,
  `addr1` VARCHAR(100) NULL DEFAULT NULL,
  `addr2` VARCHAR(50) NULL DEFAULT NULL,
  `zipcode` VARCHAR(50) NULL DEFAULT NULL,
  `tel` VARCHAR(50) NULL DEFAULT NULL,
  `first_image` VARCHAR(200) NULL DEFAULT NULL,
  `first_image2` VARCHAR(200) NULL DEFAULT NULL,
  `readcount` INT NULL DEFAULT NULL,
  `sido_code` INT NULL DEFAULT NULL,
  `gugun_code` INT NULL DEFAULT NULL,
  `latitude` DECIMAL(20,17) NULL DEFAULT NULL,
  `longitude` DECIMAL(20,17) NULL DEFAULT NULL,
  `mlevel` VARCHAR(2) NULL DEFAULT NULL,
  PRIMARY KEY (`content_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


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
  `birth` DATE NULL DEFAULT NULL,
  `tel` VARCHAR(30) NOT NULL,
  `gender` TINYINT NOT NULL,
  `admin` TINYINT NULL DEFAULT '0',
  `join_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_at` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `materip`.`board`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `materip`.`board` ;

CREATE TABLE IF NOT EXISTS `materip`.`board` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NULL DEFAULT NULL,
  `contents` VARCHAR(5000) NULL DEFAULT NULL,
  `created_at` TIMESTAMP NULL DEFAULT NULL,
  `modified_at` TIMESTAMP NULL DEFAULT NULL,
  `author` VARCHAR(20) NULL DEFAULT NULL,
  `hits` INT NULL DEFAULT '0',
  `board_type` ENUM('review', 'recruitment', 'notice') NOT NULL,
  `travel_spot` INT NULL DEFAULT NULL,
  `travel_date` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `users_to_article_idx` (`author` ASC) VISIBLE,
  INDEX `board_to_attraction_idx` (`travel_spot` ASC) VISIBLE,
  CONSTRAINT `users_to_article`
    FOREIGN KEY (`author`)
    REFERENCES `materip`.`users` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `board_to_attraction`
    FOREIGN KEY (`travel_spot`)
    REFERENCES `materip`.`attraction_info` (`content_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8
COMMENT = '        ';


-- -----------------------------------------------------
-- Table `materip`.`comments`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `materip`.`comments` ;

CREATE TABLE IF NOT EXISTS `materip`.`comments` (
  `sequence` INT NOT NULL AUTO_INCREMENT,
  `article_id` INT NULL DEFAULT NULL,
  `author` VARCHAR(45) NULL DEFAULT NULL,
  `contents` VARCHAR(300) NULL DEFAULT NULL,
  `created_at` TIMESTAMP NULL DEFAULT NULL,
  `modified_at` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`sequence`),
  INDEX `users_comments_idx` (`author` ASC) VISIBLE,
  INDEX `articles_commnets_idx` (`article_id` ASC) VISIBLE,
  CONSTRAINT `articles_commnets`
    FOREIGN KEY (`article_id`)
    REFERENCES `materip`.`board` (`id`),
  CONSTRAINT `users_comments`
    FOREIGN KEY (`author`)
    REFERENCES `materip`.`users` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `materip`.`files`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `materip`.`files` ;

CREATE TABLE IF NOT EXISTS `materip`.`files` (
  `board_id` INT NULL DEFAULT NULL,
  `save_folder` VARCHAR(45) NULL DEFAULT NULL,
  `origin_file` VARCHAR(45) NULL DEFAULT NULL,
  `save_file` VARCHAR(45) NULL DEFAULT NULL,
  INDEX `board_id_mapping_idx` (`board_id` ASC) VISIBLE,
  CONSTRAINT `board_id_to_files`
    FOREIGN KEY (`board_id`)
    REFERENCES `materip`.`board` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `materip`.`participants`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `materip`.`participants` ;

CREATE TABLE IF NOT EXISTS `materip`.`participants` (
  `board_id` INT NOT NULL,
  `user_id` VARCHAR(45) NULL DEFAULT NULL,
  INDEX `board_id_mapping_idx` (`board_id` ASC) VISIBLE,
  INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `board_id_mapping`
    FOREIGN KEY (`board_id`)
    REFERENCES `materip`.`board` (`id`),
  CONSTRAINT `user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `materip`.`users` (`id`)
    ON DELETE SET NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `materip`.`userlikes`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `materip`.`userlikes` ;

CREATE TABLE IF NOT EXISTS `materip`.`userlikes` (
  `sequence` INT NOT NULL AUTO_INCREMENT,
  `id` VARCHAR(20) NULL DEFAULT NULL,
  `liked_by` VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (`sequence`),
  INDEX `user_mapping_idx` (`id` ASC) VISIBLE,
  CONSTRAINT `user_mapping`
    FOREIGN KEY (`id`)
    REFERENCES `materip`.`users` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '    ';


-- -----------------------------------------------------
-- Table `materip`.`refresh-token`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `materip`.`refresh-token` ;

CREATE TABLE IF NOT EXISTS `materip`.`refresh-token` (
  `user_id` VARCHAR(50) NOT NULL,
  `token` VARCHAR(500) NOT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `token_to_users`
    FOREIGN KEY (`user_id`)
    REFERENCES `materip`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

alter table `refresh-token` drop foreign key `token_to_users`;

alter table `refresh-token` add constraint foreign key (user_id) references users (id) on delete cascade;

alter table `participants`
add unique (board_id, user_id);
alter table `participants`
add column `id` int auto_increment primary key;


alter table `board`
add column `max_count` int default 0;

alter table `board`
modify column `travel_date` datetime; 

alter table `board`
add column `current_count` int default 0;

alter table `participants`
add column `status` int default -1;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
