/* 2019-02-12 */
-- 配置表
CREATE TABLE `al_config` (
 `id` BIGINT PRIMARY KEY COMMENT 'id',
 `code` VARCHAR(255) COMMENT '编码',
 `value` VARCHAR(255) COMMENT '值',
 `description` VARCHAR(255) COMMENT '描述',
 `createTime` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 `updateTime` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 组织表
CREATE TABLE `al_organization` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `isStar` tinyint(1) DEFAULT '0' COMMENT '是否星标',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/* 2019-02-17 */
-- 项目表
CREATE TABLE `al_project` (
  `id` bigint(20) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `indexNo` int DEFAULT 0 COMMENT '排序',
  `organization_id` bigint(20) DEFAULT NULL COMMENT '所属组织',
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 项目表与组织表关联
ALTER TABLE `al_project`
ADD CONSTRAINT `fk_organization_project` FOREIGN KEY (`organization_id`) REFERENCES `al_organization` (`id`);


/* 2019-03-03 */
-- 组织表添加名称唯一约束
ALTER TABLE `al_organization`
ADD UNIQUE INDEX `uni_idx_name` (`name`);

-- 项目表添加名称+组织唯一约束
ALTER TABLE `al_project`
ADD UNIQUE INDEX `uni_idx_name` (`name`, `organization_id`);


/* 2019-03-04 */
-- 数据字典表
CREATE TABLE `sys_dictionary` (
 `category` varchar(255) COMMENT '类型',
 `code` varchar(255) PRIMARY KEY COMMENT '编码',
 `name` varchar(255) COMMENT '名称',
 `indexNo` int COMMENT '索引'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 任务标签
CREATE TABLE `al_task_tag` (
 `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'id',
 `name` varchar(255) COMMENT '名称',
 `color` varchar(255) COMMENT '颜色',
 `createTime` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 `updateTime` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  UNIQUE INDEX `uni_idx_name` (`name`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 任务表
CREATE TABLE `al_task` (
 `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'id',
 `name` varchar(255) COMMENT '名称',
 `description` text COMMENT '描述',
 `level_code` varchar(255) COMMENT '级别',
 `status_code` varchar(255) COMMENT '状态',
 `taskTag_id` bigint COMMENT '所属标签', 
 `project_id` bigint COMMENT '所属项目',
 `planBeginDate` datetime COMMENT '计划开始日期',
 `planFinishDate` datetime COMMENT '计划结束日期',
 `beginDate` datetime COMMENT '开始日期',
 `finishDate` datetime COMMENT '结束日期',
 `createTime` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 `updateTime` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
 CONSTRAINT `fk_task_tag` FOREIGN KEY (`taskTag_id`) REFERENCES `al_task_tag` (`id`),
 CONSTRAINT `fk_task_project` FOREIGN KEY (`project_id`) REFERENCES `al_project` (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


/* 2019-03-08 */
-- 数据字典表新增任务级别
INSERT INTO `sys_dictionary` (`category`, `code`, `name`, `indexNo`)
VALUES 
 ('com.asing1elife.teamnote.model.dictionary.TaskLevel', 'TALE_Normal', '普通', '0'),
 ('com.asing1elife.teamnote.model.dictionary.TaskLevel', 'TALE_Urgency', '紧急', '1'),
 ('com.asing1elife.teamnote.model.dictionary.TaskLevel', 'TALE_Very', '非常紧急', '2');
 
-- 数据字典表新增任务状态
INSERT INTO `sys_dictionary` (`category`, `code`, `name`, `indexNo`)
VALUES 
 ('com.asing1elife.teamnote.model.dictionary.TaskStatus', 'TAST_Init', '初始化', '0'),
 ('com.asing1elife.teamnote.model.dictionary.TaskStatus', 'TAST_Impl', '进行中', '1'),
 ('com.asing1elife.teamnote.model.dictionary.TaskStatus', 'TAST_Finish', '已完成', '2');
 

/* 2019-03-24 */
-- 日志表
CREATE TABLE `al_daily` (
 `id` bigint PRIMARY KEY AUTO_INCREMENT COMMENT 'id',
 `year` int COMMENT '年份',
 `month` int COMMENT '月份',
 `isExpense` tinyint(1) COMMENT '是否报销',
 `expenseAmount` double COMMENT '报销金额',
 `totalDay` int COMMENT '总计天数',
 `createTime` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 `updateTime` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 日志记录表
CREATE TABLE `al_daily_record` (
 `id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'id',
 `daily_id` bigint COMMENT '所属日志',
 `day` int COMMENT '日期',
 `isExtra` tinyint(1) COMMENT '是否加班', 
 `createTime` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 `updateTime` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
 CONSTRAINT `fk_daily_record_daily` FOREIGN KEY (`daily_id`) REFERENCES `al_daily` (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 日志记录任务表
CREATE TABLE `al_daily_record_task` (
	`dailyRecord_id` bigint COMMENT '所属日志记录',
	`task_id` bigint COMMENT '所属任务',
	CONSTRAINT `fk_daily_record_task_daily_record` FOREIGN KEY (`dailyRecord_id`) REFERENCES `al_daily_record` (`id`),
	CONSTRAINT `fk_daily_record_task_task` FOREIGN KEY (`task_id`) REFERENCES `al_task` (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


/* 2019-04-02 */
-- 日志记录表新增是否还班字段
ALTER TABLE `al_daily_record`
ADD COLUMN `isRepay` tinyint(1) COMMENT '是否还班' AFTER `isExtra`;


/* 2019-04-27 */
-- 日志表新增所属组织字段
ALTER TABLE `al_daily`
ADD COLUMN `organization_id` BIGINT(20) COMMENT '所属组织' AFTER `id`;

-- 日志表与组织表增加外键关联
ALTER TABLE `al_daily`
ADD CONSTRAINT `fk_daily_organization` FOREIGN KEY (`organization_id`) REFERENCES `al_organization` (`id`);


/* 2019-06-03 */
-- 日志记录表新增是否休息字段
ALTER TABLE `al_daily_record`
ADD COLUMN `isRest` TINYINT(1) DEFAULT 0 COMMENT '是否休息' AFTER `isRepay`;