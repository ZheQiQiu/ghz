/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : localhost:3306
 Source Schema         : ghz

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 29/05/2020 16:18:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for boss
-- ----------------------------
DROP TABLE IF EXISTS `boss`;
CREATE TABLE `boss`  (
  `id` int(8) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT,
  `current_hp` int(4) NOT NULL,
  `max_hp` int(4) NOT NULL,
  `list_id` int(8) UNSIGNED ZEROFILL NOT NULL,
  `ordered` int(4) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `list_id`(`list_id`) USING BTREE,
  CONSTRAINT `boss_ibfk_1` FOREIGN KEY (`list_id`) REFERENCES `boss_list` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for boss_list
-- ----------------------------
DROP TABLE IF EXISTS `boss_list`;
CREATE TABLE `boss_list`  (
  `id` int(8) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT,
  `round` int(4) NOT NULL,
  `current_num` int(4) NOT NULL,
  `total_num` int(4) NOT NULL,
  `start_date` date NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ghz_data
-- ----------------------------
DROP TABLE IF EXISTS `ghz_data`;
CREATE TABLE `ghz_data`  (
  `id` int(8) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT,
  `current_boss_list` int(8) UNSIGNED ZEROFILL NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `current_boss_list`(`current_boss_list`) USING BTREE,
  CONSTRAINT `ghz_data_ibfk_1` FOREIGN KEY (`current_boss_list`) REFERENCES `boss_list` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ghz_user
-- ----------------------------
DROP TABLE IF EXISTS `ghz_user`;
CREATE TABLE `ghz_user`  (
  `id` int(8) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password_sha512` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `nick_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for knife_data
-- ----------------------------
DROP TABLE IF EXISTS `knife_data`;
CREATE TABLE `knife_data`  (
  `id` int(8) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT,
  `member_id` int(8) UNSIGNED ZEROFILL NOT NULL,
  `damage` int(8) NOT NULL,
  `boss_id` int(8) UNSIGNED ZEROFILL NOT NULL,
  `round` int(5) NOT NULL,
  `date` date NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `member_id`(`member_id`) USING BTREE,
  INDEX `boss_id`(`boss_id`) USING BTREE,
  CONSTRAINT `knife_data_ibfk_1` FOREIGN KEY (`member_id`) REFERENCES `member` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `knife_data_ibfk_2` FOREIGN KEY (`boss_id`) REFERENCES `boss` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 60 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member`  (
  `id` int(8) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT,
  `number_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `nick_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(8) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_re_role
-- ----------------------------
DROP TABLE IF EXISTS `user_re_role`;
CREATE TABLE `user_re_role`  (
  `user_id` int(8) UNSIGNED ZEROFILL NOT NULL,
  `role_id` int(8) UNSIGNED ZEROFILL NOT NULL,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `role_id`(`role_id`) USING BTREE,
  CONSTRAINT `user_re_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `ghz_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_re_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
