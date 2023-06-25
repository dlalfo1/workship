-- 스키마 선택
USE gdj61;

-- 테이블 삭제
DROP TABLE IF EXISTS MAIL_FILE_T;
DROP TABLE IF EXISTS MAIL_TO_T;
DROP TABLE IF EXISTS MAIL_T;

DROP TABLE IF EXISTS PROJECT_WORK_FILE_T;
DROP TABLE IF EXISTS PROJECT_FILE_T;
DROP TABLE IF EXISTS PROJECT_WORK_T;
DROP TABLE IF EXISTS PROJECT_T;
DROP TABLE IF EXISTS ING_T;

DROP TABLE IF EXISTS VACATION_T;

DROP TABLE IF EXISTS APPROVAL_FILE_T;
DROP TABLE IF EXISTS APPROVAL_REFERENCE_T;
DROP TABLE IF EXISTS APPROVAL_LINE_T;
DROP TABLE IF EXISTS APPROVAL_T;

DROP TABLE IF EXISTS REPORT_BOARD_T;
DROP TABLE IF EXISTS BOARD_FILE_T;
DROP TABLE IF EXISTS BOARD_COMMENT_T;
DROP TABLE IF EXISTS BOARD_T;

DROP TABLE IF EXISTS TODOLIST_T;
DROP TABLE IF EXISTS ATTENDANCE_T;

DROP TABLE IF EXISTS NOTICE_FILE_T;
DROP TABLE IF EXISTS NOTICE_BOARD_T;
DROP TABLE IF EXISTS RETIRED_MEMBER_T;
DROP TABLE IF EXISTS MEMBER_T;
DROP TABLE IF EXISTS JOB_T;
DROP TABLE IF EXISTS DEPARTMENT_T;

-- 테이블 생성
CREATE TABLE JOB_T (
	JOB_NO		INT			NOT NULL AUTO_INCREMENT,
	JOB_NAME	VARCHAR(8)	NOT NULL,
	CONSTRAINT PK_JOB PRIMARY KEY(JOB_NO)
);

CREATE TABLE DEPARTMENT_T (
	DEPT_NO		INT			NOT NULL AUTO_INCREMENT,
	DEPT_NAME	VARCHAR(20)	NOT NULL,
    CONSTRAINT PK_DEPT PRIMARY KEY(DEPT_NO)
);

CREATE TABLE MEMBER_T (
	MEMBER_NO		  INT			NOT NULL AUTO_INCREMENT,
	JOB_NO			  INT			NOT NULL,
	DEPT_NO			  INT			NOT NULL,
	MEMBER_NAME		  VARCHAR(20)	NOT NULL,
	EMAIL_ID		  VARCHAR(50)	NOT NULL UNIQUE,
	TEL				  VARCHAR(15)	NULL,
	BIRTHDAY	      VARCHAR(8)	NULL,
	JOINED_AT		  DATETIME		NOT NULL,
	MODIFIED_AT		  DATETIME		NULL,
	STATUS			  INT			NULL,
    TOTAL_DAYOFF      INT 			NULL,
	DAYOFF_COUNT	  INT			NULL,
	PW				  VARCHAR(64)	NOT NULL,
	PROFILE_FILE_PATH    VARCHAR(300)  NULL,
	PROFILE_FILE_NAME    VARCHAR(50)   NULL,
	AUTOLOGIN_ID         VARCHAR(50)   NULL,
	AUTOLOGIN_EXPIRED_AT DATE          NULL,
    CONSTRAINT PK_MEMBER PRIMARY KEY(MEMBER_NO),
    CONSTRAINT FK_MEMBER_JOB FOREIGN KEY(JOB_NO) REFERENCES JOB_T(JOB_NO),
    CONSTRAINT FK_MEMBER_DEPT FOREIGN KEY(DEPT_NO) REFERENCES DEPARTMENT_T(DEPT_NO)
);

CREATE TABLE RETIRED_MEMBER_T (
	RETIRED_MEMBER_NO	INT			NOT NULL,
	MEMBER_NAME			VARCHAR(20)	NOT NULL,
	TEL					VARCHAR(15)	NULL,
	JOINED_AT			DATETIME	NOT NULL,
	RETIRED_AT			DATETIME	NOT NULL,
    CONSTRAINT PK_RET_MEMBER PRIMARY KEY(RETIRED_MEMBER_NO)
);

CREATE TABLE ATTENDANCE_T (
	ATTENDANCE_NO INT  NOT NULL AUTO_INCREMENT,
    MEMBER_NO  	  INT  NOT NULL,
    DATE          DATE NOT NULL,
    ASTARTTIME    TIME,
    AENDTIME   	  TIME,
    WORKTIME      TIME,
    ATTENDANCE VARCHAR(10),
    CONSTRAINT PK_ATTENDANCE PRIMARY KEY(ATTENDANCE_NO),
    CONSTRAINT FK_ATTENDANCE_MEMBER FOREIGN KEY(MEMBER_NO) REFERENCES MEMBER_T(MEMBER_NO) ON DELETE CASCADE
);

CREATE TABLE TODOLIST_T (
	TODOLIST_NO INT NOT NULL AUTO_INCREMENT,
    MEMBER_NO   INT NOT NULL,
    TODO_TITLE  VARCHAR(200) NOT NULL,
    TODO_MEMO	LONGTEXT,
    TODO_STATE  INT NOT NULL,
	CONSTRAINT PK_TODOLIST PRIMARY KEY(TODOLIST_NO),
    CONSTRAINT FK_TODOLIST_MEMBER FOREIGN KEY(MEMBER_NO) REFERENCES MEMBER_T(MEMBER_NO) ON DELETE CASCADE
);

CREATE TABLE MAIL_T (
	MAIL_NO			INT				NOT NULL	AUTO_INCREMENT,
	EMAIL_ID		VARCHAR(50)		NOT NULL,
	MAIL_TITLE		VARCHAR(100)	NOT NULL,
	MAIL_CONTENT	LONGTEXT		NULL,
	MAIL_HAS_FILE	VARCHAR(2)		NULL,
    MAIL_DATE		DATETIME		NULL,
	MAIL_CATEGORY	VARCHAR(10)		NULL,
    CONSTRAINT PK_MAIL PRIMARY KEY(MAIL_NO),
    CONSTRAINT FK_MAIL_MEMBER FOREIGN KEY(EMAIL_ID) REFERENCES MEMBER_T(EMAIL_ID)
);

CREATE TABLE MAIL_TO_T (
	MAIL_TO_NO		 INT			NOT NULL	AUTO_INCREMENT,
	MAIL_NO			 INT			NOT NULL,
	MAIL_TO			 VARCHAR(50)	NULL,
    MAIL_TO_ROLE 	 VARCHAR(10) NULL,
    MAIL_TO_CATEGORY	 VARCHAR(10)		NULL,    
	MAIL_TO_STATUS		 VARCHAR(2)		NULL,
	MAIL_TO_STAR		 VARCHAR(2)		NULL,
    CONSTRAINT PK_MAIL_TO PRIMARY KEY(MAIL_TO_NO),
    CONSTRAINT FK_MAIL_TO_MAIL FOREIGN KEY(MAIL_NO) REFERENCES MAIL_T(MAIL_NO)
);

CREATE TABLE MAIL_FILE_T (
	MAIL_FILE_NO			INT				NOT NULL	AUTO_INCREMENT,
	MAIL_NO					INT				NOT NULL,
	MAIL_FILE_PATH			VARCHAR(300)	NOT NULL,
	MAIL_FILE_ORIGIN_NAME	VARCHAR(300)	NOT NULL,
	MAIL_FILE_SYSTEM_NAME	VARCHAR(50)		NOT NULL,
    CONSTRAINT PK_MAIL_FILE PRIMARY KEY(MAIL_FILE_NO),
    CONSTRAINT FK_MAIL_FILE_MAIL FOREIGN KEY(MAIL_NO) REFERENCES MAIL_T(MAIL_NO)
);

CREATE TABLE APPROVAL_T ( 
	APPROVAL_NO 		INT 		 NOT NULL AUTO_INCREMENT, -- 전자결재번호(PK)
    MEMBER_NO 			INT			 NOT NULL,			      -- 사원번호(FK)		
    DOC_NAME 			INT			 NOT NULL,				  -- 기안이름
    DOC_TITLE 			VARCHAR(100) NULL,				      -- 기안제목
    CREATED_AT 			DATETIME	 NOT NULL,			 	  -- 기안작성일자
    DOC_CONTENT 		LONGTEXT     NULL,					  -- 기안내용
    DOC_STATUS 			INT 		 NULL,				 	  -- 문서상태(0: 임시저장, 1: 등록)
    APPROVAL_STATUS 	INT			 NOT NULL,		  		  -- 기안진행상태(0: 결재대기, 1: 진행중, 2:결재완료, 3:반려, 4: 임시저장)
    APPROVAL_COUNT 		INT 		 NOT NULL,         		  -- 총 결재자수 (1, 2, 3 - 최대 3명)
    APPROVAL_SEQUENCE 	INT 		 NULL,				      -- 현재결재순서 (1, 2, 3)
    VACATION_CATEGORY   VARCHAR(15)  NULL,
    VACATION_START_DATE DATETIME 	 NULL,				  	  -- 휴가시작일(휴가신청서 관련)
    VACATION_END_DATE 	DATETIME 	 NULL,				 	  -- 휴가종료일(휴가신청서 관련)
    VACATION_STATE		INT			 NULL,
    PAY_DATE 		 	DATETIME 	 NULL,				  	  -- 지출일자(지출결의서 관련)
    RESIGNATION_DATE 	DATETIME 	 NULL,				 	  -- 퇴사예정일(사직서 관련)
    CONSTRAINT PK_APPROVAL PRIMARY KEY(APPROVAL_NO),
    CONSTRAINT FK_APPROVAL_MEMBER FOREIGN KEY(MEMBER_NO) REFERENCES MEMBER_T(MEMBER_NO)
);

-- 결재선 테이블
CREATE TABLE APPROVAL_LINE_T (
	APPROVAL_LINE_NO 	   INT 		    NOT NULL AUTO_INCREMENT, -- 결재선번호(PK)
    MEMBER_NO  	           INT 		    NOT NULL,				 -- 결재자사원번호(FK)
    APPROVAL_NO 		   INT 		    NOT NULL,			     -- 전자결재번호(FK)
    APPROVAL_ORDER  	   INT 		    NOT NULL,				 -- 결재순서번호(1, 2, 3)
    MEMBER_APPROVAL_STATUS INT 	  	    NULL,					 -- 사원별결재상태(0:대기중, 1:승인, 2:반려)
    APPROVAL_DATE 		   DATETIME 	NULL,					 -- 결재일
    DOC_COMMENT 	 	   VARCHAR(100) NULL,					 -- 반려의견
	CONSTRAINT PK_APPROVAL_LINE PRIMARY KEY(APPROVAL_LINE_NO),
    CONSTRAINT FK_APPROVAL_LINE_MEMBER FOREIGN KEY(MEMBER_NO) REFERENCES MEMBER_T(MEMBER_NO),
    CONSTRAINT FK_APPROVAL_LINE_APPROVAL FOREIGN KEY(APPROVAL_NO) REFERENCES APPROVAL_T(APPROVAL_NO)
);

-- 참조선 테이블
CREATE TABLE APPROVAL_REFERENCE_T (
	REFERENCE_NO INT NOT NULL AUTO_INCREMENT, -- 참조번호(PK)
    APPROVAL_NO  INT NOT NULL,                -- 전자결재번호(FK)
    MEMBER_NO    INT NOT NULL,                -- 참조자사원번호(FK)
    CONSTRAINT PK_APPROVAL_REFERENCE PRIMARY KEY(REFERENCE_NO),
    CONSTRAINT FK_APPROVAL_REFERENCE_APPROVAL FOREIGN KEY(APPROVAL_NO) REFERENCES APPROVAL_T(APPROVAL_NO),
    CONSTRAINT FK_APPROVAL_REFERENCE_MEMBER FOREIGN KEY(MEMBER_NO) REFERENCES MEMBER_T(MEMBER_NO)
);

-- 전자결재 첨부파일 테이블
CREATE TABLE APPROVAL_FILE_T (
	APPROVAL_FILE_NO 		  INT 		   NOT NULL AUTO_INCREMENT, -- 결재파일번호(PK)
    APPROVAL_NO 			  INT 		   NOT NULL,				-- 전자결재번호(FK)
    APPROVAL_FILE_PATH 	      VARCHAR(300) NOT NULL,				-- 첨부파일경로
    APPROVAL_FILE_ORIGIN_NAME VARCHAR(300) NOT NULL,				-- 첨부파일 원래이름
    APPROVAL_FILE_SYSTEM_NAME VARCHAR(300) NOT NULL,				-- 첨부파일 저장이름
    CONSTRAINT PK_APPROVAL_FILE PRIMARY KEY(APPROVAL_FILE_NO),
    CONSTRAINT FK_APPROVAL_FILE_APPROVAL FOREIGN KEY(APPROVAL_NO) REFERENCES APPROVAL_T(APPROVAL_NO)
);

CREATE TABLE VACATION_T (
	VACATION_NO 		INT NOT NULL AUTO_INCREMENT,
    MEMBER_NO   		INT NOT NULL,
    APPROVAL_NO 		INT,
    VACATION_DAY	    INT,
	CONSTRAINT PK_VACATION PRIMARY KEY(VACATION_NO),
    CONSTRAINT FK_VACATION_MEMBER FOREIGN KEY(MEMBER_NO) REFERENCES MEMBER_T(MEMBER_NO) ON DELETE CASCADE,
    CONSTRAINT FK_VACATION_APPROVAL FOREIGN KEY(APPROVAL_NO) REFERENCES APPROVAL_T(APPROVAL_NO) ON DELETE SET NULL
);

CREATE TABLE NOTICE_BOARD_T (
	NOTICE_NO			INT				NOT NULL AUTO_INCREMENT,
	MEMBER_NO			INT				NOT NULL,
	NOTICE_TITLE		VARCHAR(100)	NOT NULL,
	NOTICE_CONTENT		LONGTEXT		NOT NULL,
	NOTICE_CREATED_AT	DATETIME		NOT NULL,
	NOTICE_MODIFIED_AT	DATETIME		NULL,
	NOTICE_HIT			INT				NULL,
	NOTICE_STATE		INT				NULL,
    CONSTRAINT PK_NOTICE PRIMARY KEY(NOTICE_NO),
    CONSTRAINT FK_NOTICE_MEMBER FOREIGN KEY(MEMBER_NO) REFERENCES MEMBER_T(MEMBER_NO)
);

CREATE TABLE NOTICE_FILE_T (
	NOTICE_FILE_NO			INT				NOT NULL AUTO_INCREMENT,
	NOTICE_NO				INT				NOT NULL,
	NOTICE_FILE_PATH		VARCHAR(300)	NOT NULL,
	NOTICE_FILE_ORIGIN_NAME	VARCHAR(300)	NOT NULL,
	NOTICE_FILE_SYSTEM_NAME	VARCHAR(50)		NOT NULL,
    CONSTRAINT PK_NOTICE_FILE PRIMARY KEY(NOTICE_FILE_NO),
    CONSTRAINT FK_NOTICE_FILE_NOTICE FOREIGN KEY(NOTICE_NO) REFERENCES NOTICE_BOARD_T(NOTICE_NO) ON DELETE CASCADE
);

CREATE TABLE BOARD_T (
	BOARD_NO          INT          NOT NULL AUTO_INCREMENT,
    BOARD_TITLE       VARCHAR(100) NOT NULL,
    BOARD_CONTENT     LONGTEXT     NOT NULL,
    BOARD_CREATED_AT  DATETIME     NOT NULL,
	BOARD_MODIFIED_AT DATETIME,
    BOARD_CATEGORY    INT          NOT NULL,
    BOARD_HIT         INT,
    BOARD_STATE       INT,
    MEMBER_NO         INT          NOT NULL,
    CONSTRAINT PK_BOARD PRIMARY KEY(BOARD_NO),
    CONSTRAINT FK_BOARD_MEMBER FOREIGN KEY(MEMBER_NO) REFERENCES MEMBER_T(MEMBER_NO) ON DELETE CASCADE
    );

CREATE TABLE BOARD_COMMENT_T (
    COMMENT_NO                INT          NOT NULL AUTO_INCREMENT,
     COMMENT_WRITER_MEMBER_NO INT          NOT NULL,
     COMMENT_CONTENT          VARCHAR(200) NOT NULL,
     COMMENT_STATE            INT,
     COMMENT_DEPTH            INT,
     COMMENT_GROUP_NO         INT,
     COMMENT_CREATED_AT       DATETIME     NOT NULL,
     COMMENT_GROUP_ORDER      INT          NULL,
     BOARD_NO                 INT          NOT NULL,
     CONSTRAINT PK_COMMENT PRIMARY KEY(COMMENT_NO),
     CONSTRAINT FK_COMMENT_MEMBER FOREIGN KEY(COMMENT_WRITER_MEMBER_NO) REFERENCES MEMBER_T(MEMBER_NO) ON DELETE CASCADE
);
     
CREATE TABLE BOARD_FILE_T (
	BOARD_FILE_NO 		   INT 			NOT NULL AUTO_INCREMENT,
    BOARD_FILE_PATH 	   VARCHAR(300) NOT NULL,
    BOARD_FILE_ORIGIN_NAME VARCHAR(300) NOT NULL,
    BOARD_FILE_SYSTEM_NAME VARCHAR(50)  NOT NULL,
    BOARD_NO 			   INT 			NOT NULL,
    CONSTRAINT PK_BOARD_FILE PRIMARY KEY(BOARD_FILE_NO),
    CONSTRAINT FK_BOARD_FILE_BOARD FOREIGN KEY(BOARD_NO) REFERENCES BOARD_T(BOARD_NO) ON DELETE CASCADE
);

CREATE TABLE REPORT_BOARD_T (
    REPORT_NO        INT      NOT NULL AUTO_INCREMENT,
    BOARD_NO         INT      NULL,
    MEMBER_NO        INT      NOT NULL,
    REPORT_CONTENT   LONGTEXT NOT NULL,
    REPORT_STATE     INT      NOT NULL,
    REPORT_DATE      DATETIME NOT NULL,
    REPORT_DONE_DATE DATETIME NULL,
    CONSTRAINT PK_REPORT PRIMARY KEY(REPORT_NO),
    CONSTRAINT FK_REPORT_BOARD FOREIGN KEY(BOARD_NO) REFERENCES BOARD_T(BOARD_NO) ON DELETE SET NULL,
    CONSTRAINT FK_REPORT_MEMBER FOREIGN KEY(MEMBER_NO) REFERENCES MEMBER_T(MEMBER_NO)
);

CREATE TABLE ING_T (
	ING_NO    INT		 NOT NULL	AUTO_INCREMENT,
	ING_STATE VARCHAR(8) NOT NULL,									
	CONSTRAINT PK_ING PRIMARY KEY(ING_NO)
);

CREATE TABLE PROJECT_T (
	PROJECT_NO			INT				NOT NULL	AUTO_INCREMENT,
	MEMBER_NO			INT				NOT NULL,
	DEPT_NO				INT				NOT NULL,	
	PROJECT_START_AT	VARCHAR(20),	
	PROJECT_END_AT		VARCHAR(20),
	PROJECT_TITLE		VARCHAR(100)	NOT NULL,
    ING_NO				INT				NOT NULL,
    CONSTRAINT PK_PROJECT_T PRIMARY KEY(PROJECT_NO),
    CONSTRAINT FK_P_MM  FOREIGN KEY(MEMBER_NO) REFERENCES MEMBER_T(MEMBER_NO) ON DELETE CASCADE,
    CONSTRAINT FK_P_MD  FOREIGN KEY(DEPT_NO) REFERENCES MEMBER_T(DEPT_NO) ON DELETE CASCADE,
    CONSTRAINT FK_PR_ING FOREIGN KEY(ING_NO) REFERENCES ING_T(ING_NO) ON DELETE CASCADE
);

CREATE TABLE PROJECT_WORK_T (
	PROJECT_WORK_NO				INT				NOT NULL	AUTO_INCREMENT,
	PROJECT_NO					INT				NOT NULL,
	MEMBER_NO					INT				NOT NULL,
	DEPT_NO						INT				NOT NULL,
	PROJECT_WORK_TITLE			VARCHAR(100)	NOT NULL,
	PROJECT_WORK_DETAIL			LONGTEXT		NOT NULL,
	PROJECT_WORK_MODIFIED_AT	DATETIME,
    ING_NO						INT				NOT NULL,
    CONSTRAINT PK_PWORK_T PRIMARY KEY(PROJECT_WORK_NO),
    CONSTRAINT FK_PW_PN  FOREIGN KEY(PROJECT_NO) REFERENCES PROJECT_T(PROJECT_NO) ON DELETE CASCADE,
    CONSTRAINT FK_PW_MM  FOREIGN KEY(MEMBER_NO) REFERENCES MEMBER_T(MEMBER_NO) ON DELETE CASCADE,
    CONSTRAINT FK_PW_MD FOREIGN KEY(DEPT_NO) REFERENCES MEMBER_T(DEPT_NO) ON DELETE CASCADE,
    CONSTRAINT FK_PW_ING FOREIGN KEY(ING_NO) REFERENCES ING_T(ING_NO) ON DELETE CASCADE
);

CREATE TABLE PROJECT_FILE_T (
	PROJECT_FILE_NO				INT				NOT NULL	AUTO_INCREMENT,
	PROJECT_NO					INT				NOT NULL,
	PROJECT_FILE_PATH			VARCHAR(300)	NOT NULL,
	PROJECT_FILE_ORIGIN_NAME	VARCHAR(300)	NOT NULL,
	PROJECT_FILE_SYSTEM_NAME	VARCHAR(50)		NOT NULL,
    CONSTRAINT PK_PFILE_T PRIMARY KEY(PROJECT_FILE_NO),
    CONSTRAINT FK_PF_PN  FOREIGN KEY(PROJECT_NO) REFERENCES PROJECT_T(PROJECT_NO) ON DELETE CASCADE
);

CREATE TABLE PROJECT_WORK_FILE_T (
	PROJECT_WORK_FILE_NO			INT				NOT NULL	AUTO_INCREMENT,
	PROJECT_WORK_NO					INT				NOT NULL,
	PROJECT_WORK_FILE_PATH			VARCHAR(300)	NOT NULL,
	PROJECT_WORK_FILE_ORIGIN_NAME	VARCHAR(300)	NOT NULL,
	PROJECT_WORK_FILE_SYSTEM_NAME	VARCHAR(50)		NOT NULL,
    CONSTRAINT PK_PWORK_FILE_T PRIMARY KEY(PROJECT_WORK_FILE_NO),
    CONSTRAINT FK_PWF_PWN  FOREIGN KEY(PROJECT_WORK_NO) REFERENCES PROJECT_WORK_T(PROJECT_WORK_NO) ON DELETE CASCADE
);

DROP TRIGGER IF EXISTS RETIRE_TRIG;

DELIMITER //
CREATE TRIGGER RETIRE_TRIG
    AFTER DELETE
    ON MEMBER_T
    FOR EACH ROW
BEGIN
    INSERT INTO RETIRED_MEMBER_T(RETIRED_MEMBER_NO, MEMBER_NAME, TEL, JOINED_AT, RETIRED_AT)
    VALUES( OLD.MEMBER_NO, OLD.MEMBER_NAME, OLD.TEL, OLD.JOINED_AT, NOW() );
END; //
DELIMITER ;