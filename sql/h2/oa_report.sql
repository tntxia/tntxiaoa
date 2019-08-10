
create table work_plan_month(
	id varchar(50),
	number varchar(25) not null,
	man varchar(15),
	year int,
	month int,
	total decimal(18, 2) NULL,
	process float,
	client varchar(200) not null,
	product varchar(100),
	dept varchar(50) NULL,
	deptjb char(10) NULL,
	create_time datetime
)

create table work_plan_month_plan_item(
	id int IDENTITY(1,1) NOT NULL,
	pid varchar(50),
	date_start int,
	date_end int,
	key_point text,
	remark text
)

create table work_plan_month_target_item(
	id int IDENTITY(1,1) NOT NULL,
	pid varchar(50),
	target varchar(200) not null,
	date_before int,
	remark text,
	state varchar(50)
)

create table work_report_daily(
	id int IDENTITY(1,1) NOT NULL,
	number varchar(25) not null,
	dept varchar(50) NULL,
	man varchar(15),
	report_date date,
	report_desc text,
	create_time datetime
)

create table work_report_weekly(
	id int IDENTITY(1,1) NOT NULL,
	number varchar(25) not null,
	dept varchar(50) NULL,
	man varchar(15),
	report_date_start date,
	report_date_end date,
	report_desc text,
	total decimal(18, 2) NULL,
	create_time datetime
)

create table work_report_monthly(
	id int IDENTITY(1,1) NOT NULL,
	number varchar(25) not null,
	dept varchar(50) NULL,
	man varchar(15),
	report_date_start date,
	report_date_end date,
	report_desc text,
	total decimal(18, 2) NULL,
	create_time datetime
)

