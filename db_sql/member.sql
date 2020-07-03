/*
 Navicat Premium Data Transfer

 Source Server         : 161.117.11.150
 Source Server Type    : MySQL
 Source Server Version : 50729
 Source Host           : 161.117.11.150:3306
 Source Schema         : ghz

 Target Server Type    : MySQL
 Target Server Version : 50729
 File Encoding         : 65001

 Date: 03/07/2020 10:00:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member`  (
  `id` int(8) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT,
  `number_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of member
-- ----------------------------
INSERT INTO `member` VALUES (00000001, '1249585630741', 'quentina');
INSERT INTO `member` VALUES (00000002, '1022177221174', '愚蠢的毫哥');
INSERT INTO `member` VALUES (00000003, '1052631800355', '赤色秋');
INSERT INTO `member` VALUES (00000004, '1017674129995', '腥红之瞳');
INSERT INTO `member` VALUES (00000005, '1255680094775', 'gkd');
INSERT INTO `member` VALUES (00000006, '1415413368319', '浅梦丿轮回');
INSERT INTO `member` VALUES (00000007, '1023493803520', '伍兹，永远滴神');
INSERT INTO `member` VALUES (00000008, '1401556958706', '不知黑白');
INSERT INTO `member` VALUES (00000009, '1424841105964', '结云村的猫');
INSERT INTO `member` VALUES (00000010, '1143963205148', '吉良吉鹰');
INSERT INTO `member` VALUES (00000011, '1400679185980', '加把劲骑士');
INSERT INTO `member` VALUES (00000012, '1298746565135', '01檀黎斗。蓬莱A');
INSERT INTO `member` VALUES (00000013, '1199156805162', '咦妹子啊');
INSERT INTO `member` VALUES (00000014, '1105989240335', '阿斯特罗斯');
INSERT INTO `member` VALUES (00000015, '1119567864366', '陆大大爱周妹');
INSERT INTO `member` VALUES (00000016, '1348525309140', '一个来自阴间的ID');
INSERT INTO `member` VALUES (00000017, '1084628139524', '落叶红');
INSERT INTO `member` VALUES (00000018, '1050549061183', '天空坠落');
INSERT INTO `member` VALUES (00000019, '1234245165191', '浅念');
INSERT INTO `member` VALUES (00000020, '1369566840379', 'Last Order');
INSERT INTO `member` VALUES (00000021, '1090842181117', '相濡以沫');
INSERT INTO `member` VALUES (00000023, '1413626693157', '阿波罗');
INSERT INTO `member` VALUES (00000024, '1006878686718', '陌离');
INSERT INTO `member` VALUES (00000025, '1082853368384', '想脱非入欧');
INSERT INTO `member` VALUES (00000026, '1310225989127', '临洲');
INSERT INTO `member` VALUES (00000027, '1203438430704', '潆霜');
INSERT INTO `member` VALUES (00000028, '1027324331590', '西奈');
INSERT INTO `member` VALUES (00000029, '1052695313964', '泠樱');
INSERT INTO `member` VALUES (00000030, '1275656952327', 'MCLYF');

SET FOREIGN_KEY_CHECKS = 1;
