use ffood;

-- Admin must be added before an associated Account can be created
insert into [Admin] (admin_fullname) values (N'Hứa Tiến Thành');
insert into [Admin] (admin_fullname) values (N'Nguyễn Huy Khánh');
insert into [Admin] (admin_fullname) values (N'Phan Thành Quốc Bảo');
insert into [Admin] (admin_fullname) values (N'Trương Mạnh Hưng');
insert into [Admin] (admin_fullname) values (N'Hồ Dương Gia Bảo');

-- Admin passwords = admin# with # range from 1 to 5
-- For example: thanhhtce171454@fpt.edu.vn: password = admin1
insert into Account (admin_id, account_username, account_email, account_password, account_type) values (1, N'Hứa Tiến Thành', N'thanhhtce171454@fpt.edu.vn', 'e00cf25ad42683b3df678c61f42c6bda', 'admin');
insert into Account (admin_id, account_username, account_email, account_password, account_type) values (2, N'Nguyễn Huy Khánh', N'knhce160269@fpt.edu.vn', 'c84258e9c39059a89ab77d846ddab909', 'admin');
insert into Account (admin_id, account_username, account_email, account_password, account_type) values (3, N'Phan Thành Quốc Bảo', N'baoptqce170696@fpt.edu.vn', '32cacb2f994f6b42183a1300d9a3e8d6', 'admin');
insert into Account (admin_id, account_username, account_email, account_password, account_type) values (4, N'Trương Mạnh Hưng', N'hungtmce171981@fpt.edu.vn', 'fc1ebc848e31e0a68e868432225e3c82', 'admin');
insert into Account (admin_id, account_username, account_email, account_password, account_type) values (5, N'Hồ Dương Gia Bảo', N'baohdgce170317@fpt.edu.vn', '26a91342190d515231d7238b0c5438e1', 'admin');

-- Customer must be added before an associated Account (if there is one) can be created
insert into Customer (customer_firstname, customer_lastname, customer_gender, customer_phone, customer_address) values (N'Tét', N'Úer', N'Nam', '0931278397', N'96 Nguyễn Ví Dụ');
insert into Customer (customer_firstname, customer_lastname, customer_gender, customer_phone, customer_address) values (N'Tiến Thành', N'Hứa', N'Nam', '0936953962', N'39 Mậu Thân, Ninh Kiều, Cần Thơ');

-- Account id 1-20 is reserved for admin accounts
-- User accound id starts from 21
-- TestUser password = testuser
-- Hứa Tiến Thành password = user1234
dbcc checkident (Account, RESEED, 20);
insert into Account (customer_id, account_username, account_email, account_password, account_type) values (1, N'TestUser', N'test@gmail.com', '5d9c68c6c50ed3d02a2fcf54f63993b6', 'user');
insert into Account (customer_id, account_username, account_email, account_password, account_type) values (NULL, N'Hứa Tiến Thành', N'huatienthanh2003@gmail.com', 'b5b73fae0d87d8b4e2573105f8fbe7bc', 'user');

-- Food types
insert into FoodType (food_type) values (N'Cơm');
insert into FoodType (food_type) values (N'Mì');
insert into FoodType (food_type) values (N'Bánh mì');
insert into FoodType (food_type) values (N'Đồ ăn vặt');
insert into FoodType (food_type) values (N'Tráng miệng');
insert into FoodType (food_type) values (N'Đồ uống');

-- Ensure food_id starts from 1
dbcc checkident (Food, RESEED, 0);
-- Rice
insert into Food (food_type_id, food_name, food_price, discount_percent, food_img_url) values (1, N'Cơm sườn bì chả', 40000, 0, 'https://drive.google.com/uc?id=171ufFckFyj9GjpMkXowmoQTw6ZcEkjSp');
insert into Food (food_type_id, food_name, food_price, discount_percent, food_img_url) values (1, N'Cơm gà xối mỡ', 55000, 20, 'https://drive.google.com/uc?id=1THlEKsaHjx_CgYpq-2O7gKXONdF1dAgZ');
insert into Food (food_type_id, food_name, food_price, discount_percent, food_img_url) values (1, N'Cơm chiên dương châu', 30000, 10, 'https://drive.google.com/uc?id=1NxzB2r6hV1UXX_uMFIdRU8pb8UYtB8dw');
insert into Food (food_type_id, food_name, food_price, discount_percent, food_img_url) values (1, N'Cơm bò lúc lắc', 45000, 0, 'https://drive.google.com/uc?id=1P-9QLh8MPKJmB9GEctJbYSZrlkXjCWqu');

-- Noodles
insert into Food (food_type_id, food_name, food_price, discount_percent, food_img_url) values (2, N'Mì xào hải sản', 60000, 15, 'https://drive.google.com/uc?id=1b2lndY7jUDPgwV1dMXpx9hAVQZQebvqS');
insert into Food (food_type_id, food_name, food_price, discount_percent, food_img_url) values (2, N'Mì xào bò', 55000, 0, 'https://drive.google.com/uc?id=16blYSeX0hkvED0Fb1dBdSKIVwRzB6_dN');
insert into Food (food_type_id, food_name, food_price, discount_percent, food_img_url) values (2, N'Mì tương đen', 45000, 0, 'https://drive.google.com/uc?id=1u87tcdwWV0-5sYn6RPN208VTZv56UB3u');
insert into Food (food_type_id, food_name, food_price, discount_percent, food_img_url) values (2, N'Mì quảng', 40000, 0, 'https://drive.google.com/uc?id=1gB9FPTZ2hurV4cSZDEvpeQTffGFdGz4g');

-- Banh mi
insert into Food (food_type_id, food_name, food_price, discount_percent, food_img_url) values (3, N'Bánh mì chả cá', 20000, 0, 'https://drive.google.com/uc?id=1KVJXVXir51PjPQu9ZQlyuiQbQrlwKS5D');
insert into Food (food_type_id, food_name, food_price, discount_percent, food_img_url) values (3, N'Bánh mì thịt nguội', 18000, 0, 'https://drive.google.com/uc?id=1RKM-vmALRleJKjg6w2CPNLearOgFdxyy');
insert into Food (food_type_id, food_name, food_price, discount_percent, food_img_url) values (3, N'Bánh mì heo quay', 25000, 0, 'https://drive.google.com/uc?id=1Bd-goOKWwQNjc6M3e6vliZnFSHV4uwlQ');
insert into Food (food_type_id, food_name, food_price, discount_percent, food_img_url) values (3, N'Bánh mì xíu mại', 35000, 50, 'https://drive.google.com/uc?id=1Fgt8Gv9xFnSYl1m-EYgzqj7JPvqM9Tda');

-- Junk food
insert into Food (food_type_id, food_name, food_price, discount_percent, food_img_url) values (4, N'Khoai tây chiên', 15000, 0, 'https://drive.google.com/uc?id=1xpFNpt7GRnW9-Z7i0oMJiHtP1_pXEe04');
insert into Food (food_type_id, food_name, food_price, discount_percent, food_img_url) values (4, N'Mực chiên xù', 20000, 0, 'https://drive.google.com/uc?id=1bH_-gCL1S4WZxQ_VjZZmtXBecUiVEqVb');
insert into Food (food_type_id, food_name, food_price, discount_percent, food_img_url) values (4, N'Gà xiên que', 18000, 0, 'https://drive.google.com/uc?id=1Ty6y9P00G49E_zjsZC_rNaA_8uKUFoav');
insert into Food (food_type_id, food_name, food_price, discount_percent, food_img_url) values (4, N'Xúc xích xiên que', 15000, 0, 'https://drive.google.com/uc?id=1jzKat34QrD7PtT-YsgeDYW5fpca8Xo34');

-- Desserts
insert into Food (food_type_id, food_name, food_price, discount_percent, food_img_url) values (5, N'Bánh flan', 10000, 0, 'https://drive.google.com/uc?id=1d4TVY2jIAtI8PWcu06_FtkP2YitNW0DD');
insert into Food (food_type_id, food_name, food_price, discount_percent, food_img_url) values (5, N'Panna cotta', 15000, 0, 'https://drive.google.com/uc?id=1Is76NMoBErXptJ3dwYK-cKgDgnL0Mwki');
insert into Food (food_type_id, food_name, food_price, discount_percent, food_img_url) values (5, N'Sữa chua dẻo', 15000, 0, 'https://drive.google.com/uc?id=1ivbEwy1KgLkDV3NlUtALGU6vN0SQHEEG');
insert into Food (food_type_id, food_name, food_price, discount_percent, food_img_url) values (5, N'Chè sen nhãn dừa', 18000, 10, 'https://drive.google.com/uc?id=1q7taf4uwKWJLrwttYUBeSuZLk98ytetm');

-- Drinks
insert into Food (food_type_id, food_name, food_price, discount_percent, food_img_url) values (6, N'Coca-Cola (330ml)', 12000, 0, 'https://drive.google.com/uc?id=14_GTRoOdBs5zuU4VwnzftPsqtdEeW1xP');
insert into Food (food_type_id, food_name, food_price, discount_percent, food_img_url) values (6, N'7 Up (330ml)', 12000, 0, 'https://drive.google.com/uc?id=1HxLmW1x2aNiZBmKYGps8hXksinGIl7rv');
insert into Food (food_type_id, food_name, food_price, discount_percent, food_img_url) values (6, N'Trà đào', 20000, 0, 'https://drive.google.com/uc?id=1RgBDS_wk1SmUfxMujGQSYMl-uOy8qob3');
insert into Food (food_type_id, food_name, food_price, discount_percent, food_img_url) values (6, N'Trà chanh dây', 20000, 0, 'https://drive.google.com/uc?id=1Cj22ZH1a79m9zz4oKmB-ZhMZuxd24qCx');

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

-- Cart, CartItem, Order
insert into Cart (customer_id) values (1);

insert into CartItem (cart_id, food_id, food_price, food_quantity) values (1, 2, 55000, 2);
insert into CartItem (cart_id, food_id, food_price, food_quantity) values (1, 23, 20000, 3);

insert into [Order] (
cart_id, customer_id, order_status_id, payment_method_id,
contact_phone, delivery_address, order_time, order_total, 
order_note, delivery_time, order_cancel_time
) values (
1, 1, 4, 1, 
'0931278397', N'39 Mậu Thân, Ninh Kiều, Cần Thơ', '20230708 10:34:09 AM', 170000, 
NULL, '20230708 10:49:35 AM', NULL);