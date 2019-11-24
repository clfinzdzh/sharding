CREATE DATABASE `equip_db_0`; /*!40100 DEFAULT CHARACTER SET utf8mb4 */
CREATE TABLE `t_equip_0` (
  `equip_id` int(11) NOT NULL,
  `name` varchar(48) NOT NULL DEFAULT '' COMMENT '设备名称',
  `type` int(11) NOT NULL DEFAULT '0' COMMENT '设备类型',
  PRIMARY KEY (`equip_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

 CREATE TABLE `t_equip_1` (
  `equip_id` int(11) NOT NULL,
  `name` varchar(48) NOT NULL DEFAULT '' COMMENT '设备名称',
  `type` int(11) NOT NULL DEFAULT '0' COMMENT '设备类型',
  PRIMARY KEY (`equip_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;