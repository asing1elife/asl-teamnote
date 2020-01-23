/* 2019-02-12 */
-- 配置表
CREATE TABLE al_config (
  id BIGINT PRIMARY KEY COMMENT 'id',
  code VARCHAR(255) COMMENT '编码',
  value VARCHAR(255) COMMENT '值',
  description VARCHAR(255) COMMENT '描述',
  createTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updateTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

-- 组织表
CREATE TABLE al_organization (
  id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  name VARCHAR(255) DEFAULT NULL COMMENT '名称',
  description VARCHAR(500) DEFAULT NULL COMMENT '描述',
  isStar TINYINT(1) DEFAULT '0' COMMENT '是否星标',
  createTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updateTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;


/* 2019-02-17 */
-- 项目表
CREATE TABLE al_project (
  id BIGINT(20) NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'id',
  name VARCHAR(255) DEFAULT NULL COMMENT '名称',
  description VARCHAR(500) DEFAULT NULL COMMENT '描述',
  indexNo INT DEFAULT 0 COMMENT '排序',
  organization_id BIGINT(20) DEFAULT NULL COMMENT '所属组织',
  createTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updateTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

-- 项目表与组织表关联
ALTER TABLE al_project
  ADD CONSTRAINT fk_organization_project FOREIGN KEY (organization_id) REFERENCES al_organization (id);


/* 2019-03-03 */
-- 组织表添加名称唯一约束
ALTER TABLE al_organization
  ADD UNIQUE INDEX uni_idx_name (name);

-- 项目表添加名称+组织唯一约束
ALTER TABLE al_project
  ADD UNIQUE INDEX uni_idx_name (name, organization_id);


/* 2019-03-04 */
-- 数据字典表
CREATE TABLE sys_dictionary (
  category VARCHAR(255) COMMENT '类型',
  code VARCHAR(255) PRIMARY KEY COMMENT '编码',
  name VARCHAR(255) COMMENT '名称',
  indexNo INT COMMENT '索引'
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

-- 任务标签
CREATE TABLE al_task_tag (
  id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'id',
  name VARCHAR(255) COMMENT '名称',
  color VARCHAR(255) COMMENT '颜色',
  createTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updateTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  UNIQUE INDEX uni_idx_name (name)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

-- 任务表
CREATE TABLE al_task (
  id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'id',
  name VARCHAR(255) COMMENT '名称',
  description TEXT COMMENT '描述',
  level_code VARCHAR(255) COMMENT '级别',
  status_code VARCHAR(255) COMMENT '状态',
  taskTag_id BIGINT COMMENT '所属标签',
  project_id BIGINT COMMENT '所属项目',
  planBeginDate DATETIME COMMENT '计划开始日期',
  planFinishDate DATETIME COMMENT '计划结束日期',
  beginDate DATETIME COMMENT '开始日期',
  finishDate DATETIME COMMENT '结束日期',
  createTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updateTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  CONSTRAINT fk_task_tag FOREIGN KEY (taskTag_id) REFERENCES al_task_tag (id),
  CONSTRAINT fk_task_project FOREIGN KEY (project_id) REFERENCES al_project (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;


/* 2019-03-08 */
-- 数据字典表新增任务级别
INSERT INTO
  sys_dictionary (category, code, name, indexNo)
VALUES
  ('com.asing1elife.teamnote.model.dictionary.TaskLevel', 'TALE_Normal', '普通', '0'),
  ('com.asing1elife.teamnote.model.dictionary.TaskLevel', 'TALE_Urgency', '紧急', '1'),
  ('com.asing1elife.teamnote.model.dictionary.TaskLevel', 'TALE_Very', '非常紧急', '2');

-- 数据字典表新增任务状态
INSERT INTO
  sys_dictionary (category, code, name, indexNo)
VALUES
  ('com.asing1elife.teamnote.model.dictionary.TaskStatus', 'TAST_Init', '初始化', '0'),
  ('com.asing1elife.teamnote.model.dictionary.TaskStatus', 'TAST_Impl', '进行中', '1'),
  ('com.asing1elife.teamnote.model.dictionary.TaskStatus', 'TAST_Finish', '已完成', '2');


/* 2019-03-24 */
-- 日志表
CREATE TABLE al_daily (
  id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'id',
  year INT COMMENT '年份',
  month INT COMMENT '月份',
  isExpense TINYINT(1) COMMENT '是否报销',
  expenseAmount DOUBLE COMMENT '报销金额',
  totalDay INT COMMENT '总计天数',
  createTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updateTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

-- 日志记录表
CREATE TABLE al_daily_record (
  id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'id',
  daily_id BIGINT COMMENT '所属日志',
  day INT COMMENT '日期',
  isExtra TINYINT(1) COMMENT '是否加班',
  createTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updateTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  CONSTRAINT fk_daily_record_daily FOREIGN KEY (daily_id) REFERENCES al_daily (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

-- 日志记录任务表
CREATE TABLE al_daily_record_task (
  dailyRecord_id BIGINT COMMENT '所属日志记录',
  task_id BIGINT COMMENT '所属任务',
  CONSTRAINT fk_daily_record_task_daily_record FOREIGN KEY (dailyRecord_id) REFERENCES al_daily_record (id),
  CONSTRAINT fk_daily_record_task_task FOREIGN KEY (task_id) REFERENCES al_task (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;


/* 2019-04-02 */
-- 日志记录表新增是否还班字段
ALTER TABLE al_daily_record
  ADD COLUMN isRepay TINYINT(1) COMMENT '是否还班' AFTER isExtra;


/* 2019-04-27 */
-- 日志表新增所属组织字段
ALTER TABLE al_daily
  ADD COLUMN organization_id BIGINT(20) COMMENT '所属组织' AFTER id;

-- 日志表与组织表增加外键关联
ALTER TABLE al_daily
  ADD CONSTRAINT fk_daily_organization FOREIGN KEY (organization_id) REFERENCES al_organization (id);


/* 2019-06-03 */
-- 日志记录表新增是否休息字段
ALTER TABLE al_daily_record
  ADD COLUMN isRest TINYINT(1) DEFAULT 0 COMMENT '是否休息' AFTER isRepay;


/* 2019-07-03 */
-- 修改日志记录表的部分字段
ALTER TABLE al_daily_record
  CHANGE COLUMN isExtra extra TINYINT(1) COMMENT '加班',
  CHANGE COLUMN isRepay repay TINYINT(1) COMMENT '还班',
  CHANGE COLUMN isRest rest TINYINT(1) COMMENT '休息';

-- 修改日志表部分字段
ALTER TABLE al_daily
  CHANGE COLUMN isExpense expense TINYINT(1) COMMENT '报销';


/* 2019-11-03 */
-- 项目表新增任务数量字段
ALTER TABLE al_project
  ADD COLUMN taskNum INT DEFAULT 0 COMMENT '任务数量' AFTER indexNo;

/* 2019-11-06 */
-- 修正项目表的任务数量
UPDATE
  al_project pru
SET
  pru.taskNum = (
    SELECT
      result.taskNum
    FROM
      (SELECT
         pr.id AS id,
         COUNT(ta.id) AS taskNum
       FROM
         al_project pr
         LEFT JOIN al_task ta ON pr.id = ta.project_id
       GROUP BY pr.id) AS result
    WHERE
      result.id = pru.id
  )
WHERE
  1 = 1;

/* 2020-02-21 */
-- 创建报告表
CREATE TABLE al_report (
  id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'id',
  name VARCHAR(255) COMMENT '名称',
  organization_id BIGINT COMMENT '所属组织',
  daily_id BIGINT COMMENT '所属日志',
  type_code VARCHAR(255) COMMENT '类型',
  createTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updateTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  CONSTRAINT fk_report_organization FOREIGN KEY (organization_id) REFERENCES al_organization (id),
  CONSTRAINT fk_report_daily FOREIGN KEY (daily_id) REFERENCES al_daily (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

-- 数据字典新增报告类型
INSERT INTO
  sys_dictionary (category, code, name, indexNo)
VALUES
  ('com.asing1elife.teamnote.model.dictionary.ReportType', 'RETY_Month', '月报', '0'),
  ('com.asing1elife.teamnote.model.dictionary.ReportType', 'RETY_Year', '年报', '1');


/* 2020-01-23 */
-- 报告表新增字段
ALTER TABLE al_report
  ADD COLUMN taskNum INT DEFAULT 0 COMMENT '任务数量' AFTER daily_id,
  ADD COLUMN taskFinishNum INT DEFAULT 0 COMMENT '任务完成数量' AFTER taskNum,
  ADD COLUMN projectNum INT DEFAULT 0 COMMENT '项目数量' AFTER taskFinishNum;

-- 报告表新增字段
ALTER TABLE al_report
  ADD COLUMN taskTagMemo VARCHAR(255) COMMENT '任务标签备注' AFTER projectNum;

-- 报告表新增字段
ALTER TABLE al_report
  ADD COLUMN dayNum VARCHAR(255) COMMENT '工作天数' AFTER taskTagMemo,
  ADD COLUMN dayExtraNum VARCHAR(255) COMMENT '工作天数' AFTER dayNum;

-- 报告表新增字段
ALTER TABLE al_report
  ADD COLUMN monthMemo VARCHAR(255) COMMENT '月备注' AFTER dayExtraNum,
  ADD COLUMN dayMemo VARCHAR(255) COMMENT '日备注' AFTER monthMemo;