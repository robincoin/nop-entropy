
CREATE TABLE nop_wf_definition(
  WF_DEF_ID VARCHAR(32) NOT NULL    COMMENT '主键',
  WF_NAME VARCHAR(500) NOT NULL    COMMENT '工作流名称',
  WF_VERSION BIGINT NOT NULL    COMMENT '工作流版本',
  DISPLAY_NAME VARCHAR(200) NOT NULL    COMMENT '显示名称',
  DESCRIPTION VARCHAR(1000) NULL    COMMENT '描述',
  MODEL_TEXT LONGTEXT NOT NULL    COMMENT '模型文本',
  STATUS INTEGER NOT NULL    COMMENT '状态',
  VERSION INTEGER NOT NULL    COMMENT '数据版本',
  CREATED_BY VARCHAR(50) NOT NULL    COMMENT '创建人',
  CREATE_TIME TIMESTAMP NOT NULL  DEFAULT CURRENT_TIMESTAMP    COMMENT '创建时间',
  UPDATED_BY VARCHAR(50) NOT NULL    COMMENT '修改人',
  UPDATE_TIME TIMESTAMP NOT NULL  DEFAULT CURRENT_TIMESTAMP    COMMENT '修改时间',
  REMARK VARCHAR(200) NULL    COMMENT '备注',
  constraint PK_nop_wf_definition primary key (WF_DEF_ID)
);

CREATE TABLE nop_wf_status_history(
  SID VARCHAR(32) NOT NULL    COMMENT '主键',
  WF_ID VARCHAR(32) NOT NULL    COMMENT '主键',
  FROM_STATUS INTEGER NOT NULL    COMMENT '源状态',
  TO_STATUS INTEGER NOT NULL    COMMENT '目标状态',
  FROM_APP_STATE VARCHAR(100) NULL    COMMENT '源应用状态',
  TO_APP_STATE VARCHAR(100) NULL    COMMENT '目标应用状态',
  CHANGE_TIME TIMESTAMP NOT NULL  DEFAULT CURRENT_TIMESTAMP    COMMENT '状态变动时间',
  OPERATOR_ID VARCHAR(50) NULL    COMMENT '操作者ID',
  OPERATOR_NAME VARCHAR(50) NULL    COMMENT '操作者',
  VERSION INTEGER NOT NULL    COMMENT '数据版本',
  CREATED_BY VARCHAR(50) NOT NULL    COMMENT '创建人',
  CREATE_TIME TIMESTAMP NOT NULL  DEFAULT CURRENT_TIMESTAMP    COMMENT '创建时间',
  constraint PK_nop_wf_status_history primary key (SID)
);

CREATE TABLE nop_wf_step_instance_link(
  WF_ID VARCHAR(32) NOT NULL    COMMENT '工作流实例ID',
  STEP_ID VARCHAR(32) NOT NULL    COMMENT '步骤ID',
  NEXT_STEP_ID VARCHAR(32) NOT NULL    COMMENT '下一步骤 ID',
  EXEC_ACTION VARCHAR(200) NOT NULL    COMMENT '执行动作',
  CREATED_BY VARCHAR(50) NOT NULL    COMMENT '创建人',
  CREATE_TIME TIMESTAMP NOT NULL  DEFAULT CURRENT_TIMESTAMP    COMMENT '创建时间',
  constraint PK_nop_wf_step_instance_link primary key (WF_ID,STEP_ID,NEXT_STEP_ID)
);

CREATE TABLE nop_wf_step_actor(
  SID VARCHAR(32) NOT NULL    COMMENT '主键',
  WF_ID VARCHAR(32) NOT NULL    COMMENT '工作流实例ID',
  STEP_ID VARCHAR(32) NOT NULL    COMMENT '工作流步骤ID',
  ACTOR_TYPE VARCHAR(10) NULL    COMMENT '参与者类型',
  ACTOR_ID VARCHAR(100) NULL    COMMENT '参与者ID',
  ACTOR_DEPT_ID VARCHAR(50) NULL    COMMENT '参与者部门ID',
  ACTOR_NAME VARCHAR(100) NULL    COMMENT '参与者名称',
  VOTE_WEIGHT INTEGER NULL    COMMENT '投票权重',
  ASSIGN_FOR_USER BOOLEAN NOT NULL    COMMENT '是否分配到用户',
  VERSION INTEGER NOT NULL    COMMENT '数据版本',
  CREATED_BY VARCHAR(50) NOT NULL    COMMENT '创建人',
  CREATE_TIME TIMESTAMP NOT NULL  DEFAULT CURRENT_TIMESTAMP    COMMENT '创建时间',
  UPDATED_BY VARCHAR(50) NOT NULL    COMMENT '修改人',
  UPDATE_TIME TIMESTAMP NOT NULL  DEFAULT CURRENT_TIMESTAMP    COMMENT '修改时间',
  constraint PK_nop_wf_step_actor primary key (SID)
);

CREATE TABLE nop_wf_user_delegate(
  SID VARCHAR(32) NOT NULL    COMMENT '主键',
  USER_ID VARCHAR(32) NOT NULL    COMMENT '用户ID',
  DELEGATE_ID VARCHAR(32) NOT NULL    COMMENT '代理人ID',
  DELEGATE_SCOPE VARCHAR(300) NULL    COMMENT '代理范围',
  VERSION INTEGER NOT NULL    COMMENT '数据版本',
  CREATED_BY VARCHAR(50) NOT NULL    COMMENT '创建人',
  CREATE_TIME TIMESTAMP NOT NULL  DEFAULT CURRENT_TIMESTAMP    COMMENT '创建时间',
  UPDATED_BY VARCHAR(50) NOT NULL    COMMENT '修改人',
  UPDATE_TIME TIMESTAMP NOT NULL  DEFAULT CURRENT_TIMESTAMP    COMMENT '修改时间',
  constraint PK_nop_wf_user_delegate primary key (SID)
);

CREATE TABLE nop_wf_action(
  SID VARCHAR(32) NOT NULL    COMMENT '主键',
  WF_ID VARCHAR(32) NOT NULL    COMMENT '工作流实例ID',
  STEP_ID VARCHAR(32) NOT NULL    COMMENT '工作流步骤ID',
  ACTION_NAME VARCHAR(200) NOT NULL    COMMENT '动作名称',
  DISPLAY_NAME VARCHAR(200) NOT NULL    COMMENT '动作显示名称',
  EXEC_TIME TIMESTAMP NOT NULL  DEFAULT CURRENT_TIMESTAMP    COMMENT '执行时刻',
  CALLER_ID VARCHAR(50) NULL    COMMENT '调用者ID',
  CALLER_NAME VARCHAR(50) NULL    COMMENT '调用者姓名',
  OPINION VARCHAR(4000) NULL    COMMENT '意见',
  VERSION INTEGER NOT NULL    COMMENT '数据版本',
  CREATED_BY VARCHAR(50) NOT NULL    COMMENT '创建人',
  CREATE_TIME TIMESTAMP NOT NULL  DEFAULT CURRENT_TIMESTAMP    COMMENT '创建时间',
  UPDATED_BY VARCHAR(50) NOT NULL    COMMENT '修改人',
  UPDATE_TIME TIMESTAMP NOT NULL  DEFAULT CURRENT_TIMESTAMP    COMMENT '修改时间',
  constraint PK_nop_wf_action primary key (SID)
);

CREATE TABLE nop_wf_output(
  WF_ID VARCHAR(32) NOT NULL    COMMENT '工作流实例ID',
  FIELD_NAME VARCHAR(100) NOT NULL    COMMENT '变量名',
  FIELD_TYPE INTEGER NOT NULL    COMMENT '变量类型',
  STRING_VALUE VARCHAR(4000) NULL    COMMENT '字符串值',
  DECIMAL_VALUE DECIMAL(30,6) NULL    COMMENT '浮点值',
  LONG_VALUE BIGINT NULL    COMMENT '整数型',
  DATE_VALUE DATE NULL    COMMENT '日期值',
  TIMESTAMP_VALUE TIMESTAMP NULL    COMMENT '时间点值',
  VERSION INTEGER NOT NULL    COMMENT '数据版本',
  CREATED_BY VARCHAR(50) NOT NULL    COMMENT '创建人',
  CREATE_TIME TIMESTAMP NOT NULL  DEFAULT CURRENT_TIMESTAMP    COMMENT '创建时间',
  UPDATED_BY VARCHAR(50) NOT NULL    COMMENT '修改人',
  UPDATE_TIME TIMESTAMP NOT NULL  DEFAULT CURRENT_TIMESTAMP    COMMENT '修改时间',
  constraint PK_nop_wf_output primary key (WF_ID,FIELD_NAME)
);

CREATE TABLE nop_wf_var(
  WF_ID VARCHAR(32) NOT NULL    COMMENT '工作流实例ID',
  FIELD_NAME VARCHAR(100) NOT NULL    COMMENT '变量名',
  FIELD_TYPE INTEGER NOT NULL    COMMENT '变量类型',
  STRING_VALUE VARCHAR(4000) NULL    COMMENT '字符串值',
  DECIMAL_VALUE DECIMAL(30,6) NULL    COMMENT '浮点值',
  LONG_VALUE BIGINT NULL    COMMENT '整数型',
  DATE_VALUE DATE NULL    COMMENT '日期值',
  TIMESTAMP_VALUE TIMESTAMP NULL    COMMENT '时间点值',
  VERSION INTEGER NOT NULL    COMMENT '数据版本',
  CREATED_BY VARCHAR(50) NOT NULL    COMMENT '创建人',
  CREATE_TIME TIMESTAMP NOT NULL  DEFAULT CURRENT_TIMESTAMP    COMMENT '创建时间',
  UPDATED_BY VARCHAR(50) NOT NULL    COMMENT '修改人',
  UPDATE_TIME TIMESTAMP NOT NULL  DEFAULT CURRENT_TIMESTAMP    COMMENT '修改时间',
  constraint PK_nop_wf_var primary key (WF_ID,FIELD_NAME)
);

CREATE TABLE nop_wf_log(
  SID VARCHAR(32) NOT NULL    COMMENT '日志ID',
  WF_ID VARCHAR(32) NOT NULL    COMMENT '工作流实例ID',
  STEP_ID VARCHAR(32) NULL    COMMENT '工作流步骤ID',
  LOG_LEVEL INTEGER NOT NULL    COMMENT '日志级别',
  LOG_MSG VARCHAR(4000) NULL    COMMENT '日志消息',
  CREATED_BY VARCHAR(50) NOT NULL    COMMENT '创建人',
  CREATE_TIME TIMESTAMP NOT NULL  DEFAULT CURRENT_TIMESTAMP    COMMENT '创建时间',
  constraint PK_nop_wf_log primary key (SID)
);

CREATE TABLE nop_wf_work(
  WORK_ID VARCHAR(32) NOT NULL    COMMENT '工作ID',
  WF_ID VARCHAR(32) NULL    COMMENT '工作流实例ID',
  STEP_ID VARCHAR(32) NULL    COMMENT '工作流步骤ID',
  WORK_TYPE VARCHAR(10) NOT NULL    COMMENT '工作类型',
  TITLE VARCHAR(2000) NOT NULL    COMMENT '工作标题',
  LINK_URL VARCHAR(2000) NOT NULL    COMMENT '工作链接',
  STATUS INTEGER NOT NULL    COMMENT '状态',
  OWNER_ID VARCHAR(50) NULL    COMMENT '拥有者ID',
  OWNER_NAME VARCHAR(50) NULL    COMMENT '拥有者姓名',
  CALLER_ID VARCHAR(50) NULL    COMMENT '调用者ID',
  CALLER_NAME VARCHAR(50) NULL    COMMENT '调用者姓名',
  READ_TIME TIMESTAMP NULL    COMMENT '读取时间',
  FINISH_TIME TIMESTAMP NULL    COMMENT '完成时间',
  VERSION INTEGER NOT NULL    COMMENT '数据版本',
  CREATED_BY VARCHAR(50) NOT NULL    COMMENT '创建人',
  CREATE_TIME TIMESTAMP NOT NULL  DEFAULT CURRENT_TIMESTAMP    COMMENT '创建时间',
  UPDATED_BY VARCHAR(50) NOT NULL    COMMENT '修改人',
  UPDATE_TIME TIMESTAMP NOT NULL  DEFAULT CURRENT_TIMESTAMP    COMMENT '修改时间',
  REMARK VARCHAR(200) NULL    COMMENT '备注',
  constraint PK_nop_wf_work primary key (WORK_ID)
);

CREATE TABLE nop_wf_step_instance(
  STEP_ID VARCHAR(32) NOT NULL    COMMENT '步骤ID',
  WF_ID VARCHAR(32) NOT NULL    COMMENT '工作流实例ID',
  STEP_TYPE VARCHAR(10) NOT NULL    COMMENT '步骤类型',
  STEP_NAME VARCHAR(200) NOT NULL    COMMENT '步骤名称',
  DISPLAY_NAME VARCHAR(200) NOT NULL    COMMENT '步骤显示名称',
  STATUS INTEGER NOT NULL    COMMENT '状态',
  APP_STATE VARCHAR(100) NULL    COMMENT '应用状态',
  SUB_WF_ID VARCHAR(32) NULL    COMMENT '子工作流ID',
  SUB_WF_NAME VARCHAR(200) NULL    COMMENT '子工作流名',
  SUB_WF_VERSION BIGINT NULL    COMMENT '子流程版本',
  SUB_WF_RESULT_STATUS INTEGER NULL    COMMENT '子流程结果状态',
  IS_READ BOOLEAN NULL    COMMENT '是否已读',
  ACTOR_TYPE VARCHAR(10) NULL    COMMENT '参与者类型',
  ACTOR_ID VARCHAR(100) NULL    COMMENT '参与者ID',
  ACTOR_DEPT_ID VARCHAR(50) NULL    COMMENT '参与者部门ID',
  ACTOR_NAME VARCHAR(100) NULL    COMMENT '参与者名称',
  OWNER_ID VARCHAR(50) NULL    COMMENT '拥有者ID',
  OWNER_NAME VARCHAR(50) NULL    COMMENT '拥有者姓名',
  ASSIGNER_ID VARCHAR(50) NULL    COMMENT '分配者ID',
  ASSIGNER_NAME VARCHAR(50) NULL    COMMENT '分配者姓名',
  CALLER_ID VARCHAR(50) NULL    COMMENT '调用者ID',
  CALLER_NAME VARCHAR(50) NULL    COMMENT '调用者姓名',
  CANCELLER_ID VARCHAR(50) NULL    COMMENT '取消人ID',
  CANCELLER_NAME VARCHAR(50) NULL    COMMENT '取消人姓名',
  FROM_ACTION VARCHAR(200) NULL    COMMENT '来源操作',
  LAST_ACTION VARCHAR(200) NULL    COMMENT '最后一次操作',
  START_TIME TIMESTAMP NULL    COMMENT '开始时间',
  FINISH_TIME TIMESTAMP NULL    COMMENT '结束时间',
  DUE_TIME TIMESTAMP NULL    COMMENT '到期时间',
  READ_TIME TIMESTAMP NULL    COMMENT '读取时间',
  REMIND_TIME TIMESTAMP NULL    COMMENT '提醒时间',
  REMIND_COUNT INTEGER NULL    COMMENT '提醒次数',
  PRIORITY INTEGER NOT NULL    COMMENT '优先级',
  JOIN_GROUP VARCHAR(100) NULL    COMMENT '汇聚分组',
  TAG_SET VARCHAR(200) NULL    COMMENT '标签',
  VERSION INTEGER NOT NULL    COMMENT '数据版本',
  CREATED_BY VARCHAR(50) NOT NULL    COMMENT '创建人',
  CREATE_TIME TIMESTAMP NOT NULL  DEFAULT CURRENT_TIMESTAMP    COMMENT '创建时间',
  UPDATED_BY VARCHAR(50) NOT NULL    COMMENT '修改人',
  UPDATE_TIME TIMESTAMP NOT NULL  DEFAULT CURRENT_TIMESTAMP    COMMENT '修改时间',
  REMARK VARCHAR(200) NULL    COMMENT '备注',
  constraint PK_nop_wf_step_instance primary key (STEP_ID)
);

CREATE TABLE nop_wf_instance(
  WF_ID VARCHAR(32) NOT NULL    COMMENT '主键',
  WF_NAME VARCHAR(500) NOT NULL    COMMENT '工作流名称',
  WF_VERSION BIGINT NOT NULL    COMMENT '工作流版本',
  WF_PARAMS VARCHAR(4000) NULL    COMMENT '工作流参数',
  WF_GROUP VARCHAR(100) NOT NULL    COMMENT '工作流分组',
  WORK_SCOPE VARCHAR(100) NULL    COMMENT '工作分类',
  TITLE VARCHAR(200) NOT NULL    COMMENT '实例标题',
  STATUS INTEGER NOT NULL    COMMENT '状态',
  APP_STATE VARCHAR(100) NULL    COMMENT '应用状态',
  START_TIME TIMESTAMP NULL    COMMENT '启动时间',
  END_TIME TIMESTAMP NULL    COMMENT '结束时间',
  SUSPEND_TIME TIMESTAMP NULL    COMMENT '暂停时间',
  DUE_TIME TIMESTAMP NULL    COMMENT '完成时限',
  BIZ_KEY VARCHAR(200) NULL    COMMENT '业务唯一键',
  BIZ_OBJ_NAME VARCHAR(200) NULL    COMMENT '业务对象名',
  BIZ_OBJ_ID VARCHAR(200) NULL    COMMENT '业务对象ID',
  PARENT_WF_NAME VARCHAR(500) NULL    COMMENT '父工作流名称',
  PARENT_WF_VERSION BIGINT NULL    COMMENT '父流程版本',
  PARENT_WF_ID VARCHAR(32) NULL    COMMENT '父流程ID',
  PARENT_STEP_ID VARCHAR(200) NULL    COMMENT '父流程步骤ID',
  STARTER_ID VARCHAR(50) NULL    COMMENT '启动人ID',
  STARTER_NAME VARCHAR(50) NULL    COMMENT '启动人',
  STARTER_DEPT_ID VARCHAR(50) NULL    COMMENT '启动人单位ID',
  LAST_OPERATOR_ID VARCHAR(50) NULL    COMMENT '上次操作者ID',
  LAST_OPERATOR_NAME VARCHAR(50) NULL    COMMENT '上次操作者',
  LAST_OPERATE_TIME TIMESTAMP NULL    COMMENT '上次操作时间',
  MANAGER_TYPE VARCHAR(50) NULL    COMMENT '管理者类型',
  MANAGER_DEPT_ID VARCHAR(50) NULL    COMMENT '管理者单位ID',
  MANAGER_NAME VARCHAR(50) NULL    COMMENT '管理者',
  MANAGER_ID VARCHAR(50) NULL    COMMENT '管理者ID',
  SIGNAL_SET VARCHAR(1000) NULL    COMMENT '信号集合',
  VERSION INTEGER NOT NULL    COMMENT '数据版本',
  CREATED_BY VARCHAR(50) NOT NULL    COMMENT '创建人',
  CREATE_TIME TIMESTAMP NOT NULL  DEFAULT CURRENT_TIMESTAMP    COMMENT '创建时间',
  UPDATED_BY VARCHAR(50) NOT NULL    COMMENT '修改人',
  UPDATE_TIME TIMESTAMP NOT NULL  DEFAULT CURRENT_TIMESTAMP    COMMENT '修改时间',
  PRIORITY INTEGER NOT NULL    COMMENT '优先级',
  REMARK VARCHAR(200) NULL    COMMENT '备注',
  constraint PK_nop_wf_instance primary key (WF_ID)
);


   ALTER TABLE nop_wf_definition COMMENT '工作流模型定义';
                
   ALTER TABLE nop_wf_status_history COMMENT '工作流状态变迁历史';
                
   ALTER TABLE nop_wf_step_instance_link COMMENT '工作流步骤关联';
                
   ALTER TABLE nop_wf_step_actor COMMENT '工作流步骤参与者';
                
   ALTER TABLE nop_wf_user_delegate COMMENT '用户代理配置';
                
   ALTER TABLE nop_wf_action COMMENT '工作流动作';
                
   ALTER TABLE nop_wf_output COMMENT '工作流输出变量';
                
   ALTER TABLE nop_wf_var COMMENT '工作流状态变量';
                
   ALTER TABLE nop_wf_log COMMENT '工作流日志';
                
   ALTER TABLE nop_wf_work COMMENT '代办工作';
                
   ALTER TABLE nop_wf_step_instance COMMENT '工作流步骤实例';
                
   ALTER TABLE nop_wf_instance COMMENT '工作流模型定义';
                
