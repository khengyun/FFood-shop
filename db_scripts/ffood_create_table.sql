-- create ffood database
create database ffood;
go

--use ffood database
use ffood;
go

-- Create 14 tables
create table FoodType (
	food_type_id		tinyint identity(1,1) not null primary key,
	food_type			nvarchar(20) not null
);

go

create table Food (
	food_id				smallint identity(1,1) not null primary key,
	food_name			nvarchar(50) not null,
	food_description	nvarchar(500) not null,
	food_price			money not null,
	food_status			bit not null,
	discount_percent	tinyint not null,
	food_img_url		varchar(255) null,
	food_type_id		tinyint not null foreign key references FoodType(food_type_id)
);

go

create table [Admin] (
	admin_id			tinyint identity(1,1) not null primary key,
	admin_fullname		nvarchar(50) not null,
);

go

create table AdminFood (
	admin_id			tinyint not null foreign key references [Admin](admin_id),
	food_id				smallint not null foreign key references Food(food_id)
);

go

create table Staff (
	staff_id			tinyint identity(1,1) not null primary key,
	staff_fullname		nvarchar(50) not null,
);

go

create table Voucher (
	voucher_id					tinyint identity(1,1) not null primary key,
	voucher_name				nvarchar(20) not null,
	voucher_discount_percent	tinyint not null
);

go

create table PromotionManager (
	pro_id				tinyint identity(1,1) not null primary key,
	pro_fullname		nvarchar(50) not null,
);

go

create table Customer (
	customer_id			int identity(1,1) not null primary key,
	customer_firstname	nvarchar(10) not null,
	customer_lastname	nvarchar(40) not null,
	customer_gender		nvarchar(5) not null,
	customer_phone		varchar(11) not null,
	customer_address	nvarchar(255) not null
);

go

-- Create index for Customer table to improve search performance
create index idx_customer_firstname_lastname_gender_phone_address
on Customer (customer_firstname, customer_lastname, customer_gender, customer_phone, customer_address);

go
create table Account (
	account_id			int identity(1,1) not null primary key,
	customer_id			int null foreign key references Customer(customer_id),
	staff_id			tinyint null foreign key references Staff(staff_id),
	pro_id				tinyint null foreign key references PromotionManager(pro_id),
	admin_id			tinyint null foreign key references [Admin](admin_id),
	account_username	nvarchar(50) not null,
	account_email		nvarchar(255) not null,
	account_password	char(32) not null,
	account_type		varchar(20) not null,
);

go

create table Cart (
	cart_id				int identity(1,1) not null primary key,
	customer_id			int not null foreign key references Customer(customer_id)
);

go

create table CartItem (
	cart_item_id		int identity(1,1) not null primary key,
	cart_id				int not null foreign key references Cart(cart_id),
	food_id				smallint not null foreign key references Food(food_id),
	food_price			money not null,
	food_quantity		tinyint not null
);

go

create table OrderStatus (
	order_status_id		tinyint identity(1,1) not null primary key,
	order_status		nvarchar(20) not null
);

go

create table PaymentMethod (
	payment_method_id	tinyint identity(1,1) not null primary key,
	payment_method		nvarchar(20) not null
);

go

create table [Order] (
	order_id			int identity(1,1) not null primary key,
	cart_id				int not null foreign key references Cart(cart_id),
	customer_id			int not null foreign key references Customer(customer_id),
	order_status_id		tinyint not null foreign key references OrderStatus(order_status_id),
	payment_method_id	tinyint not null foreign key references PaymentMethod(payment_method_id),
	voucher_id			tinyint null foreign key references Voucher(voucher_id),
	contact_phone		varchar(11) not null,
	delivery_address	nvarchar(255) not null,
	order_time			datetime not null,
	order_total			money not null,
	order_note			nvarchar(1023) null,
	delivery_time		datetime null,
	order_cancel_time	datetime null
);
