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
INSERT INTO Account (admin_id, account_username, account_email, account_password, account_type) VALUES (1, N'vuhuynh123', N'huynhnvnce170550@fpt.edu.vn', CONVERT(NVARCHAR(32), HashBytes('MD5', '123456'), 2), 'admin');
insert into Account (admin_id, account_username, account_email, account_password, account_type) values (2, N'hoangkhang123', N'khangnhce171197@fpt.edu.vn', CONVERT(NVARCHAR(32), HashBytes('MD5', '123456'), 2), 'admin');
insert into Account (admin_id, account_username, account_email, account_password, account_type) values (3, N'khachuy123', N'huyhkce171229@fpt.edu.vn', CONVERT(NVARCHAR(32), HashBytes('MD5', '123456'), 2), 'admin');
insert into Account (admin_id, account_username, account_email, account_password, account_type) values (4, N'tienthanh123', N'thanhhtce171454@fpt.edu.vn', CONVERT(NVARCHAR(32), HashBytes('MD5', '123456'), 2), 'admin');
insert into Account (admin_id, account_username, account_email, account_password, account_type) values (5, N'quocanh1130', N'anhnqce170483@fpt.edu.vn', CONVERT(NVARCHAR(32), HashBytes('MD5', '123456'), 2), 'admin');
insert into Account (admin_id, account_username, account_email, account_password, account_type) values (6, N'duykhang123', N'khanghdse172647@fpt.edu.vn', CONVERT(NVARCHAR(32), HashBytes('MD5', '123456'), 2), 'admin');

-- Insert test staff account
insert into Staff (staff_fullname) values ('Nguyễn Văn TestStaff');
-- Reset the identity seed for the Account table to 20
dbcc checkident (Account, RESEED, 20);
-- Insert Staff Account
insert into Account (staff_id, account_username, account_email, account_password, account_type) values (1, N'testStaff', N'teststaff@fpt.edu.vn', CONVERT(NVARCHAR(32), HashBytes('MD5', '123456'), 2), 'staff');

-- Insert test promotion manager account
insert into PromotionManager (pro_fullname) values ('Nguyễn Văn TestPromotion');
-- Reset the identity seed for the Account table to 40
dbcc checkident (Account, RESEED, 40);
-- Insert Promotion Manager Account
insert into Account(pro_id, account_username, account_email, account_password, account_type) values (1, N'testPromotion', N'testPromotion@fpt.edu.vn', CONVERT(NVARCHAR(32), HashBytes('MD5', '123456'), 2), 'promotionManager');

-- Insert Customer and associated Account
insert into Customer (customer_firstname, customer_lastname, customer_gender, customer_phone, customer_address) values (N'Quoc Anh', N'Nguyen', N'Nam', '0914875606', N'Đường sô 3, Khu Vực Bình thường B, Bình Thủy, Cần Thơ');
-- Reset the identity seed for the Account table to 50
dbcc checkident (Account, RESEED, 50);
-- Insert Customer Account
insert into Account (customer_id, account_username, account_email, account_password, account_type) values (1, N'quocanh123', N'anhnq1130@gmail.com', CONVERT(NVARCHAR(32), HashBytes('MD5', '123456'), 2), 'user');

-- Insert Food Types
insert into FoodType (food_type) values (N'Cơm');
insert into FoodType (food_type) values (N'Mì');
insert into FoodType (food_type) values (N'Bánh mì');
insert into FoodType (food_type) values (N'Đồ ăn vặt');
insert into FoodType (food_type) values (N'Tráng miệng');
insert into FoodType (food_type) values (N'Đồ uống');

-- Ensure food_id starts from 1
dbcc checkident (Food, RESEED, 0);
-- Rice
insert into Food (food_type_id, food_name, food_description ,food_price, food_status, discount_percent, food_img_url) 
values (1, N'Cơm sườn bì chả',N'Cơm Sườn Bì Chả thường được phục vụ trong một tô hoặc trên một dĩa, với một lượng lớn cơm nguội ở dưới cùng. Thịt sườn nướng, lớp bì xé và lớp chả được sắp xếp một cách tinh tế. Món ăn được trang trí với các loại rau mầm và rau cải tươi ngon, tạo nên sự tương phản màu sắc rất hấp dẫn. Cuối cùng, món ăn được trang trí bằng nước chấm ngon mắt, kết hợp hoàn hảo hương vị của từng thành phần.' , 40000, 1, 0, 'https://drive.google.com/uc?id=171ufFckFyj9GjpMkXowmoQTw6ZcEkjSp');
insert into Food (food_type_id, food_name, food_description ,food_price, food_status, discount_percent, food_img_url) 
values (1, N'Cơm gà xối mỡ',N'Cơm Gà Xối Mỡ là một món ăn phổ biến trong ẩm thực Việt Nam. Món ăn này bao gồm cơm nóng hổi được trải đều trên đĩa, phủ lên trên là lớp gà chiên giòn và thơm béo. Gà được chiên đến khi vỏ giòn và màu vàng óng ả, tạo ra một hương vị đặc trưng và hấp dẫn. Cơm Gà Xối Mỡ thường được kèm theo các loại rau sống như dưa chuột, cà chua, và rau mầm, cùng với nước sốt mắc khén thơm ngon.', 55000, 1, 20, 'https://drive.google.com/uc?id=1THlEKsaHjx_CgYpq-2O7gKXONdF1dAgZ');
insert into Food (food_type_id, food_name, food_description, food_price, food_status, discount_percent, food_img_url) 
values (1, N'Cơm chiên dương châu', N'Cơm Chiên Dương Châu là một món ăn tuyệt vời với hương vị độc đáo và hấp dẫn. Cơm chiên được chế biến cùng với hải sản như tôm, mực và cá, kết hợp với các loại rau cải tươi ngon. Món ăn này thường được chế biến trên lửa lớn để tạo ra lớp cơm giòn và thơm béo, tạo nên hương vị đặc trưng của ẩm thực Đông Á.', 30000, 1, 10, 'https://drive.google.com/uc?id=1NxzB2r6hV1UXX_uMFIdRU8pb8UYtB8dw');
insert into Food (food_type_id, food_name, food_description, food_price, food_status, discount_percent, food_img_url) 
values (1, N'Cơm bò lúc lắc', N'Cơm Bò Lúc Lắc là một món ăn phổ biến trong ẩm thực Việt Nam. Món ăn này bao gồm cơm nóng hổi được kết hợp với thịt bò xào nhanh trên lửa lớn, tạo ra lớp thịt bò giòn và thơm ngon. Thịt bò thường được chiên chín với các loại gia vị, tỏi và ớt, tạo nên hương vị đặc trưng. Món ăn thường được phục vụ kèm theo salad rau sống và nước sốt mắc khén.', 45000, 1, 0, 'https://drive.google.com/uc?id=1P-9QLh8MPKJmB9GEctJbYSZrlkXjCWqu');

-- Noodles
insert into Food (food_type_id, food_name, food_description, food_price, food_status, discount_percent, food_img_url) 
values (2, N'Mì xào hải sản', N'Mì Xào Hải Sản thường được chế biến từ mì dai, ngon mắt, kết hợp với hải sản như tôm, mực, cá và các loại rau sống giòn tươi. Món ăn này thường được xào nhanh trên lửa lớn với các loại gia vị để tạo nên hương vị đậm đà và hấp dẫn.', 60000, 1, 15, 'https://drive.google.com/uc?id=1b2lndY7jUDPgwV1dMXpx9hAVQZQebvqS');
insert into Food (food_type_id, food_name, food_description, food_price, food_status, discount_percent, food_img_url) 
values (2, N'Mì xào bò', N'Mì Xào Bò là một món ăn ngon và nhanh gọn, chế biến từ thịt bò mềm ngon và các loại rau cải tươi ngon. Món ăn này thường được xào nhanh với lửa lớn để giữ được độ giòn của rau và vị thơm của thịt.', 55000, 1, 0, 'https://drive.google.com/uc?id=16blYSeX0hkvED0Fb1dBdSKIVwRzB6_dN');
insert into Food (food_type_id, food_name, food_description, food_price, food_status, discount_percent, food_img_url) 
values (2, N'Mì tương đen', N'Mì Tương Đen là một món ăn phổ biến, chế biến từ mì dai và ngon miệng, kết hợp với tương đen, thịt heo hoặc thịt bò, và các loại rau sống. Món ăn này có hương vị đặc trưng của tương đen và vị ngon của thịt.', 45000, 1, 0, 'https://drive.google.com/uc?id=1u87tcdwWV0-5sYn6RPN208VTZv56UB3u');
insert into Food (food_type_id, food_name, food_description, food_price, food_status, discount_percent, food_img_url) 
values (2, N'Mì quảng', N'Mì Quảng là một món ăn truyền thống của người Việt Nam, với hương vị đặc trưng và đa dạng. Mì mềm thơm, kết hợp với các loại thịt như thịt bò, thịt heo, tôm, mực và các loại rau sống giòn ngon, tạo nên một hương vị độc đáo và hấp dẫn.', 40000, 1, 0, 'https://drive.google.com/uc?id=1gB9FPTZ2hurV4cSZDEvpeQTffGFdGz4g');

-- Banh mi
insert into Food (food_type_id, food_name, food_description, food_price, food_status, discount_percent, food_img_url) 
values (3, N'Bánh mì chả cá', N'Bánh Mì Chả Cá là một món ăn đường phố phổ biến tại Việt Nam. Bánh mì mềm mại và giòn tan được chế biến từ chả cá tươi ngon, kết hợp với các loại rau sống và nước mắc khén, tạo nên hương vị độc đáo và hấp dẫn.', 20000, 1, 0, 'https://drive.google.com/uc?id=1KVJXVXir51PjPQu9ZQlyuiQbQrlwKS5D');
insert into Food (food_type_id, food_name, food_description, food_price, food_status, discount_percent, food_img_url) 
values (3, N'Bánh mì thịt nguội', N'Bánh Mì Thịt Người là một món ăn truyền thống của Việt Nam. Bánh mì mềm mại và thơm ngon được kết hợp với các loại thịt nguội, các loại rau sống và nước sốt, tạo nên một hương vị đặc trưng và hấp dẫn.', 18000, 1, 0, 'https://drive.google.com/uc?id=1RKM-vmALRleJKjg6w2CPNLearOgFdxyy');
insert into Food (food_type_id, food_name, food_description, food_price, food_status, discount_percent, food_img_url) 
values (3, N'Bánh mì heo quay', N'Bánh Mì Heo Quay là một món ăn ngon và phổ biến tại Việt Nam. Bánh mì giòn tan được kết hợp với lớp thịt heo quay giòn và thơm ngon, kèm theo các loại rau sống và nước sốt mắc khén, tạo nên một trải nghiệm ẩm thực tuyệt vời.', 25000, 1, 0, 'https://drive.google.com/uc?id=1Bd-goOKWwQNjc6M3e6vliZnFSHV4uwlQ');
insert into Food (food_type_id, food_name, food_description, food_price, food_status, discount_percent, food_img_url) 
values (3, N'Bánh mì xíu mại', N'Bánh Mì Xíu Mại là một món ăn đường phố hấp dẫn của Việt Nam. Bánh mì mềm mại chứa bên trong những viên xíu mại thơm ngon, kết hợp với các loại rau sống và nước sốt, tạo nên một hương vị truyền thống và thú vị.', 35000, 1, 50, 'https://drive.google.com/uc?id=1Fgt8Gv9xFnSYl1m-EYgzqj7JPvqM9Tda');

-- Junk food
insert into Food (food_type_id, food_name, food_description, food_price, food_status, discount_percent, food_img_url) 
values (4, N'Khoai tây chiên', N'Khoai Tây Chiên là một món ăn nhanh phổ biến được làm từ khoai tây cắt thành sợi mỏng và chiên giòn. Món ăn thường được phục vụ nóng hổi, giòn rụm, và thường được ăn kèm với sốt mayonnaise hoặc sốt ớt.', 15000, 1, 0, 'https://drive.google.com/uc?id=1xpFNpt7GRnW9-Z7i0oMJiHtP1_pXEe04');
insert into Food (food_type_id, food_name, food_description, food_price, food_status, discount_percent, food_img_url) 
values (4, N'Mực chiên xù', N'Mực Chiên Xù là một món ăn biến thái được chế biến từ mực tươi ngon. Mực được phủ lớp bột chiên giòn và thơm phức, tạo nên một trải nghiệm ẩm thực độc đáo và ngon miệng.', 20000, 1, 0, 'https://drive.google.com/uc?id=1bH_-gCL1S4WZxQ_VjZZmtXBecUiVEqVb');
insert into Food (food_type_id, food_name, food_description, food_price, food_status, discount_percent, food_img_url) 
values (4, N'Gà xiên que', N'Gà Xiên Que là một món ăn đường phố phổ biến. Miếng thịt gà được xiên lên que và chiên giòn. Món ăn thường được phục vụ nóng hổi và thường đi kèm với các loại sốt gia vị.', 18000, 1, 0, 'https://drive.google.com/uc?id=1Ty6y9P00G49E_zjsZC_rNaA_8uKUFoav');
insert into Food (food_type_id, food_name, food_description, food_price, food_status, discount_percent, food_img_url) 
values (4, N'Xúc xích xiên que', N'Xúc Xích Xiên Que là một món ăn độc đáo và ngon miệng. Xúc xích được xiên lên que và chiên giòn. Món ăn thường được ăn kèm với các loại sốt gia vị và rau sống.', 15000, 1, 0, 'https://drive.google.com/uc?id=1jzKat34QrD7PtT-YsgeDYW5fpca8Xo34');

-- Dessert
insert into Food (food_type_id, food_name, food_description, food_price, food_status, discount_percent, food_img_url) 
values (5, N'Bánh flan', N'Bánh Flan là một món tráng miệng ngon miệng được làm từ trứng, đường và sữa. Món ăn có vị ngọt, mềm mịn và thường được phục vụ với caramel đặc biệt ở phía trên.', 10000, 1, 0, 'https://drive.google.com/uc?id=1d4TVY2jIAtI8PWcu06_FtkP2YitNW0DD');
insert into Food (food_type_id, food_name, food_description, food_price, food_status, discount_percent, food_img_url) 
values (5, N'Panna cotta', N'Panna Cotta là một món tráng miệng nguyên thủy xuất phát từ Italy. Món ăn này được làm từ sữa, đường và gelatin, tạo nên một kết cấu mềm mịn và mát lạnh. Panna Cotta thường được phục vụ với các loại sốt trái cây hoặc caramel.', 15000, 1, 0, 'https://drive.google.com/uc?id=1Is76NMoBErXptJ3dwYK-cKgDgnL0Mwki');
insert into Food (food_type_id, food_name, food_description, food_price, food_status, discount_percent, food_img_url) 
values (5, N'Sữa chua dẻo', N'Sữa Chua Dẻo là một món tráng miệng phổ biến tại các quán ăn đường phố Việt Nam. Món ăn này được làm từ sữa chua, đường và gelatin, tạo nên một kết cấu mềm mịn và ngon miệng.', 15000, 1, 0, 'https://drive.google.com/uc?id=1ivbEwy1KgLkDV3NlUtALGU6vN0SQHEEG');
insert into Food (food_type_id, food_name, food_description, food_price, food_status, discount_percent, food_img_url) 
values (5, N'Chè sen nhãn dừa', N'Chè Sen Nhãn Dừa là một món tráng miệng truyền thống của ẩm thực Việt Nam. Món ăn này bao gồm sen, nhãn và dừa tươi ngon, tạo nên hương vị ngọt ngào và mát lạnh. Món ăn thường được phục vụ với đá và thạch dừa.', 18000, 1, 10, 'https://drive.google.com/uc?id=1q7taf4uwKWJLrwttYUBeSuZLk98ytetm');

-- Drinks
insert into Food (food_type_id, food_name, food_description, food_price, food_status, discount_percent, food_img_url) 
values (6, N'Coca-Cola (330ml)', N'Coca-Cola là một loại nước ngọt có ga phổ biến trên toàn thế giới. Với hương vị đặc trưng, ngọt ngào và sảy ngon, Coca-Cola thường được phục vụ lạnh và kèm theo đá.', 12000, 1, 0, 'https://drive.google.com/uc?id=14_GTRoOdBs5zuU4VwnzftPsqtdEeW1xP');
insert into Food (food_type_id, food_name, food_description, food_price, food_status, discount_percent, food_img_url) 
values (6, N'7 Up (330ml)', N'7 Up là một loại nước ngọt có ga với hương vị chanh mát lạnh. Được sản xuất từ các thành phần tự nhiên, 7 Up thường là lựa chọn tuyệt vời để giải khát trong những ngày nắng nóng.', 12000, 1, 0, 'https://drive.google.com/uc?id=1HxLmW1x2aNiZBmKYGps8hXksinGIl7rv');
insert into Food (food_type_id, food_name, food_description, food_price, food_status, discount_percent, food_img_url) 
values (6, N'Trà đào', N'Trà Đào là một loại trà trái cây ngon mát, được làm từ trà đen pha chế cùng với hương vị tự nhiên và ngọt ngọt của đào. Một lựa chọn tuyệt vời để giải nhiệt và thư giãn trong ngày nắng nóng.', 20000, 1, 0, 'https://drive.google.com/uc?id=1RgBDS_wk1SmUfxMujGQSYMl-uOy8qob3');
insert into Food (food_type_id, food_name, food_description, food_price, food_status, discount_percent, food_img_url) 
values (6, N'Trà chanh dây', N'Trà Chanh Dây là một loại trà trái cây tươi ngon, được làm từ trà xanh pha chế cùng với hương vị chua chua ngọt ngọt của chanh dây. Một lựa chọn sức khỏe và thưởng thức tuyệt vời cho mọi ngày.', 20000, 1, 0, 'https://drive.google.com/uc?id=1Cj22ZH1a79m9zz4oKmB-ZhMZuxd24qCx');

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

-- Insert an Order for the Cart
insert into [Order] (
cart_id, customer_id, order_status_id, payment_method_id,
contact_phone, delivery_address, order_time, order_total, 
order_note, delivery_time, order_cancel_time
) values (
1, 1, 4, 1, 
'0931278397', N'39 Mậu Thân, Ninh Kiều, Cần Thơ', '20230708 10:34:09 AM', 170000, 
NULL, '20230708 10:49:35 AM', NULL);