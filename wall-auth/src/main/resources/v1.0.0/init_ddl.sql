CREATE TABLE IF NOT EXISTS `action` (
                          `id` bigint unsigned NOT NULL AUTO_INCREMENT,
                          `name` varchar(255) NOT NULL,
                          `description` text,
                          `type` tinyint NOT NULL,
                          `status` tinyint NOT NULL,
                          `domain_id` bigint NOT NULL,
                          `creator` bigint NOT NULL,
                          `mender` bigint NOT NULL,
                          `create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
                          `modify_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
                          PRIMARY KEY (`id`) USING BTREE,
                          UNIQUE KEY `unique_name_domain` (`name`,`domain_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `casbin_policy` (
                                 `id` bigint NOT NULL AUTO_INCREMENT,
                                 `ptype` varchar(10) NOT NULL,
                                 `v0` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                                 `v1` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                                 `v2` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                                 `v3` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                                 `v4` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                                 `v5` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `object` (
                          `id` bigint unsigned NOT NULL AUTO_INCREMENT,
                          `name` varchar(255) NOT NULL,
                          `description` text,
                          `type` tinyint NOT NULL,
                          `status` tinyint NOT NULL,
                          `parent_id` bigint DEFAULT NULL,
                          `domain_id` bigint NOT NULL,
                          `creator` bigint NOT NULL,
                          `mender` bigint NOT NULL,
                          `create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
                          `modify_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
                          PRIMARY KEY (`id`) USING BTREE,
                          UNIQUE KEY `unique_name_domain` (`name`,`domain_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `permission` (
                              `id` bigint unsigned NOT NULL AUTO_INCREMENT,
                              `description` text,
                              `type` tinyint NOT NULL,
                              `status` tinyint NOT NULL,
                              `policy` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                              `external_id` bigint NOT NULL,
                              `domain_id` bigint NOT NULL,
                              `expire_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
                              `creator` bigint NOT NULL,
                              `mender` bigint NOT NULL,
                              `create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
                              `modify_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `role` (
                        `id` bigint unsigned NOT NULL AUTO_INCREMENT,
                        `name` varchar(255) NOT NULL,
                        `description` text,
                        `type` tinyint NOT NULL,
                        `status` tinyint NOT NULL,
                        `parent_id` bigint DEFAULT NULL,
                        `domain_id` bigint NOT NULL,
                        `creator` bigint NOT NULL,
                        `mender` bigint NOT NULL,
                        `create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
                        `modify_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
                        PRIMARY KEY (`id`) USING BTREE,
                        UNIQUE KEY `unique_name_domain` (`name`,`domain_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `role_relation` (
                                 `id` bigint unsigned NOT NULL AUTO_INCREMENT,
                                 `role_id` bigint NOT NULL,
                                 `subject_id` varchar(255) NOT NULL,
                                 `type` tinyint NOT NULL,
                                 `status` tinyint NOT NULL,
                                 `expire_time` datetime(3) NOT NULL,
                                 `creator` bigint NOT NULL,
                                 `mender` bigint NOT NULL,
                                 `create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
                                 `modify_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
                                 PRIMARY KEY (`id`) USING BTREE,
                                 UNIQUE KEY `unique_role_subject` (`role_id`,`subject_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;