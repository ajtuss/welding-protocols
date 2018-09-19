INSERT INTO `brands` VALUES (1,'2018-09-10 10:23:12','2018-09-10 10:23:12',1,'Kemppi'),
(2,'2018-09-10 10:41:22','2018-09-10 10:41:22',1,'EWM'),
(3,'2018-09-10 10:41:57','2018-09-10 10:41:57',1,'OZAS'),
(4,'2018-09-10 10:42:34','2018-09-10 11:04:25',1,'Merkle');

INSERT INTO `ranges` VALUES (8,250,10,30,20.5),(9,300,5,22,10),(10,350,10,34,20.5),(11,400,5,26,10);

INSERT INTO `models` VALUES
(2,'2018-09-14 11:47:21','2018-09-17 12:10:59',2, false , false , true, 'MasterTig 3000', false , true , false ,2,NULL,8,9),
(3,'2018-09-14 11:47:31','2018-09-17 12:13:47',2, false , false , true,'MasterTig 4000', false , true , false ,2,NULL,10,11);

INSERT INTO `customers` VALUES (1,'2018-09-17 12:20:17','2018-09-17 12:20:17',1,'Katowice',NULL,'Firma sp. z O.O.',NULL,'Firma','Kościuszki 18','40-584');

INSERT INTO `machines` (`id`,`creation_date`,`modification_date`,`version_id`,`inw_number`,`serial_number`,`customer_id`,`model_id`)
VALUES (1,'2018-09-17 13:52:50','2018-09-17 13:52:50',1,NULL,'1234567',1,2);

INSERT INTO `validations` (`id`,`creation_date`,`modification_date`,`version_id`,`date_validation`,`finalized`,`next_validation`,`protocol_number`,`result`,`type`,`machine_id`,`protocol_id`)
VALUES (1,'2018-09-18 13:27:26','2018-09-18 13:27:26',1,NULL,FALSE ,NULL,NULL,NULL,'MMA',1,NULL),
(2,'2018-09-18 13:27:26','2018-09-18 13:27:26',1,NULL,FALSE ,NULL,NULL,NULL,'TIG',1,NULL);

INSERT INTO `measures` (`id`,`creation_date`,`modification_date`,`version_id`,`i_adjust`,`i_power`,`i_valid`,`u_adjust`,`u_power`,`u_valid`,`validprotocol_id`) VALUES
(2,'2018-09-19 09:24:58','2018-09-19 09:24:58',1,79,NULL,NULL,13.2,NULL,NULL,1),
(1,'2018-09-19 09:24:58','2018-09-19 09:24:58',1,5,NULL,NULL,10.2,NULL,NULL,1),
(4,'2018-09-19 09:24:58','2018-09-19 09:24:58',1,226,NULL,NULL,19.1,NULL,NULL,1),
(3,'2018-09-19 09:24:58','2018-09-19 09:24:58',1,153,NULL,NULL,16.1,NULL,NULL,1),
(5,'2018-09-19 09:24:58','2018-09-19 09:24:58',1,300,NULL,NULL,22.0,NULL,NULL,1);