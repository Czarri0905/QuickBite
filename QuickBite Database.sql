/*
SQLyog Community v13.1.5  (64 bit)
MySQL - 8.1.0 : Database - quickbite
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`quickbite` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `quickbite`;

/*Table structure for table `add_on` */

DROP TABLE IF EXISTS `add_on`;

CREATE TABLE `add_on` (
  `addon_id` int NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) NOT NULL,
  `extra_price` decimal(10,2) NOT NULL,
  PRIMARY KEY (`addon_id`),
  UNIQUE KEY `NAME` (`NAME`),
  CONSTRAINT `add_on_chk_1` CHECK ((`extra_price` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `add_on` */

insert  into `add_on`(`addon_id`,`NAME`,`extra_price`) values 
(1,'Extra rice',15.00),
(2,'Extra sauce',10.00),
(3,'Egg',20.00);

/*Table structure for table `cash_payment` */

DROP TABLE IF EXISTS `cash_payment`;

CREATE TABLE `cash_payment` (
  `payment_id` int NOT NULL,
  `amount_tendered` decimal(10,2) NOT NULL,
  `change_given` decimal(10,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`payment_id`),
  CONSTRAINT `cash_payment_ibfk_1` FOREIGN KEY (`payment_id`) REFERENCES `payment` (`payment_id`) ON DELETE CASCADE,
  CONSTRAINT `cash_payment_chk_1` CHECK ((`amount_tendered` > 0)),
  CONSTRAINT `cash_payment_chk_2` CHECK ((`change_given` >= 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `cash_payment` */

insert  into `cash_payment`(`payment_id`,`amount_tendered`,`change_given`) values 
(1,500.00,173.00),
(3,300.00,73.00);

/*Table structure for table `category` */

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `category_id` int NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) NOT NULL,
  PRIMARY KEY (`category_id`),
  UNIQUE KEY `NAME` (`NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `category` */

insert  into `category`(`category_id`,`NAME`) values 
(1,'Burgers'),
(6,'Desserts'),
(5,'Drinks'),
(2,'Meals'),
(3,'Pasta'),
(4,'Sides');

/*Table structure for table `dining_table` */

DROP TABLE IF EXISTS `dining_table`;

CREATE TABLE `dining_table` (
  `table_no` int NOT NULL,
  `capacity` int NOT NULL,
  `STATUS` varchar(10) NOT NULL DEFAULT 'AVAILABLE',
  `expected_free_time` datetime DEFAULT NULL,
  PRIMARY KEY (`table_no`),
  CONSTRAINT `dining_table_chk_1` CHECK ((`capacity` > 0)),
  CONSTRAINT `dining_table_chk_2` CHECK ((`STATUS` in (_utf8mb3'AVAILABLE',_utf8mb3'RESERVED',_utf8mb3'OCCUPIED')))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `dining_table` */

insert  into `dining_table`(`table_no`,`capacity`,`STATUS`,`expected_free_time`) values 
(1,2,'AVAILABLE',NULL),
(2,2,'AVAILABLE',NULL),
(3,4,'AVAILABLE',NULL),
(4,4,'OCCUPIED','2026-11-20 12:50:00'),
(5,6,'AVAILABLE',NULL),
(6,8,'AVAILABLE',NULL);

/*Table structure for table `ewallet_payment` */

DROP TABLE IF EXISTS `ewallet_payment`;

CREATE TABLE `ewallet_payment` (
  `payment_id` int NOT NULL,
  `provider` varchar(30) NOT NULL,
  `reference_no` varchar(20) NOT NULL,
  PRIMARY KEY (`payment_id`),
  CONSTRAINT `ewallet_payment_ibfk_1` FOREIGN KEY (`payment_id`) REFERENCES `payment` (`payment_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `ewallet_payment` */

insert  into `ewallet_payment`(`payment_id`,`provider`,`reference_no`) values 
(2,'GCash','GC8841205');

/*Table structure for table `menu_item` */

DROP TABLE IF EXISTS `menu_item`;

CREATE TABLE `menu_item` (
  `item_id` int NOT NULL AUTO_INCREMENT,
  `NAME` varchar(100) NOT NULL,
  `category_id` int NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `available` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`item_id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `menu_item_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`),
  CONSTRAINT `menu_item_chk_1` CHECK ((`price` > 0)),
  CONSTRAINT `menu_item_chk_2` CHECK ((`available` in (0,1)))
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `menu_item` */

insert  into `menu_item`(`item_id`,`NAME`,`category_id`,`price`,`available`) values 
(1,'Classic Burger',1,129.00,1),
(2,'Crispy Chicken Meal',2,159.00,1),
(3,'Spaghetti',3,139.00,1),
(4,'French Fries',4,59.00,1),
(5,'Iced Tea',5,39.00,1),
(6,'Chocolate Sundae',6,49.00,1);

/*Table structure for table `order_item` */

DROP TABLE IF EXISTS `order_item`;

CREATE TABLE `order_item` (
  `order_item_id` int NOT NULL AUTO_INCREMENT,
  `order_no` int NOT NULL,
  `item_id` int NOT NULL,
  `quantity` int NOT NULL,
  `size` varchar(20) DEFAULT NULL,
  `remarks` varchar(200) DEFAULT NULL,
  `unit_price` decimal(10,2) NOT NULL,
  PRIMARY KEY (`order_item_id`),
  KEY `item_id` (`item_id`),
  KEY `idx_order_item_order` (`order_no`),
  CONSTRAINT `order_item_ibfk_1` FOREIGN KEY (`order_no`) REFERENCES `orders` (`order_no`) ON DELETE CASCADE,
  CONSTRAINT `order_item_ibfk_2` FOREIGN KEY (`item_id`) REFERENCES `menu_item` (`item_id`),
  CONSTRAINT `order_item_chk_1` CHECK ((`quantity` > 0)),
  CONSTRAINT `order_item_chk_2` CHECK ((`unit_price` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `order_item` */

insert  into `order_item`(`order_item_id`,`order_no`,`item_id`,`quantity`,`size`,`remarks`,`unit_price`) values 
(1,1001,1,2,'Regular','No pickles',129.00),
(2,1001,5,1,'Large',NULL,39.00),
(3,1002,2,1,'Regular',NULL,159.00),
(4,1002,4,2,'Regular',NULL,59.00),
(5,1002,6,1,'Regular',NULL,49.00),
(6,1003,3,1,'Regular',NULL,139.00),
(7,1003,5,2,'Regular',NULL,39.00),
(8,1004,1,1,'Regular',NULL,129.00);

/*Table structure for table `order_item_addon` */

DROP TABLE IF EXISTS `order_item_addon`;

CREATE TABLE `order_item_addon` (
  `order_item_id` int NOT NULL,
  `addon_id` int NOT NULL,
  `extra_price` decimal(10,2) NOT NULL,
  PRIMARY KEY (`order_item_id`,`addon_id`),
  KEY `addon_id` (`addon_id`),
  CONSTRAINT `order_item_addon_ibfk_1` FOREIGN KEY (`order_item_id`) REFERENCES `order_item` (`order_item_id`) ON DELETE CASCADE,
  CONSTRAINT `order_item_addon_ibfk_2` FOREIGN KEY (`addon_id`) REFERENCES `add_on` (`addon_id`),
  CONSTRAINT `order_item_addon_chk_1` CHECK ((`extra_price` >= 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `order_item_addon` */

insert  into `order_item_addon`(`order_item_id`,`addon_id`,`extra_price`) values 
(1,1,15.00),
(3,3,20.00),
(6,2,10.00);

/*Table structure for table `orders` */

DROP TABLE IF EXISTS `orders`;

CREATE TABLE `orders` (
  `order_no` int NOT NULL AUTO_INCREMENT,
  `order_type` varchar(10) NOT NULL,
  `STATUS` varchar(10) NOT NULL DEFAULT 'PENDING',
  `table_no` int DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`order_no`),
  KEY `table_no` (`table_no`),
  KEY `idx_orders_status` (`STATUS`,`created_at`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`table_no`) REFERENCES `dining_table` (`table_no`),
  CONSTRAINT `orders_chk_1` CHECK ((`order_type` in (_utf8mb4'Dine In',_utf8mb4'Take Out'))),
  CONSTRAINT `orders_chk_2` CHECK ((`STATUS` in (_utf8mb4'PENDING',_utf8mb4'PREPARING',_utf8mb4'READY',_utf8mb4'COMPLETED'))),
  CONSTRAINT `orders_chk_3` CHECK (((`order_type` = _utf8mb4'Dine In') or (`table_no` is null)))
) ENGINE=InnoDB AUTO_INCREMENT=1005 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `orders` */

insert  into `orders`(`order_no`,`order_type`,`STATUS`,`table_no`,`created_at`) values 
(1001,'Dine In','COMPLETED',2,'2026-11-20 11:30:00'),
(1002,'Take Out','READY',NULL,'2026-11-20 12:05:00'),
(1003,'Dine In','PENDING',4,'2026-11-20 12:10:00'),
(1004,'Take Out','PENDING',NULL,'2026-11-20 12:20:00');

/*Table structure for table `payment` */

DROP TABLE IF EXISTS `payment`;

CREATE TABLE `payment` (
  `payment_id` int NOT NULL AUTO_INCREMENT,
  `order_no` int NOT NULL,
  `payment_type` varchar(10) NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `paid_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`payment_id`),
  UNIQUE KEY `order_no` (`order_no`),
  CONSTRAINT `payment_ibfk_1` FOREIGN KEY (`order_no`) REFERENCES `orders` (`order_no`),
  CONSTRAINT `payment_chk_1` CHECK ((`payment_type` in (_utf8mb3'CASH',_utf8mb3'EWALLET'))),
  CONSTRAINT `payment_chk_2` CHECK ((`amount` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `payment` */

insert  into `payment`(`payment_id`,`order_no`,`payment_type`,`amount`,`paid_at`) values 
(1,1001,'CASH',327.00,'2026-11-20 11:32:00'),
(2,1002,'EWALLET',346.00,'2026-11-20 12:06:00'),
(3,1003,'CASH',227.00,'2026-11-20 12:11:00');

/*Table structure for table `transaction_record` */

DROP TABLE IF EXISTS `transaction_record`;

CREATE TABLE `transaction_record` (
  `transaction_no` varchar(20) NOT NULL,
  `order_no` int NOT NULL,
  `date_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `total` decimal(10,2) NOT NULL,
  PRIMARY KEY (`transaction_no`),
  UNIQUE KEY `order_no` (`order_no`),
  CONSTRAINT `transaction_record_ibfk_1` FOREIGN KEY (`order_no`) REFERENCES `orders` (`order_no`),
  CONSTRAINT `transaction_record_chk_1` CHECK ((`total` >= 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `transaction_record` */

insert  into `transaction_record`(`transaction_no`,`order_no`,`date_time`,`total`) values 
('TXN1001',1001,'2026-11-20 11:32:00',327.00),
('TXN1002',1002,'2026-11-20 12:06:00',346.00),
('TXN1003',1003,'2026-11-20 12:11:00',227.00);

/* Trigger structure for table `cash_payment` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `trg_cash_payment_type` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `trg_cash_payment_type` BEFORE INSERT ON `cash_payment` FOR EACH ROW BEGIN
    IF (SELECT payment_type FROM PAYMENT WHERE payment_id = NEW.payment_id) <> 'CASH' THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'This payment is not a CASH payment';
    END IF;
END */$$


DELIMITER ;

/* Trigger structure for table `ewallet_payment` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `trg_ewallet_payment_type` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `trg_ewallet_payment_type` BEFORE INSERT ON `ewallet_payment` FOR EACH ROW BEGIN
    IF (SELECT payment_type FROM PAYMENT WHERE payment_id = NEW.payment_id) <> 'EWALLET' THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'This payment is not an EWALLET payment';
    END IF;
END */$$


DELIMITER ;

/*Table structure for table `v_best_sellers` */

DROP TABLE IF EXISTS `v_best_sellers`;

/*!50001 DROP VIEW IF EXISTS `v_best_sellers` */;
/*!50001 DROP TABLE IF EXISTS `v_best_sellers` */;

/*!50001 CREATE TABLE  `v_best_sellers`(
 `item_name` varchar(100) ,
 `units_sold` decimal(32,0) 
)*/;

/*Table structure for table `v_daily_sales` */

DROP TABLE IF EXISTS `v_daily_sales`;

/*!50001 DROP VIEW IF EXISTS `v_daily_sales` */;
/*!50001 DROP TABLE IF EXISTS `v_daily_sales` */;

/*!50001 CREATE TABLE  `v_daily_sales`(
 `sale_date` date ,
 `transactions` bigint ,
 `total_sales` decimal(32,2) 
)*/;

/*Table structure for table `v_kitchen_queue` */

DROP TABLE IF EXISTS `v_kitchen_queue`;

/*!50001 DROP VIEW IF EXISTS `v_kitchen_queue` */;
/*!50001 DROP TABLE IF EXISTS `v_kitchen_queue` */;

/*!50001 CREATE TABLE  `v_kitchen_queue`(
 `order_no` int ,
 `order_type` varchar(10) ,
 `table_no` int ,
 `status` varchar(10) ,
 `created_at` datetime ,
 `total_items` decimal(32,0) 
)*/;

/*Table structure for table `v_order_lines` */

DROP TABLE IF EXISTS `v_order_lines`;

/*!50001 DROP VIEW IF EXISTS `v_order_lines` */;
/*!50001 DROP TABLE IF EXISTS `v_order_lines` */;

/*!50001 CREATE TABLE  `v_order_lines`(
 `order_no` int ,
 `order_item_id` int ,
 `item_name` varchar(100) ,
 `size` varchar(20) ,
 `quantity` int ,
 `unit_price` decimal(10,2) ,
 `addons_price` decimal(32,2) ,
 `line_total` decimal(43,2) 
)*/;

/*Table structure for table `v_order_totals` */

DROP TABLE IF EXISTS `v_order_totals`;

/*!50001 DROP VIEW IF EXISTS `v_order_totals` */;
/*!50001 DROP TABLE IF EXISTS `v_order_totals` */;

/*!50001 CREATE TABLE  `v_order_totals`(
 `order_no` int ,
 `total` decimal(65,2) 
)*/;

/*View structure for view v_best_sellers */

/*!50001 DROP TABLE IF EXISTS `v_best_sellers` */;
/*!50001 DROP VIEW IF EXISTS `v_best_sellers` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_best_sellers` AS select `mi`.`NAME` AS `item_name`,sum(`oi`.`quantity`) AS `units_sold` from ((`order_item` `oi` join `menu_item` `mi` on((`mi`.`item_id` = `oi`.`item_id`))) join `payment` `p` on((`p`.`order_no` = `oi`.`order_no`))) group by `mi`.`item_id`,`mi`.`NAME` order by `units_sold` desc */;

/*View structure for view v_daily_sales */

/*!50001 DROP TABLE IF EXISTS `v_daily_sales` */;
/*!50001 DROP VIEW IF EXISTS `v_daily_sales` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_daily_sales` AS select cast(`transaction_record`.`date_time` as date) AS `sale_date`,count(0) AS `transactions`,sum(`transaction_record`.`total`) AS `total_sales` from `transaction_record` group by cast(`transaction_record`.`date_time` as date) */;

/*View structure for view v_kitchen_queue */

/*!50001 DROP TABLE IF EXISTS `v_kitchen_queue` */;
/*!50001 DROP VIEW IF EXISTS `v_kitchen_queue` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_kitchen_queue` AS select `o`.`order_no` AS `order_no`,`o`.`order_type` AS `order_type`,`o`.`table_no` AS `table_no`,`o`.`STATUS` AS `status`,`o`.`created_at` AS `created_at`,(select sum(`order_item`.`quantity`) from `order_item` where (`order_item`.`order_no` = `o`.`order_no`)) AS `total_items` from `orders` `o` where ((`o`.`STATUS` in ('PENDING','PREPARING')) and exists(select 1 from `payment` `p` where (`p`.`order_no` = `o`.`order_no`))) order by `o`.`created_at` */;

/*View structure for view v_order_lines */

/*!50001 DROP TABLE IF EXISTS `v_order_lines` */;
/*!50001 DROP VIEW IF EXISTS `v_order_lines` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_order_lines` AS select `oi`.`order_no` AS `order_no`,`oi`.`order_item_id` AS `order_item_id`,`mi`.`NAME` AS `item_name`,`oi`.`size` AS `size`,`oi`.`quantity` AS `quantity`,`oi`.`unit_price` AS `unit_price`,coalesce((select sum(`a`.`extra_price`) from `order_item_addon` `a` where (`a`.`order_item_id` = `oi`.`order_item_id`)),0) AS `addons_price`,(`oi`.`quantity` * (`oi`.`unit_price` + coalesce((select sum(`a`.`extra_price`) from `order_item_addon` `a` where (`a`.`order_item_id` = `oi`.`order_item_id`)),0))) AS `line_total` from (`order_item` `oi` join `menu_item` `mi` on((`mi`.`item_id` = `oi`.`item_id`))) */;

/*View structure for view v_order_totals */

/*!50001 DROP TABLE IF EXISTS `v_order_totals` */;
/*!50001 DROP VIEW IF EXISTS `v_order_totals` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_order_totals` AS select `v_order_lines`.`order_no` AS `order_no`,sum(`v_order_lines`.`line_total`) AS `total` from `v_order_lines` group by `v_order_lines`.`order_no` */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
