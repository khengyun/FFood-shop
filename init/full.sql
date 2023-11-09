USE [master]
GO

/*******************************************************************************
   Drop database if it exists
********************************************************************************/
IF EXISTS (SELECT name FROM master.dbo.sysdatabases WHERE name = N'ffood')
BEGIN
	ALTER DATABASE ffood SET OFFLINE WITH ROLLBACK IMMEDIATE;
	ALTER DATABASE ffood SET ONLINE;
	DROP DATABASE ffood;
END

GO

CREATE DATABASE ffood
GO

USE ffood
GO

/*******************************************************************************
	Drop tables if exists
*******************************************************************************/
DECLARE @sql nvarchar(MAX) 
SET @sql = N'' 

SELECT @sql = @sql + N'ALTER TABLE ' + QUOTENAME(KCU1.TABLE_SCHEMA) 
    + N'.' + QUOTENAME(KCU1.TABLE_NAME) 
    + N' DROP CONSTRAINT ' -- + QUOTENAME(rc.CONSTRAINT_SCHEMA)  + N'.'  -- not in MS-SQL
    + QUOTENAME(rc.CONSTRAINT_NAME) + N'; ' + CHAR(13) + CHAR(10) 
FROM INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS AS RC 

INNER JOIN INFORMATION_SCHEMA.KEY_COLUMN_USAGE AS KCU1 
    ON KCU1.CONSTRAINT_CATALOG = RC.CONSTRAINT_CATALOG  
    AND KCU1.CONSTRAINT_SCHEMA = RC.CONSTRAINT_SCHEMA 
    AND KCU1.CONSTRAINT_NAME = RC.CONSTRAINT_NAME 

EXECUTE(@sql) 

GO
DECLARE @sql2 NVARCHAR(max)=''

SELECT @sql2 += ' Drop table ' + QUOTENAME(TABLE_SCHEMA) + '.'+ QUOTENAME(TABLE_NAME) + '; '
FROM   INFORMATION_SCHEMA.TABLES
WHERE  TABLE_TYPE = 'BASE TABLE'

Exec Sp_executesql @sql2 
GO 

--use ffood database
use ffood;
GO

-- Create 16 tables
create table FoodType (
	food_type_id	tinyint identity(1,1) not null primary key,
	food_type		nvarchar(20) not null
);

GO

create table Food (
	food_id			smallint identity(1,1) not null primary key,
	food_name		nvarchar(500) not null,
	food_description	nvarchar(2000) null,
	food_price		money not null,
        food_limit		smallint not null,
	food_status		bit not null,
	food_rate		tinyint null,
	discount_percent	tinyint not null,
	food_img_url		varchar(400) not null,
	food_type_id		tinyint not null foreign key references FoodType(food_type_id)
);

GO

create table [Admin] (
	admin_id		tinyint identity(1,1) not null primary key,
	admin_fullname		nvarchar(200) not null,
);

GO

create table AdminFood (
	admin_id		tinyint not null foreign key references [Admin](admin_id),
	food_id			smallint not null foreign key references Food(food_id)
);

GO

create table Staff (
	staff_id		tinyint identity(1,1) not null primary key,
	staff_fullname		nvarchar(200) not null,
);

GO

create table Voucher (
	voucher_id		tinyint identity(1,1) not null primary key,
	voucher_name		nvarchar(200) not null,
	voucher_code			char(16) not null,
	voucher_discount_percent	tinyint not null,
	voucher_quantity		tinyint not null,
	voucher_status			bit not null,
	voucher_date			datetime not null
);

GO

create table PromotionManager (
	pro_id			tinyint identity(1,1) not null primary key,
	pro_fullname		nvarchar(200) not null,
);

GO

create table Customer (
	customer_id		int identity(1,1) not null primary key,
	customer_firstname	nvarchar(200) not null,
	customer_lastname	nvarchar(200) not null,
	customer_gender		nvarchar(5) not null,
	customer_phone		varchar(11) not null,
	customer_address	nvarchar(1000) not null
);

GO

-- Create index for Customer table to improve search performance
create index idx_customer_firstname_lastname_gender_phone_address
on Customer (customer_firstname, customer_lastname, customer_gender, customer_phone, customer_address);

GO

create table Account (
	account_id		int identity(1,1) not null primary key,
	customer_id		int null foreign key references Customer(customer_id),
	staff_id		tinyint null foreign key references Staff(staff_id),
	pro_id			tinyint null foreign key references PromotionManager(pro_id),
	admin_id		tinyint null foreign key references [Admin](admin_id),
	account_username	nvarchar(100) not null,
	account_email		nvarchar(500) not null,
	account_password	char(32) not null,
	account_type		varchar(20) not null,
	lastime_order 	datetime null
);

GO

create table Cart (
	cart_id			int identity(1,1) not null primary key,
	customer_id		int not null foreign key references Customer(customer_id)
);

GO

create table CartItem (
	cart_item_id		int identity(1,1) not null primary key,
	cart_id			int not null foreign key references Cart(cart_id),
	food_id			smallint not null foreign key references Food(food_id),
	food_price		money not null,
	food_quantity		tinyint not null
);

GO

create table OrderStatus (
	order_status_id		tinyint identity(1,1) not null primary key,
	order_status		nvarchar(50) not null
);

GO

create table PaymentMethod (
	payment_method_id	tinyint identity(1,1) not null primary key,
	payment_method		nvarchar(50) not null
);

GO

create table [Order] (
	order_id		int identity(1,1) not null primary key,
	cart_id			int not null foreign key references Cart(cart_id),
	customer_id		int not null foreign key references Customer(customer_id),
	order_status_id		tinyint not null foreign key references OrderStatus(order_status_id),
	payment_method_id	tinyint not null foreign key references PaymentMethod(payment_method_id),
	voucher_id		tinyint null foreign key references Voucher(voucher_id),
	contact_phone		varchar(11) not null,
	delivery_address	nvarchar(500) not null,
	order_time		datetime not null,
	order_total		money not null,
	order_note		nvarchar(1023) null,
	delivery_time		datetime null,
	order_cancel_time	datetime null
);

GO

create table Payment (
    order_id                int not null foreign key references [Order](order_id),
    payment_method_id       tinyint not null foreign key references PaymentMethod(payment_method_id),
    payment_total           money not null,
    payment_content         nvarchar(1023) null,
    payment_bank            nvarchar(50) null,
    payment_code            varchar(20) null,
    payment_status          tinyint not null,
    payment_time            datetime not null
);

GO

create table OrderLog (
    log_id				int identity(1,1) not null primary key,
    order_id            int not null foreign key references [Order](order_id),
    staff_id			tinyint null foreign key references Staff(staff_id),
    admin_id			tinyint null foreign key references [Admin](admin_id),
    log_activity        nvarchar(100) not null,
    log_time            datetime not null
);

GO

--Use ffood database
USE ffood
GO

--Set status of food = 0 if limit = 0
CREATE TRIGGER tr_UpdateFoodStatus
ON Food
AFTER UPDATE
AS
BEGIN
    IF UPDATE(food_limit)
    BEGIN
        UPDATE Food
        SET food_status = 0
        WHERE food_limit = 0;
    END
END;

GO

-- Inactivate food when delete
CREATE TRIGGER tr_InactivateFood
ON Food
INSTEAD OF DELETE
AS
BEGIN
    UPDATE Food
    SET food_status = 0
    WHERE food_id IN (SELECT food_id FROM deleted);
END;

GO

-- Remove cart after customer deleted
CREATE TRIGGER tr_delete_cart_links
ON Account
AFTER DELETE
AS
BEGIN
    DELETE FROM Cart WHERE customer_id IN (SELECT deleted.customer_id FROM deleted);
END

GO

-- Don't delete when still have order
CREATE TRIGGER tr_prevent_delete_customer
ON Customer
INSTEAD OF DELETE
AS
BEGIN
    IF (EXISTS (SELECT 1 FROM Cart WHERE customer_id = (SELECT customer_id FROM deleted)) OR
        EXISTS (SELECT 1 FROM [Order] WHERE customer_id = (SELECT customer_id FROM deleted)))
    BEGIN
        RAISERROR('Cannot delete customer with active cart or orders.', 16, 1)
    END
    ELSE
    BEGIN
        DELETE FROM Customer WHERE customer_id = (SELECT customer_id FROM deleted)
    END
END

GO

--Use ffood database
use ffood;

-- Insert Admin records
insert into [Admin] (admin_fullname) values (N'Nguyễn Vũ Như Huỳnh');
insert into [Admin] (admin_fullname) values (N'Nguyễn Hoàng Khang');
insert into [Admin] (admin_fullname) values (N'Huỳnh Khắc Huy');
insert into [Admin] (admin_fullname) values (N'Hứa Tiến Thành');
insert into [Admin] (admin_fullname) values (N'Nguyễn Quốc Anh');
insert into [Admin] (admin_fullname) values (N'Huỳnh Duy Khang');

-- Insert Account records for Admins
-- Admin passwords are 'admin#' where # ranges from 1 to 6
-- Hash the passwords using MD5 algorithm
-- Admin passwords = 123456
-- Admin account ID starts from 1-20

INSERT INTO Account (admin_id, account_username, account_email, account_password, account_type) VALUES (1, N'vuhuynh123', N'huynhnvnce170550@fpt.edu.vn', CONVERT(NVARCHAR(32), HashBytes('MD5', '123456'), 2), 'admin');
insert into Account (admin_id, account_username, account_email, account_password, account_type) values (2, N'hoangkhang123', N'khangnhce171197@fpt.edu.vn', CONVERT(NVARCHAR(32), HashBytes('MD5', '123456'), 2), 'admin');
insert into Account (admin_id, account_username, account_email, account_password, account_type) values (3, N'khachuy123', N'huyhkce171229@fpt.edu.vn', CONVERT(NVARCHAR(32), HashBytes('MD5', '123456'), 2), 'admin');
insert into Account (admin_id, account_username, account_email, account_password, account_type) values (4, N'tienthanh123', N'thanhhtce171454@fpt.edu.vn', CONVERT(NVARCHAR(32), HashBytes('MD5', '123456'), 2), 'admin');
insert into Account (admin_id, account_username, account_email, account_password, account_type) values (5, N'quocanh1130', N'anhnqce170483@fpt.edu.vn', CONVERT(NVARCHAR(32), HashBytes('MD5', '123456'), 2), 'admin');
insert into Account (admin_id, account_username, account_email, account_password, account_type) values (6, N'duykhang123', N'khanghdse172647@fpt.edu.vn', CONVERT(NVARCHAR(32), HashBytes('MD5', '123456'), 2), 'admin');

-- Staffs must be added before an associated Account (if exists) can be created
insert into Staff (staff_fullname) values (N'Test Staff Một');
insert into Staff (staff_fullname) values (N'Test Staff Hai');
insert into Staff (staff_fullname) values (N'Test Staff Ba');
insert into Staff (staff_fullname) values (N'Test Staff Bốn');
insert into Staff (staff_fullname) values (N'Test Staff Năm');
insert into Staff (staff_fullname) values (N'Test Staff Sáu');
-- Reset the identity seed for the Account table to 20
-- Staffs' account ID starts from 21-40
dbcc checkident (Account, RESEED, 50);
-- Insert Staff Account
insert into Account(staff_id, account_username, account_email, account_password, account_type) values (1, N'testStaff1', N'teststaff1@fpt.edu.vn', CONVERT(NVARCHAR(32), HashBytes('MD5', '123456'), 2), 'staff');
insert into Account(staff_id, account_username, account_email, account_password, account_type) values (2, N'testStaff2', N'teststaff2@fpt.edu.vn', CONVERT(NVARCHAR(32), HashBytes('MD5', '123456'), 2), 'staff');
insert into Account(staff_id, account_username, account_email, account_password, account_type) values (3, N'testStaff3', N'teststaff3@fpt.edu.vn', CONVERT(NVARCHAR(32), HashBytes('MD5', '123456'), 2), 'staff');
insert into Account(staff_id, account_username, account_email, account_password, account_type) values (4, N'testStaff4', N'teststaff4@fpt.edu.vn', CONVERT(NVARCHAR(32), HashBytes('MD5', '123456'), 2), 'staff');
insert into Account(staff_id, account_username, account_email, account_password, account_type) values (5, N'testStaff5', N'teststaff5@fpt.edu.vn', CONVERT(NVARCHAR(32), HashBytes('MD5', '123456'), 2), 'staff');
insert into Account(staff_id, account_username, account_email, account_password, account_type) values (6, N'testStaff6', N'teststaff6@fpt.edu.vn', CONVERT(NVARCHAR(32), HashBytes('MD5', '123456'), 2), 'staff');

-- Insert test promotion manager account
insert into PromotionManager (pro_fullname) values (N'Test Promotion Manager Một');
insert into PromotionManager (pro_fullname) values (N'Test Promotion Manager Hai');
insert into PromotionManager (pro_fullname) values (N'Test Promotion Manager Ba');
insert into PromotionManager (pro_fullname) values (N'Test Promotion Manager Bốn');
insert into PromotionManager (pro_fullname) values (N'Test Promotion Manager Năm');
insert into PromotionManager (pro_fullname) values (N'Test Promotion Manager Sáu');
-- Promotion managers' account ID starts from 41-50
dbcc checkident (Account, RESEED, 100);
-- Insert Promotion Manager Account
insert into Account(pro_id, account_username, account_email, account_password, account_type) values (1, N'testPromotion1', N'testPromotion1@fpt.edu.vn', CONVERT(NVARCHAR(32), HashBytes('MD5', '123456'), 2), 'promotionManager');
insert into Account(pro_id, account_username, account_email, account_password, account_type) values (2, N'testPromotion2', N'testPromotion2@fpt.edu.vn', CONVERT(NVARCHAR(32), HashBytes('MD5', '123456'), 2), 'promotionManager');
insert into Account(pro_id, account_username, account_email, account_password, account_type) values (3, N'testPromotion3', N'testPromotion3@fpt.edu.vn', CONVERT(NVARCHAR(32), HashBytes('MD5', '123456'), 2), 'promotionManager');
insert into Account(pro_id, account_username, account_email, account_password, account_type) values (4, N'testPromotion4', N'testPromotion4@fpt.edu.vn', CONVERT(NVARCHAR(32), HashBytes('MD5', '123456'), 2), 'promotionManager');
insert into Account(pro_id, account_username, account_email, account_password, account_type) values (5, N'testPromotion5', N'testPromotion5@fpt.edu.vn', CONVERT(NVARCHAR(32), HashBytes('MD5', '123456'), 2), 'promotionManager');
insert into Account(pro_id, account_username, account_email, account_password, account_type) values (6, N'testPromotion6', N'testPromotion6@fpt.edu.vn', CONVERT(NVARCHAR(32), HashBytes('MD5', '123456'), 2), 'promotionManager');


-- Customer must be added before an associated Account (if exists) can be created
insert into Customer (customer_firstname, customer_lastname, customer_gender, customer_phone, customer_address) values (N'Quốc Anh', N'Nguyễn', N'Nam', '0914875606', N'Đường sô 3, Khu Vực Bình thường B, Bình Thủy, Cần Thơ');
insert into Customer (customer_firstname, customer_lastname, customer_gender, customer_phone, customer_address) values (N'Khắc Huy', N'Huỳnh', N'Nam', '0375270803', N'132/24D, đường 3-2, Ninh Kiều Cần Thơ');
insert into Customer (customer_firstname, customer_lastname, customer_gender, customer_phone, customer_address) values (N'Vũ Như Huỳnh', N'Nguyễn', N'Nữ', '0896621155', N'34, B25, Kdc 91B, An Khánh, Ninh Kiều');
insert into Customer (customer_firstname, customer_lastname, customer_gender, customer_phone, customer_address) values (N'Tiến Thành', N'Hứa', N'Nam', '0912371282', N'39 Mậu Thân, Xuân Khánh, Ninh Kiều, Cần Thơ');
insert into Customer (customer_firstname, customer_lastname, customer_gender, customer_phone, customer_address) values (N'Hoàng Khang', N'Nguyễn', N'Nam', '0387133745', N'110/22/21 Trần Hưng Đạo, Bình Thỷ, Cần thơ');
insert into Customer (customer_firstname, customer_lastname, customer_gender, customer_phone, customer_address) values (N'Duy Khang', N'Huỳnh', N'Nam', '0913992409', N'138/29/21 Trần Hưng Đạo, Ninh Kiều, Cần thơ');
dbcc checkident (Account, RESEED, 200);
-- Insert Customer Account
insert into Account (customer_id, account_username, account_email, account_password, account_type) values (1, N'quocanh123', N'anhnq1130@gmail.com', CONVERT(NVARCHAR(32), HashBytes('MD5', '123456'), 2), 'user');
insert into Account (customer_id, account_username, account_email, account_password, account_type) values (2, N'hkhachuy', N'hkhachuy.dev@gmail.com', CONVERT(NVARCHAR(32), HashBytes('MD5', '123456'), 2), 'user');
insert into Account (customer_id, account_username, account_email, account_password, account_type) values (3, N'rainyvuwritter', N'rainyvuwritter@gmail.com', CONVERT(NVARCHAR(32), HashBytes('MD5', '123456'), 2), 'user');
insert into Account (customer_id, account_username, account_email, account_password, account_type) values (4, N'huatienthanh2003', N'huatienthanh2003@gmail.com', CONVERT(NVARCHAR(32), HashBytes('MD5', '123456'), 2), 'user');
insert into Account (customer_id, account_username, account_email, account_password, account_type) values (5, N'khangnguyen', N'khgammingcraft@gmail.com', CONVERT(NVARCHAR(32), HashBytes('MD5', '123456'), 2), 'user');
insert into Account (customer_id, account_username, account_email, account_password, account_type) values (6, N'hdkhang2112', N'hdkhang2112@gmail.com ', CONVERT(NVARCHAR(32), HashBytes('MD5', '123456'), 2), 'user');

-- Insert Food Types
insert into FoodType (food_type) values (N'Mì và Bún');
insert into FoodType (food_type) values (N'Bánh và Bánh Mì');
insert into FoodType (food_type) values (N'Hải Sản');
insert into FoodType (food_type) values (N'Món Ăn Truyền Thống');
insert into FoodType (food_type) values (N'Món Ăn Châu Á');
insert into FoodType (food_type) values (N'Món Thịt');
insert into FoodType (food_type) values (N'Món ăn nhanh');
insert into FoodType (food_type) values (N'Món ăn nhẹ');
insert into FoodType (food_type) values (N'Món Tráng Miệng');
insert into FoodType (food_type) values (N'Đồ uống');

-- Ensure food_id starts from 1
dbcc checkident (Food, RESEED, 0);
-- Noodle
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url) 
values (1, N'Phở', 40000, 100, 1, 5, 0, 'https://www.allrecipes.com/thmb/SZjdgaXhmkrRNLoOvdxuAktwk3E=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/228443-authentic-pho-DDMFS-4x3-0523f6531ccf4dbeb4b5bde52e007b1e.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url) 
values (1, N'Bún bò Huế', 50000, 40, 1, 5, 5, 'https://th.bing.com/th/id/OIP.SXfegdkWCvC_Hbc3A4eW4wHaE7?pid=ImgDet&rs=1');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url) 
values (1, N'Bún đậu mắm tôm', 45000, 50, 1, 5, 0, 'https://img-global.cpcdn.com/recipes/2c630c584ca9709c/751x532cq70/bun-d%E1%BA%ADu-m%E1%BA%AFm-tom-recipe-main-photo.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url) 
values (1, N'Bún mắm', 40000,70, 0, 5, 30, 'https://th.bing.com/th/id/OIP.3p7EKLDnu_dS3comDM40oQHaE0?pid=ImgDet&rs=1');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url) 
values (1, N'Bún riêu', 40000, 30, 1, 5, 0, 'https://th.bing.com/th/id/R.e41d43c5534281e211ae9a708a2b5517?rik=eec%2bt0%2fAUqttcQ&riu=http%3a%2f%2fseonkyounglongest.com%2fwp-content%2fuploads%2f2018%2f06%2fBun-Rieu-07.jpg&ehk=0iemq%2bdo28ouF67dFC5dFQTUvo%2biGdgK7hx4tsn%2bv%2bc%3d&risl=&pid=ImgRaw&r=0');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url) 
values (1, N'Bún thịt nướng', 35000, 50, 1, 5, 5, 'https://th.bing.com/th/id/R.dd5289abf81592cb720dedc3bf6a598a?rik=L6zDZI0S1g2QyA&pid=ImgRaw&r=0');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url) 
values (1, N'Mì quảng', 50000, 20, 1, 5, 0, 'https://beptruong.edu.vn/wp-content/uploads/2022/10/mi-quang-chay-voi-vi-thanh-dam.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url) 
values (1, N'Ramen', 63000, 100, 1, 5, 0, 'https://www.justonecookbook.com/wp-content/uploads/2023/04/Spicy-Shoyu-Ramen-8055-I.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
values (1, N'Hủ Tiếu', 38000, 30, 1, 5, 10, 'https://vcdn1-giadinh.vnecdn.net/2023/05/15/Bc8Thnhphm18-1684125639-9811-1684125654.jpg?w=1200&h=0&q=100&dpr=1&fit=crop&s=i0OuvKxyIvG-1BRluganjQ');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
values (1, N'Ravioli', 30000, 40, 0, 5, 15, 'https://cdn11.bigcommerce.com/s-cjh14ahqln/product_images/uploaded_images/cheese-ravioli-2-web.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
values (1, N'Spaghetti Bolognese', 58000, 30, 1, 5, 15, 'https://supervalu.ie/image/var/files/real-food/recipes/Uploaded-2020/spaghetti-bolognese-recipe.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
values (1, N'Spaghetti Carbonara', 60000, 20, 1, 5, 5, 'https://static01.nyt.com/images/2021/02/14/dining/carbonara-horizontal/carbonara-horizontal-threeByTwoMediumAt2X-v2.jpg');

-- Banh - Banh Mi
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (2, N'Bánh bèo', 25000, 50, 1, 5, 10, 'https://static.vinwonders.com/production/banh-beo-nha-trang-1.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (2, N'Bánh bột lọc', 25000, 30, 1, 5, 0, 'https://th.bing.com/th/id/OIP.1W7a0ykWZ0Sk8ohsGHZk0QHaE8?pid=ImgDet&rs=1');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (2, N'Bánh căn', 35000, 30, 1, 5, 15, 'https://cdn.vatgia.com/pictures/thumb/0x0/2021/03/1616756570-lwn.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (2, N'Bánh canh', 34000, 30, 1, 5, 0, 'https://th.bing.com/th/id/OIG.UJ7hyP4iO5Y6Cqo7lU5N?pid=ImgGn');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (2, N'Bánh chưng', 40000, 10, 0, 5, 20, 'https://www.cet.edu.vn/wp-content/uploads/2020/01/banh-chung.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (2, N'Bánh cuốn', 20000, 20, 1, 5, 0, 'https://i.ytimg.com/vi/vR18wfdLtJE/maxresdefault.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (2, N'Bánh đúc', 34000, 10, 1, 5, 5, 'https://i.ytimg.com/vi/-he2nZsGghA/maxresdefault.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (2, N'Bánh giò', 38000, 20, 1, 5, 0, 'https://th.bing.com/th/id/R.b63b36f87a42ca49b9c3cf9cdbb98dd6?rik=W7iOEdgJi7vMhA&pid=ImgRaw&r=0');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)   
VALUES (2, N'Bánh khọt', 40000, 0, 0, 5, 30, 'https://th.bing.com/th/id/R.3710363219d01194ae36bb770a07ad3f?rik=L2f9%2bVTEPN9j9w&riu=http%3a%2f%2ffoodisafourletterword.com%2fwp-content%2fuploads%2f2020%2f12%2fVietnamese_Crispy_Savory_Shrimp_Pancakes_Recipe_Banh_Khot_top.jpg&ehk=XCD8GsPPzpMrhvD6HySuocNVMJ4fXCObJXtrs7Bde0c%3d&risl=&pid=ImgRaw&r=0');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (2, N'Bánh mì', 20000, 30, 1, 5, 0, 'https://th.bing.com/th/id/R.9bd3f3d87a4571fe7a6300f26941058b?rik=ZmuwBpVNPPYIwQ&pid=ImgRaw&r=0');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (2, N'Bánh pía', 60000, 20, 1, 5, 0, 'https://media.urbanistnetwork.com/saigoneer/article-images/2018/09/Sep17/lapia/BanhPia1b.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (2, N'Bánh tét', 66000, 10, 1, 5, 15, 'https://img4.thuthuatphanmem.vn/uploads/2019/12/16/anh-dep-nhat-ve-banh-chung-truyen-thong-cua-nguoi-dan-toc_023616788.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (2, N'Bánh tráng nướng', 15000, 20, 1, 5, 0, 'https://bizweb.dktcdn.net/100/393/897/files/banh-trang-nuong-bao-nhieu-calo.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (2, N'Bánh xèo', 30000, 30, 0, 5, 0, 'https://th.bing.com/th/id/R.517c2ff96732c3950f8e95a673c01f09?rik=%2fqn6R%2fZgswIjyg&pid=ImgRaw&r=0');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (2, N'Carrot Cake', 48000, 20, 1, 5, 20, 'https://images.squarespace-cdn.com/content/v1/5d7a597d2459d4207ae1a00a/1575826400580-43ATBE70CA4J66XS53T7/carrot_cake-3.jpg?format=1000w');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (2, N'French Toast', 45000, 40, 1, 5, 0, 'https://www.simplyrecipes.com/thmb/b48moNCTtaUYEc1Qyxhe9V66XKc=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/Simply-Recipes-French-Toast-Lead-Shot-3b-c3a68a576a9548f5bd43cce3d2d7f4b7.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (2, N'Garlic Bread', 58000, 30, 1, 5, 15, 'https://www.sorrentina.in/cdn/shop/articles/Garlic_Bread.webp?v=1684226216');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (2, N'Lasagna', 45000, 30, 1, 5, 0, 'https://static01.nyt.com/images/2023/08/31/multimedia/RS-Lasagna-hkjl/RS-Lasagna-hkjl-superJumbo.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (2, N'Pancakes', 40000, 20, 1, 5, 0, 'https://www.allrecipes.com/thmb/WqWggh6NwG-r8PoeA3OfW908FUY=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/21014-Good-old-Fashioned-Pancakes-mfs_001-1fa26bcdedc345f182537d95b6cf92d8.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (2, N'Panna Cotta', 25000, 35, 0, 5, 20, 'https://biancazapatka.com/wp-content/uploads/2022/12/panna-cotta-720x1008.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (2, N'Red Velvet Cake', 44000, 20, 1, 5, 0, 'https://www.allrecipes.com/thmb/gDJ1S6ETLfWGyyWw_4A_IGhvDYE=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/9295_red-velvet-cake_ddmfs_4x3_1129-a8ab17b825e3464a9a53ceeda54ff461.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (2, N'Strawberry Shortcake', 70000, 28, 1, 5, 5, 'https://th.bing.com/th/id/OIG.3zJBFoevyz04wpkIIDZx?pid=ImgGn');

-- Sea Food
-- Chèn các món ăn vào cơ sở dữ liệu với giá ngẫu nhiên tương ứng với thị trường ở Việt Nam
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (3, N'Cá kho tộ', 80000, 10, 1, 5, 0, 'https://bepmina.vn/wp-content/uploads/2023/07/cach-lam-ca-ba-sa-kho-to.jpeg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (3, N'Sashimi', 120000, 20, 1, 5, 0, 'https://images.immediate.co.uk/production/volatile/sites/30/2020/02/sashimi-c123df7.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)   
VALUES (3, N'Scallops', 150000, 10, 0, 5, 15, 'https://www.onceuponachef.com/images/2022/03/how-to-cook-scallops-2-scaled.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (3, N'Seaweed Salad', 50000, 20, 1, 5, 20, 'https://valuemartgrocery.com/cdn/shop/products/Seaweed-Salad-Reshoot-6-scaledsquare_1024x1024.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (3, N'Shrimp and Grits', 90000, 30, 1, 5, 0, 'https://www.bowlofdelicious.com/wp-content/uploads/2018/08/Easy-Classic-Shrimp-and-Grits-square.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (3, N'Lobster Bisque', 130000, 5, 0, 5, 0, 'https://cafedelites.com/wp-content/uploads/2020/02/Lobster-Bisque-IMAGE-1jpg.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (3, N'Lobster Roll Sandwich', 140000, 20, 1, 5, 30, 'https://www.eatingwell.com/thmb/ZrNy9pvrIiCo_PVC5G6EH-jlP28=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/lobster-roll-ck-226594-4x3-1-b3aea3b5cd3e46b6820e2ca6a5c7b310.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)   
VALUES (3, N'Ceviche', 70000, 20, 1, 5, 0, 'https://hips.hearstapps.com/hmg-prod/images/ceviche-index-64887642e188d.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (3, N'Crab Cakes', 100000, 30, 1, 5, 20, 'https://hips.hearstapps.com/hmg-prod/images/crab-cakes-index-64e7cee7d4dda.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (3, N'Grilled Salmon', 110000, 3, 0, 5, 0, 'https://www.acouplecooks.com/wp-content/uploads/2020/05/Grilled-Salmon-015-1.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (3, N'Mussels', 60000, 20, 1, 5, 0, 'https://www.healthyseasonalrecipes.com/wp-content/uploads/2023/01/simple-steamed-mussels-with-garlic-sq-041.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (3, N'Oysters', 90000, 50, 1, 5, 0, 'https://static.emerils.com/grilled%20oysters.jpeg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (3, N'Tuna Tartare', 120000, 30, 1, 5, 15, 'https://pinchandswirl.com/wp-content/uploads/2022/12/Tuna-Tartare-sq.jpg');


-- Mon an truyen thong
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (4, N'Cao lầu', 55000, 20, 1, 5, 0, 'https://img-global.cpcdn.com/recipes/2940d93145814c54/680x482cq70/cao-l%E1%BA%A7u-h%E1%BB%99i-an-fake-recipe-main-photo.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (4, N'Cháo lòng', 40000, 15, 0, 5, 20, 'https://diadiemlongkhanh.cdn.vccloud.vn/static/images/2022/06/08/ee1023df-7261-4b67-b939-9aef34e0d33e-image.jpeg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (4, N'Com tấm', 30000, 50, 1, 5, 0, 'https://i.ytimg.com/vi/6luZIIX5yCM/maxresdefault.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (4, N'Gỏi cuốn', 35000, 30, 1, 5, 15, 'https://www.cet.edu.vn/wp-content/uploads/2018/11/goi-cuon-tom-thit.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (4, N'Nem chua', 25000, 20, 1, 5, 0, 'https://statics.vinpearl.com/nem-chua-1_1628326267.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)   
VALUES (4, N'Xôi xéo', 20000, 30, 1, 5, 30, 'https://statics.vinpearl.com/xoi-xeo-01%20(2)_1632322118.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (4, N'Canh Chua', 35000, 10, 1, 5, 20, 'https://i-giadinh.vnecdn.net/2023/04/25/Thanh-pham-1-1-7239-1682395675.jpg');


-- Mon an chau a
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (5, N'Pad Thai', 60000, 30, 1, 5, 15, 'https://static01.nyt.com/images/2022/03/23/dining/17padthairex1/merlin_203116326_32624565-ffae-482d-9a55-043cf31afb0b-mediumSquareAt3X.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (5, N'Takoyaki', 55000, 28, 0, 5, 0, 'https://upload.wikimedia.org/wikipedia/commons/thumb/c/cb/Takoyaki.jpg/640px-Takoyaki.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (5, N'Bibimbap', 65000, 35, 1, 5, 0, 'https://upload.wikimedia.org/wikipedia/commons/4/44/Dolsot-bibimbap.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (5, N'Breakfast Burrito', 50000, 20, 1, 5, 5, 'https://hips.hearstapps.com/hmg-prod/images/delish-breakfast-burrito-horizontaljpg-1541624805.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (5, N'Chicken Curry', 70000, 30, 1, 5, 0, 'https://www.foodandwine.com/thmb/8YAIANQTZnGpVWj2XgY0dYH1V4I=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/spicy-chicken-curry-FT-RECIPE0321-58f84fdf7b484e7f86894203eb7834e7.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (5, N'Fried Rice', 48000, 35, 1, 5, 30, 'https://www.seriouseats.com/thmb/BJjCEDw9OZe95hpZxmNcD3rJnHo=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/20230529-SEA-EggFriedRice-AmandaSuarez-hero-c8d95fbf69314b318bc279159f582882.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (5, N'Gyoza', 55000, 30, 1, 5, 0, 'https://assets.epicurious.com/photos/628ba0d3fa016bab2139efa2/1:1/w_4546');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (5, N'Peking Duck', 90000, 10, 1, 5, 15, 'https://cdn.i-scmp.com/sites/default/files/images/methode/2018/11/21/b681f60a-eca4-11e8-b0fe-c62dccd2d711_image_hires_192253.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (5, N'Samosa', 35000, 3, 0, 5, 0, 'https://www.indianhealthyrecipes.com/wp-content/uploads/2021/12/samosa-recipe.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (5, N'Spring Rolls', 38000, 30, 1, 5, 15, 'https://jackpurcellmeats.com.au/wp-content/uploads/2017/07/spring-rolls-01.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (5, N'Sushi', 70000, 25, 1, 5, 0, 'https://media.post.rvohealth.io/wp-content/uploads/2021/09/sushi-sashimi-732x549-thumbnail-732x549.jpg');

-- Mon Thit
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (6, N'Beef Carpaccio', 65000, 25, 1, 5, 5, 'https://www.seriouseats.com/thmb/VgVaMsNZog6fWU79Ottu-iw3CLc=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/SEA-italian-easy-beef-carpaccio-recipe-hero-01-e4153bc58c19429085be0e525888a37a.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (6, N'Beef Tartare', 70000, 20, 1, 5, 0, 'https://upload.wikimedia.org/wikipedia/commons/d/db/Classic_steak_tartare.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (6, N'Chicken Quesadilla', 48000, 30, 1, 5, 15, 'https://hips.hearstapps.com/hmg-prod/images/chicken-quesadilla-index-64515c8e98e28.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (6, N'Chicken Wings', 55000, 5, 1, 5, 0, 'https://images.immediate.co.uk/production/volatile/sites/30/2020/12/Air-Fryer-Chicken-Wings-d2c6fa4.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (6, N'Pork Chop', 60000, 20, 1, 5, 20, 'https://hips.hearstapps.com/del.h-cdn.co/assets/18/11/1600x1200/sd-aspect-1520972774-pork-chops-horizontal.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)   
VALUES (6, N'Prime Rib', 80000, 30, 1, 5, 0, 'https://www.kingsford.com/wp-content/uploads/2023/05/PrimeRib-8_cc1_00000000_72-Desktop.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (6, N'Pulled Pork Sandwich', 55000, 20, 1, 5, 30, 'https://www.simplyrecipes.com/thmb/xl-Bn-Y1HByU2ZfYZlaoHRVm0MQ=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/Simply-Recipes-Pulled-Pork-Sandwich-Lead-3b_vertical-94ec18fb8f264514b09a408359f5d7e8.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (6, N'Steak', 70000, 15, 1, 5, 0, 'https://www.skinnytaste.com/wp-content/uploads/2022/03/Air-Fryer-Steak-6.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (6, N'Baby Back Ribs', 75000, 20, 1, 5, 10, 'https://www.southernliving.com/thmb/sQ3jAjFAP-SPt_upe-Im4rxMKrQ=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/oven-baked-baby-back-ribs-beauty-332_preview-34579f7f15ed4548ae3bb5b2048aab60.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (6, N'Filet Mignon', 90000, 20, 1, 5, 15, 'https://i2.wp.com/www.downshiftology.com/wp-content/uploads/2023/02/Filet-Mignon-main.jpg');

-- Mon an nhanh
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (7, N'French Fries', 25000, 30, 1, 5, 10, 'https://static.toiimg.com/thumb/54659021.cms?imgsize=275086&width=800&height=800');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (7, N'Hot Dog', 30000, 20, 1, 5, 0, 'https://i.redd.it/uke6xn3ji5071.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (7, N'Nachos', 35000, 30, 1, 5, 0, 'https://www.simplyrecipes.com/thmb/xTCx1mKCjjPYgGasys_JGafuem0=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/__opt__aboutcom__coeus__resources__content_migration__simply_recipes__uploads__2019__04__Nachos-LEAD-3-e6dd6cbb61474c9889e1524b3796601e.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (7, N'Pizza', 40000, 30, 1, 5, 15, 'https://www.allrecipes.com/thmb/fFW1o307WSqFFYQ3-QXYVpnFj6E=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/48727-Mikes-homemade-pizza-DDMFS-beauty-4x3-BG-2974-a7a9842c14e34ca699f3b7d7143256cf.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (7, N'Dumplings', 35000, 20, 1, 5, 0, 'https://www.bhg.com/thmb/eQgTJ-Bl7DUSNIVQvfntHP3ZVOM=/2000x0/filters:no_upscale():strip_icc()/bhg-pork-and-shitake-steamed-dumplings-FmOg5-5J4gv94CccQYTVph-0ef0a4a8987244759154f9e5e1b1819e.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (7, N'Fish and Chips', 45000, 30, 1, 5, 5, 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Fish_and_chips_blackpool.jpg/800px-Fish_and_chips_blackpool.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (7, N'Fried Calamari', 38000, 20, 1, 5, 0, 'https://www.seriouseats.com/thmb/RLHQFr_lp9-HTIWBikzVwu4M17s=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/__opt__aboutcom__coeus__resources__content_migration__serious_eats__seriouseats.com__2020__11__20201125-fried-calamari-vicky-wasik-10-9cee3a081e96476b89e29b331d30be61.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (7, N'Gnocchi', 32000, 3, 0, 5, 0, 'https://www.marthastewart.com/thmb/AdbLwcdFLpcsvW1bah2OuLij55o=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/336461-gnocchi-with-tomato-sauce-hero-04-1fe29843b76a4f0ab2ebc226de2723a0.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (7, N'Hamburger', 30000, 20, 1, 5, 10, 'https://www.washingtonpost.com/wp-apps/imrs.php?src=https://arc-anglerfish-washpost-prod-washpost.s3.amazonaws.com/public/M6HASPARCZHYNN4XTUYT7H6PTE.jpg&w=1440');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (7, N'Huevos Rancheros', 35000, 30, 1, 5, 0, 'https://i0.wp.com/www.aspicyperspective.com/wp-content/uploads/2018/04/best-huevos-rancheros-recipe-25.jpg?resize=800%2C675&ssl=1');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (7, N'Tacos', 38000, 50, 1, 5, 15, 'https://upload.wikimedia.org/wikipedia/commons/thumb/7/73/001_Tacos_de_carnitas%2C_carne_asada_y_al_pastor.jpg/1200px-001_Tacos_de_carnitas%2C_carne_asada_y_al_pastor.jpg');

-- Mon an nhe
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (8, N'Caprese Salad', 35000, 20, 1, 5, 0, 'https://i2.wp.com/www.downshiftology.com/wp-content/uploads/2019/07/Caprese-Salad-main-1.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (8, N'Greek Salad', 32000, 30, 1, 5, 10, 'https://cdn.loveandlemons.com/wp-content/uploads/2019/07/greek-salad-2.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (8, N'Guacamole', 28000, 35, 1, 5, 0, 'https://www.giallozafferano.com/images/255-25549/Guacamole_1200x800.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (8, N'Hummus', 25000, 3, 0, 5, 0, 'https://i2.wp.com/www.downshiftology.com/wp-content/uploads/2022/08/Hummus-main-1.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (8, N'Caesar Salad', 30000, 50, 1, 5, 10, 'https://cdn.loveandlemons.com/wp-content/uploads/2019/12/caesar-salad-recipe.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (8, N'Risotto', 38000, 40, 1, 5, 0, 'https://www.allrecipes.com/thmb/854efwMYEwilYjKr0FiF4FkwBvM=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/85389-gourmet-mushroom-risotto-DDMFS-4x3-a8a80a8deb064c6a8f15452b808a0258.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (8, N'Omelette', 26000, 30, 1, 5, 0, 'https://realfood.tesco.com/media/images/1400x919-Tesco-5For15-13273-RainbowOmelette-b3f0c3cc-2f15-40a7-98b1-07af0609f99e-0-1400x919.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (8, N'Onion Rings', 22000, 20, 1, 5, 15, 'https://th.bing.com/th/id/OIG.tUsUxbWy2qtAgqLtbaxx?pid=ImgGn');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (8, N'Poutine', 25000, 30, 1, 5, 0, 'https://www.seasonsandsuppers.ca/wp-content/uploads/2014/01/new-poutine-1.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (8, N'Club Sandwich', 35000, 25, 1, 5, 0, 'https://www.foodandwine.com/thmb/tM060YA0Fd0UALCmPQ-5gGWyBqA=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/Classic-Club-Sandwich-FT-RECIPE0523-99327c9c87214026b9419b949ee13a9c.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (8, N'Beet Salad', 28000, 30, 1, 5, 5, 'https://cdn.loveandlemons.com/wp-content/uploads/2021/11/beet-salad-1.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (8, N'Cheese Plate', 32000, 50, 0, 5, 5, 'https://www.barleyandsage.com/wp-content/uploads/2021/08/summer-cheeseboard-1200x1200-1.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (8, N'Clam Chowder', 26000, 10, 1, 5, 0, 'https://s23209.pcdn.co/wp-content/uploads/2019/10/Easy-Clam-ChowderIMG_1064.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (8, N'Croque Madame', 32000, 20, 0, 5, 5, 'https://hips.hearstapps.com/hmg-prod/images/190417-croque-monsieur-horizontal-476-1556565130.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (8, N'Edamame', 18000, 30, 1, 5, 0, 'https://peasandcrayons.com/wp-content/uploads/2018/02/quick-easy-spicy-sambal-edamame-recipe-2.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (8, N'Eggs Benedict', 32000, 20, 1, 5, 15, 'https://www.foodandwine.com/thmb/j6Ak6jECu0fdly1XFHsp4zZM8gQ=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/Eggs-Benedict-FT-RECIPE0123-4f5f2f2544464dc89a667b5d960603b4.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (8, N'Foie Gras', 75000, 15, 1, 5, 0, 'https://upload.wikimedia.org/wikipedia/commons/8/82/Foie_gras_en_cocotte.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (8, N'Grilled Cheese Sandwich', 25000, 20, 1, 5, 20, 'https://static01.nyt.com/images/2023/02/28/multimedia/ep-air-fryer-grilled-cheese-vpmf/ep-air-fryer-grilled-cheese-vpmf-mediumSquareAt3X.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (8, N'Ice Cream', 15000, 50, 0, 5, 0, 'https://images.unsplash.com/photo-1497034825429-c343d7c6a68f?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8aWNlX2NyZWFtfGVufDB8fDB8fHww&w=1000&q=80');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (8, N'Paella', 42000, 10, 1, 5, 0, 'https://www.allrecipes.com/thmb/PdwNPwZiNXr9cw8W6WQacCl6i98=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/84137-easy-paella-DDMFS-4x3-08712e61e7dc453d94673f65f9eca7d2.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (8, N'Waffles', 18000, 20, 0, 5, 15, 'https://www.allrecipes.com/thmb/imrP1HYi5pu7j1en1_TI-Kcnzt4=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/20513-classic-waffles-mfs-025-4x3-81c0f0ace44d480ca69dd5f2c949731a.jpg');

-- Mon trang mieng
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (9, N'Apple Pie', 45000, 20, 1, 5, 10, 'https://www.inspiredtaste.net/wp-content/uploads/2022/11/Apple-Pie-Recipe-Video.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (9, N'Baklava', 55000, 3, 0, 5, 0, 'https://cleobuttera.com/wp-content/uploads/2018/03/lifted-baklava-720x540.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (9, N'Beignets', 40000, 30, 1, 5, 0, 'https://hips.hearstapps.com/hmg-prod/images/beignets-1656591291.png');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (9, N'Bread Pudding', 38000, 25, 0, 5, 10, 'https://www.livewellbakeoften.com/wp-content/uploads/2020/10/Bread-Pudding-10s-new.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (9, N'Cannoli', 50000, 25, 1, 5, 0, 'https://i0.wp.com/memoriediangelina.com/wp-content/uploads/2023/01/Cannoli-2.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (9, N'Cheesecake', 48000, 30, 1, 5, 0, 'https://www.allrecipes.com/thmb/DHosjm3NundSDP1q6wWEEECYwr8=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/8419-easy-sour-cream-cheesecake-DDMFS-beauty-4x3-BG-2747-44b427d330aa41aa876269b1249698a0.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (9, N'Chocolate Cake', 46000, 20, 0, 5, 5, 'https://ichef.bbci.co.uk/food/ic/food_16x9_832/recipes/easy_chocolate_cake_31070_16x9.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (9, N'Chocolate Mousse', 42000, 50, 1, 5, 0, 'https://bakerbynature.com/wp-content/uploads/2023/08/Easy-Chocolate-Mousse-Baker-by-Nature-12617-1.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (9, N'Churros', 35000, 40, 1, 5, 0, 'https://www.recipetineats.com/wp-content/uploads/2016/08/Churros_9-SQ.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (9, N'Creme Brulee', 48000, 30, 1, 5, 10, 'https://bellyfull.net/wp-content/uploads/2023/05/Creme-Brulee-blog-1.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (9, N'Cupcakes', 40000, 50, 0, 5, 0, 'https://www.bhg.com/thmb/iL-5Q6gGjmXkxCKqEovughTLQAo=/3000x0/filters:no_upscale():strip_icc()/how-to-bake-how-to-make-cupcakes-hero-01-12c03f3eff374d569b0565bff7d9e597.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (9, N'Donuts', 35000, 1, 20, 5, 0, 'https://cdn.britannica.com/38/230838-050-D0173E79/doughnuts-donuts.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (9, N'Macarons', 42000, 50, 1, 5, 0, 'https://www.southernliving.com/thmb/dnsycw_-mf35yKRkq3cBsThVzrY=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/Southern-Living_Macarons_025-0e05e0cd226d44609f55ed8bc9cd3a3e.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (9, N'Tiramisu', 49000, 30, 1, 5, 5, 'https://static01.nyt.com/images/2017/04/05/dining/05COOKING-TIRAMISU1/05COOKING-TIRAMISU1-threeByTwoMediumAt2X-v2.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (9, N'Bruschetta', 35000, 50, 0, 5, 0, 'https://www.cookingclassy.com/wp-content/uploads/2019/07/bruschetta-2.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (9, N'Deviled Eggs', 32000, 50, 1, 5, 0, 'https://static01.nyt.com/images/2021/10/15/dining/aw-classic-deviled-eggs/aw-classic-deviled-eggs-articleLarge.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (9, N'Escargots', 55000, 30, 1, 5, 5, 'https://legallyhealthyblonde.com/wp-content/uploads/2022/09/escargot-featured.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (9, N'Falafel', 38000, 50, 0, 5, 0, 'https://i2.wp.com/www.downshiftology.com/wp-content/uploads/2019/07/Falafel-7.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (9, N'French Onion Soup', 42000, 30, 1, 5, 10, 'https://www.onceuponachef.com/images/2019/02/french-onion-soup-1.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (9, N'Frozen Yogurt', 30000, 30, 1, 5, 0, 'https://th.bing.com/th/id/OIG.LFpbBY20Lcd.8a_h1B3J?pid=ImgGn');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (9, N'Hot and Sour Soup', 36000, 20, 1, 5, 0, 'https://cookwithdana.com/wp-content/uploads/2021/11/IMG_2866-scaled.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (9, N'Macaroni and Cheese', 32000, 9, 1, 5, 15, 'https://upload.wikimedia.org/wikipedia/commons/4/44/Original_Mac_n_Cheese_.jpg');
insert into Food (food_type_id, food_name, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
VALUES (9, N'Miso Soup', 35000, 5, 0, 5, 10, 'https://upload.wikimedia.org/wikipedia/commons/thumb/e/e8/Miso_Soup_001.jpg/1200px-Miso_Soup_001.jpg');

-- Drinks
insert into Food (food_type_id, food_name, food_description, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
values (10, N'Coca-Cola (330ml)', N'Coca-Cola là một loại nước ngọt có ga phổ biến trên toàn thế giới. Với hương vị đặc trưng, ngọt ngào và sảy ngon, Coca-Cola thường được phục vụ lạnh và kèm theo đá.', 12000, 500, 1,5, 0, 'https://drive.google.com/uc?id=14_GTRoOdBs5zuU4VwnzftPsqtdEeW1xP');
insert into Food (food_type_id, food_name, food_description, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
values (10, N'7 Up (330ml)', N'7 Up là một loại nước ngọt có ga với hương vị chanh mát lạnh. Được sản xuất từ các thành phần tự nhiên, 7 Up thường là lựa chọn tuyệt vời để giải khát trong những ngày nắng nóng.', 12000, 200, 1,5, 0, 'https://drive.google.com/uc?id=1HxLmW1x2aNiZBmKYGps8hXksinGIl7rv');
insert into Food (food_type_id, food_name, food_description, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
values (10, N'Trà đào', N'Trà Đào là một loại trà trái cây ngon mát, được làm từ trà đen pha chế cùng với hương vị tự nhiên và ngọt ngọt của đào. Một lựa chọn tuyệt vời để giải nhiệt và thư giãn trong ngày nắng nóng.', 20000, 300, 1,4, 0, 'https://drive.google.com/uc?id=1RgBDS_wk1SmUfxMujGQSYMl-uOy8qob3');
insert into Food (food_type_id, food_name, food_description, food_price, food_limit, food_status, food_rate, discount_percent, food_img_url)  
values (10, N'Trà chanh dây', N'Trà Chanh Dây là một loại trà trái cây tươi ngon, được làm từ trà xanh pha chế cùng với hương vị chua chua ngọt ngọt của chanh dây. Một lựa chọn sức khỏe và thưởng thức tuyệt vời cho mọi ngày.', 20000, 200, 1, 5, 0, 'https://drive.google.com/uc?id=1Cj22ZH1a79m9zz4oKmB-ZhMZuxd24qCx');

-- Payment methods
insert into PaymentMethod (payment_method) values (N'Thẻ tín dụng');
insert into PaymentMethod (payment_method) values (N'Thẻ ghi nợ');
insert into PaymentMethod (payment_method) values (N'COD');

-- Order statuses
insert into OrderStatus (order_status) values (N'Chờ xác nhận');
insert into OrderStatus (order_status) values (N'Đang chuẩn bị món');
insert into OrderStatus (order_status) values (N'Đang giao');
insert into OrderStatus (order_status) values (N'Đã giao');
insert into OrderStatus (order_status) values (N'Đã hủy');

-- Voucher
insert into Voucher (voucher_name, voucher_code, voucher_discount_percent, voucher_quantity, voucher_status, voucher_date) values ( N'Quốc tế phụ nữ', 'ADASD2FD23123DBE', 30, 15, 0,'20231021 00:01:00 AM' );
insert into Voucher (voucher_name, voucher_code, voucher_discount_percent, voucher_quantity, voucher_status, voucher_date) values ( N'Khách hàng may mắn', 'BD2128BDYOQM87V7', 20, 10, 0,'20230809 00:01:00 AM');
insert into Voucher (voucher_name, voucher_code, voucher_discount_percent, voucher_quantity, voucher_status, voucher_date) values ( N'Halloween cùng FFood', 'XDEF39O9YOQM8PPV', 15, 20, 0,'20231101 00:01:00 AM');
insert into Voucher (voucher_name, voucher_code, voucher_discount_percent, voucher_quantity, voucher_status, voucher_date) values ( N'Người đặc biệt', 'DJWOA975N4B92BH6', 50, 3, 1,'20231112 00:01:00 AM' );
insert into Voucher (voucher_name, voucher_code, voucher_discount_percent, voucher_quantity, voucher_status, voucher_date) values ( N'Ngày Nhà giáo Việt Nam', '9JADYEDYOQM8E7OA', 15, 10, 0,'20231121 00:01:00 AM');
insert into Voucher (voucher_name, voucher_code, voucher_discount_percent, voucher_quantity, voucher_status, voucher_date) values (N'Quà tặng Noel', 'DUEMAHWOPUNH62GH', 20, 10, 1,'20231223 00:01:00 AM' );

-- Cart, CartItem, Order test data
insert into Cart (customer_id) values (1);

insert into CartItem (cart_id, food_id, food_price, food_quantity) values (1, 2, 50000, 2);
insert into CartItem (cart_id, food_id, food_price, food_quantity) values (1, 10, 30000, 1);
insert into CartItem (cart_id, food_id, food_price, food_quantity) values (1, 23, 20000, 3);

-- Insert an Order for the Cart
insert into [Order] (
cart_id, customer_id ,order_status_id, payment_method_id,
contact_phone, delivery_address, order_time, order_total, 
order_note, delivery_time, order_cancel_time
) values (
1, 1, 4, 1, 
'0931278397', N'39 Mậu Thân, Ninh Kiều, Cần Thơ', '20231108 10:49:00 AM', 190000, 
NULL, '20231108 10:49:00 AM', NULL);

insert into Payment (
    order_id, payment_method_id, payment_total, payment_content, payment_bank, payment_code, payment_status, payment_time
) values (
    1,1,190000,N'Thanh toán đơn hàng ffood',N'NCB','14111641',1,'20231108 11:20:00 AM'
);

update Account set lastime_order = '20231108 10:34:00 AM' where account_id = 201

insert into OrderLog (order_id, staff_id, log_activity, log_time) values (1, 1, N'Cập nhật thông tin đơn hàngg','20231108 10:51:00 AM');
insert into OrderLog (order_id, staff_id, log_activity, log_time) values (1, 1, N'Cập nhật trạng thái đơn hàng','20231108 11:03:00 AM');
insert into OrderLog (order_id, staff_id, log_activity, log_time) values (1, 2, N'Cập nhật trạng thái đơn hàng','20231108 11:18:00 AM');
insert into OrderLog (order_id, staff_id, log_activity, log_time) values (1, 3, N'Cập nhật trạng thái đơn hàng','20231108 11:20:00 AM');

-- Cart, CartItem, Order test data
insert into Cart (customer_id) values (2);

insert into CartItem (cart_id, food_id, food_price, food_quantity) values (2, 5, 40000, 2);
insert into CartItem (cart_id, food_id, food_price, food_quantity) values (2, 14, 25000, 3);
insert into CartItem (cart_id, food_id, food_price, food_quantity) values (2, 23, 20000, 3);

-- Insert an Order for the Cart
insert into [Order] (
cart_id, customer_id ,order_status_id, payment_method_id,
contact_phone, delivery_address, order_time, order_total, 
order_note, delivery_time, order_cancel_time
) values (
2, 5, 4, 3, 
'0931278397', N'39 Mậu Thân, Ninh Kiều, Cần Thơ', '20231108 15:43:00 PM', 215000, 
NULL, '20231108 15:43:00 PM', NULL);

update Account set lastime_order = '20231108 15:43:00 PM' where account_id = 205

insert into OrderLog (order_id, staff_id, log_activity, log_time) values (2, 1, N'Cập nhật trạng thái đơn hàng','20231108 15:50:00 PM');
insert into OrderLog (order_id, staff_id, log_activity, log_time) values (2, 2, N'Cập nhật trạng thái đơn hàng','20231108 16:05:00 PM');
insert into OrderLog (order_id, staff_id, log_activity, log_time) values (2, 3, N'Cập nhật trạng thái đơn hàng','20231108 16:20:00 PM');

