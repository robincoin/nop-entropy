
CREATE TABLE nop_file_record(
  FILE_ID VARCHAR(32) NOT NULL    COMMENT '文件ID',
  FILE_NAME VARCHAR(300) NULL    COMMENT '文件名',
  FILE_PATH VARCHAR(2000) NOT NULL    COMMENT '文件路径',
  FILE_EXT VARCHAR(50) NULL    COMMENT '扩展名',
  MIME_TYPE VARCHAR(100) NOT NULL    COMMENT '内容类型',
  FILE_LENGTH BIGINT NULL    COMMENT '文件长度',
  FILE_LAST_MODIFIED TIMESTAMP NULL    COMMENT '文件修改时间',
  BIZ_OBJ_NAME VARCHAR(200) NULL    COMMENT '对象名',
  BIZ_OBJ_ID VARCHAR(200) NULL    COMMENT '对象ID',
  FIELD_NAME VARCHAR(100) NULL    COMMENT '字段名',
  FILE_HASH VARCHAR(200) NULL    COMMENT '文件摘要',
  DEL_FLAG TINYINT NOT NULL    COMMENT '删除标识',
  CREATED_BY VARCHAR(50) NOT NULL    COMMENT '创建人',
  CREATE_TIME TIMESTAMP NOT NULL  DEFAULT CURRENT_TIMESTAMP    COMMENT '创建时间',
  REMARK VARCHAR(200) NULL    COMMENT '备注',
  constraint PK_nop_file_record primary key (FILE_ID)
);


   ALTER TABLE nop_file_record COMMENT '文件记录';
                
