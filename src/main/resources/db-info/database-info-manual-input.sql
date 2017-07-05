USE wft;

DELETE FROM shopping_card;
DELETE FROM purchase_history;
DELETE FROM item;
DELETE FROM artist;
DELETE FROM user;
DELETE FROM artist_specialization_lkp;


INSERT INTO artist_specialization_lkp(id,spec_type) VALUES(1,'PAINTER');
INSERT INTO artist_specialization_lkp(id,spec_type) VALUES(2,'SCULPTOR');
INSERT INTO artist_specialization_lkp(id,spec_type) VALUES(3,'PHOTOGRAPHER');
INSERT INTO artist_specialization_lkp(id,spec_type) VALUES(4,'OTHER');


INSERT INTO user(firstname,lastname,age,email,password) VALUES ('Adolf','Hitler',25,'adolf.hitler@gmail.com','hiHitler');
INSERT INTO user(firstname,lastname,age,email,password) VALUES ('Iosif','Stalin',29,'iosif.stalin@gmail.com','stalinigrad');
INSERT INTO user(firstname,lastname,age,email,password) VALUES ('Valod','Valodikyan',31,'valodik.valodikyan@gmail.com','valodik31');
INSERT INTO user(firstname,lastname,age,email,password) VALUES ('Hamlet','Grigoryan',40,'hamlet.grigoryan@gmail.com','hamo40');
INSERT INTO user(firstname,lastname,age,email,password) VALUES ('Testik','Testikyan',63,'test.testikyan@gmail.com','test1234');
INSERT INTO user(firstname,lastname,age,email,password) VALUES ('Will','Smith',51,'will.smith@gmail.com','smith51');

INSERT INTO shopping_card(balance,buyer_id) VALUES(1000,1);
INSERT INTO shopping_card(balance,buyer_id) VALUES(100000,2);
INSERT INTO shopping_card(balance,buyer_id) VALUES(500,3);
INSERT INTO shopping_card(balance,buyer_id) VALUES(0,4);
INSERT INTO shopping_card(balance,buyer_id) VALUES(100,5);
INSERT INTO shopping_card(balance,buyer_id) VALUES(30,6);


INSERT INTO artist(user_id,spec_id,photo) VALUES(1,3,12321321);
INSERT INTO artist(user_id,spec_id,photo) VALUES(2,1,12321321);
INSERT INTO artist(user_id,spec_id,photo) VALUES(3,4,12321321);
INSERT INTO artist(user_id,spec_id,photo) VALUES(5,3,12321321);


INSERT INTO item(title,description,photo_url,price,artist_id,status,type) VALUES('Title 1','Test Desc 1','../../resources/images/product/images (1).jpg',100,1,0,'SCULPTURE');
INSERT INTO item(title,description,photo_url,price,artist_id,status,type) VALUES('Title 2','Test Desc 2','../../resources/images/product/images (2).jpg',250,1,0,'PHOTOGRAPHY');
INSERT INTO item(title,description,photo_url,price,artist_id,status,type) VALUES('Title 3','Test Desc 3','../../resources/images/product/images (3).jpg',450,1,0,'PAINTING');
INSERT INTO item(title,description,photo_url,price,artist_id,status,type) VALUES('Title 4','Test Desc 4','../../resources/images/product/images (4).jpg',50,1,0,'OTHER');
INSERT INTO item(title,description,photo_url,price,artist_id,status,type) VALUES('Title 5','Test Desc 5','../../resources/images/product/images (5).jpg',180,2,0,'PHOTOGRAPHY');
INSERT INTO item(title,description,photo_url,price,artist_id,status,type) VALUES('Title 6','Test Desc 6','../../resources/images/product/images (6).jpg',1000,2,0,'SCULPTURE');
INSERT INTO item(title,description,photo_url,price,artist_id,status,type) VALUES('Title 7','Test Desc 7','../../resources/images/product/images (7).jpg',300,3,0,'PAINTING');
INSERT INTO item(title,description,photo_url,price,artist_id,status,type) VALUES('Title 8','Test Desc 8','../../resources/images/product/images (8).jpg',560,5,0,'OTHER');

