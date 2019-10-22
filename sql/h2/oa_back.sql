
drop table if exists menu;
CREATE TABLE menu(
	id varchar(32),
	name varchar(100),
	url varchar(100),
	key_name varchar(50),
	order_no int,
	pid varchar(32)
)

drop table if exists leftbar;
CREATE TABLE leftbar(
	id varchar(32) NOT NULL,
	name varchar(100),
	url varchar(200),
	restrain numeric,
	pid varchar(32)
)

CREATE TABLE restrain_menu(
	id int IDENTITY(1,1) NOT NULL,
	restrain_id int,
	menu_id int
)

CREATE TABLE user_right(
	id int NOT NULL auto_increment,
	name varchar(50) NULL,
	display varchar(200) NULL,
	group_id int
);

CREATE TABLE user_role_rel(
	id int NOT NULL auto_increment,
	user_id int,
	role_id int
);

CREATE TABLE user_right_group(
	id int NOT NULL auto_increment,
	name varchar(50) NULL
);

CREATE TABLE role_right_rel(
	id int IDENTITY(1,1) NOT NULL,
	roleId int NOT NULL,
	rightId int NOT NULL,
 
);

CREATE TABLE send_bill(
	id int NOT NULL auto_increment,
	number varchar(10),
	man varchar(20),
	create_time datetime,
	company_id int,
	sale_number varchar(32),
	coname varchar(100),
	co_tel varchar(70)
);

CREATE TABLE send_bill_pro(
	id int NOT NULL auto_increment,
	model varchar(300),
	brand varchar(32),
	batch varchar(32),
	num int,
	price decimal(18,2),
	remark varchar(32),
	pid int
);


CREATE TABLE work_flow(
	id int IDENTITY(1,1) NOT NULL,
	flow_type varchar(50),
	dept char(30) NULL,
	audit_man char(15) NOT NULL,
	remark varchar(350) NULL,
	create_time datetime NOT NULL
)

drop table if exists work_clock;
CREATE TABLE work_clock(
	id varchar(32) NOT NULL,
	username varchar(50) NOT NULL,
	clock_date date NOT NULL,
	clock_in_time datetime NOT NULL,
	clock_out_time datetime,
	clock_times int,
	remark varchar(350) NULL
)

drop table if exists work_clock_detail;
CREATE TABLE work_clock_detail(
	id varchar(32) NOT NULL,
	clock_time datetime NOT NULL,
	pid varchar(32) NOT NULL
)



