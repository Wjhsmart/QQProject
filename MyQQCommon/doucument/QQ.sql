create database MyQQ --�½����ݿ���ΪQQ
 on primary -- ���ļ�
(name = 'MyQQ', filename = 'd:\MyQQ.mdf', size = 10mb, maxsize = unlimited, filegrowth = 1mb ) 
 log on -- ��־�ļ�
(name = 'MyQQ_log', filename = 'd:\MyQQ_log.ldf', size = 2mb, maxsize = 1024gb, filegrowth = 10%)
go

use MyQQ;
-- ������Ϣ��
create table t_account (
	number varchar(10) primary key, -- �˺�
	pwd varchar(32) not null, -- ����
	nickname varchar(30) not null, -- �ǳ�
	gender char(2) not null, -- �Ա�
	age int not null, -- ����
	autograph varchar(1000), -- ǩ��
	provice varchar(10) not null, -- ʡ
	city varchar(10) not null, -- ��
	area varchar(10) not null, -- ��
	status varchar(10) not null, -- ����״̬
	head varchar(100) not null default 'head', -- ͷ������
	mobile varchar(50) not null, -- �绰����
	skin varchar(30) not null default 'main_background4' -- Ƥ��
);

-- ����checkԼ��
alter table t_account add constraint CK_account_status check (status in('online', 'offline', 'leave', 'busy', 'qme', 'dont', 'hidden'));

-- �������ѱ�
create table t_friends (
	id int primary key identity(1, 1),
	account_number1 varchar(10) not null,
	account_number2 varchar(10) not null
);

-- ������Լ��
alter table t_friends add constraint FK_friends_account_number1 foreign key(account_number1) references t_account(number);
alter table t_friends add constraint FK_friends_account_number2 foreign key(account_number2) references t_account(number);

-- ������Ϣ��
create table t_message (
	id int primary key identity(1, 1),
	account_number1 varchar(10) not null,
	account_number2 varchar(10) not null,
	message varchar(1000),
	send_time datetime not null
);

-- ������Լ��
alter table t_message add constraint FK_message_account_number1 foreign key(account_number1) references t_account(number);
alter table t_message add constraint Fk_message_account_number2 foreign key(account_number2) references t_account(number);