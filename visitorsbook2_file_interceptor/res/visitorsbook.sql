-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema visitorweb
-- -----------------------------------------------------

CREATE SCHEMA IF NOT EXISTS `visitorweb` DEFAULT CHARACTER SET utf8 ;
USE `visitorweb` ;

-- -----------------------------------------------------
-- Table `visitorweb`.`visitor`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `visitorweb`.`visitors` ;

CREATE TABLE IF NOT EXISTS `visitorweb`.`visitors` (
  `visitorid` VARCHAR(16) NOT NULL,
  `visitorname` VARCHAR(20) NOT NULL,
  `visitorpwd` VARCHAR(16) NOT NULL,
  `email` VARCHAR(50) NULL,
  `joindate` TIMESTAMP NULL DEFAULT current_timestamp,
  PRIMARY KEY (`visitorid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `visitorweb`.`visitorsbook`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `visitorweb`.`visitorsbook` ;

CREATE TABLE IF NOT EXISTS `visitorweb`.`visitorsbook` (
  `articleno` INT NOT NULL AUTO_INCREMENT,
  `visitorid` VARCHAR(16) NOT NULL,
  `subject` VARCHAR(100) NOT NULL,
  `content` VARCHAR(2000) NOT NULL,
  `regtime` TIMESTAMP NOT NULL DEFAULT current_timestamp,
  INDEX `visitorsbook_visitorname_FK_idx` (`visitorid` ASC) VISIBLE,
  PRIMARY KEY (`articleno`),
  CONSTRAINT `visitorsbook_visitorid_FK`
    FOREIGN KEY (`visitorid`)
    REFERENCES `visitorweb`.`visitors` (`visitorid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `visitorweb`.`file_info`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `visitorweb`.`file_info` ;

CREATE TABLE IF NOT EXISTS `visitorweb`.`file_info` (
  `articleno` INT NOT NULL,
  `savefolder` VARCHAR(45) NULL,
  `originfile` VARCHAR(50) NULL,
  `savefile` VARCHAR(50) NULL,
  INDEX `file_info_articleno_fk_idx` (`articleno` ASC) VISIBLE,
  CONSTRAINT `file_info_articleno_fk`
    FOREIGN KEY (`articleno`)
    REFERENCES `visitorweb`.`visitorsbook` (`articleno`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


INSERT INTO visitors (visitorid, visitorname, visitorpwd, email)
VALUES('admin', '관리자', 'admin', 'admin@admin.com');

INSERT INTO visitors (visitorid, visitorname, visitorpwd, email)
VALUES('visitor', '방문자', 'visitor', 'visitor@visitor.com');

commit;