CREATE DATABASE `equip_db_0`; /*!40100 DEFAULT CHARACTER SET utf8mb4 */
CREATE TABLE `t_equip_parts_0` (
  `equip_parts_id` int(11) NOT NULL,
  `equip_id` int(11) NOT NULL,
  `name` varchar(48) NOT NULL DEFAULT '',
  `type` int(11) NOT NULL,
  PRIMARY KEY (`equip_parts_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `t_equip_parts_1` (
  `equip_parts_id` int(11) NOT NULL,
  `equip_id` int(11) NOT NULL,
  `name` varchar(48) NOT NULL DEFAULT '',
  `type` int(11) NOT NULL,
  PRIMARY KEY (`equip_parts_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4

