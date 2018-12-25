/*
Navicat MySQL Data Transfer

Source Server         : 192.168.0.249
Source Server Version : 50162
Source Host           : 192.168.0.249:3306
Source Database       : file

Target Server Type    : MYSQL
Target Server Version : 50162
File Encoding         : 65001

Date: 2018-12-24 11:29:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_china
-- ----------------------------
DROP TABLE IF EXISTS `t_china`;
CREATE TABLE `t_china` (
  `id` int(10) NOT NULL COMMENT '主键ID',
  `name` varchar(32) DEFAULT NULL COMMENT '名称',
  `parent_id` int(10) DEFAULT NULL COMMENT '父节点ID',
  `short_name` varchar(32) DEFAULT NULL COMMENT '简称',
  `level_type` int(2) DEFAULT NULL COMMENT '缩进',
  `city_code` varchar(10) DEFAULT NULL COMMENT '城市代码',
  `zip_code` varchar(10) DEFAULT NULL COMMENT '邮政编码',
  `manager_name` varchar(32) DEFAULT NULL COMMENT '管辖区',
  `lng` float(10,0) DEFAULT NULL COMMENT '经度',
  `lat` float(10,0) DEFAULT NULL COMMENT '维度',
  `pinyin` varchar(32) DEFAULT NULL COMMENT '拼音',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
