INSERT INTO user(name, password, cpf, email) 
VALUES('Koi Nes', '$2a$10$ENa9esVD8dBOzWKegTaHY.IsucQ.m29dXKhSoyJYgGDd1rCNFqcka', '42673187006', 'koi@com.com');

INSERT INTO user(name, password, cpf, email) 
VALUES('Jan Nes', '$2a$10$ENa9esVD8dBOzWKegTaHY.IsucQ.m29dXKhSoyJYgGDd1rCNFqcka', '10510159010', 'jannes@com.com');

INSERT INTO user(name, password, cpf, email) 
VALUES('Jeovano Andrade', '$2a$10$ENa9esVD8dBOzWKegTaHY.IsucQ.m29dXKhSoyJYgGDd1rCNFqcka', '55765444040', 'jeovano@com.com');

INSERT INTO user(name, password, cpf, email) 
VALUES('Tais Fernanda', '$2a$10$ENa9esVD8dBOzWKegTaHY.IsucQ.m29dXKhSoyJYgGDd1rCNFqcka', '77960554048', 'tuca@com.com');
/*===========================================================*/
INSERT INTO car_park(name, password, cnpj, email, phone) 
VALUES('Estaciona Legal', '$2a$10$ENa9esVD8dBOzWKegTaHY.IsucQ.m29dXKhSoyJYgGDd1rCNFqcka', '36388702000131', 'estalegal@com.com', '1140028922');

INSERT INTO car_park(name, password, cnpj, email, phone) 
VALUES('Paradao Veiculos', '$2a$10$ENa9esVD8dBOzWKegTaHY.IsucQ.m29dXKhSoyJYgGDd1rCNFqcka', '14284279000197', 'padrao@com.com', '2251139033');

INSERT INTO car_park(name, password, cnpj, email, phone) 
VALUES('Patio não Policial', '$2a$10$ENa9esVD8dBOzWKegTaHY.IsucQ.m29dXKhSoyJYgGDd1rCNFqcka', '74713858000130', 'pp@com.com', '3362240144');
/*===========================================================*/
INSERT INTO adress(car_park_id, street, postal_code, district, state, city, number, additional_info) 
VALUES('1', 'Rua 75', '22030003', 'Groove Street', 'GTASA', 'San Andreas', '123', 'Do lado da casa do CJ.');

INSERT INTO adress(car_park_id, street, postal_code, district, state, city, number, additional_info) 
VALUES('1', 'Rua 76', '11020002', 'Av. Sovai', 'Barreto', 'Santos', '879', '');

INSERT INTO adress(car_park_id, street, postal_code, district, state, city, number, additional_info) 
VALUES('2', 'Rua 77', '33040004', 'Viela Maneira', 'Granada', 'Mairinque', '78', 'Proximo ao mercado.');
/*===========================================================*/
INSERT INTO vacancy(adress_id, amount, hour_price, day_price, month_price, type_of_vehicle) 
VALUES('1', '25', '5.0', '20.0', '120.0', 'MOTO');
INSERT INTO vacancy(adress_id, amount, hour_price, day_price, month_price, type_of_vehicle) 
VALUES('1', '50', '6.0', '25.0', '150.0', 'CARRO');
INSERT INTO vacancy(adress_id, amount, hour_price, day_price, month_price, type_of_vehicle) 
VALUES('1', '50', '6.0', '25.0', '150.0', 'CARRO');
INSERT INTO vacancy(adress_id, amount, hour_price, day_price, month_price, type_of_vehicle) 
VALUES('3', '35', '8.0', '40.0', '200.0', 'CARRO');
/*===========================================================*/
INSERT INTO brand(brand) 
VALUES('Honda');
INSERT INTO brand(brand) 
VALUES('Yamaha');
INSERT INTO brand(brand) 
VALUES('BMW');
INSERT INTO brand(brand) 
VALUES('Toyota');
INSERT INTO brand(brand) 
VALUES('Citroen');
INSERT INTO brand(brand) 
VALUES('Ford');
/*===========================================================*/
INSERT INTO model(brand_id, type_of_vehicle, model) 
VALUES('5', 'CARRO', 'C3');
INSERT INTO model(brand_id, type_of_vehicle, model) 
VALUES('3', 'CARRO', 'M3');
INSERT INTO model(brand_id, type_of_vehicle, model) 
VALUES('4', 'CARRO', 'Yaris 2022');
INSERT INTO model(brand_id, type_of_vehicle, model) 
VALUES('4', 'CARRO', 'Corolla Cross');
INSERT INTO model(brand_id, type_of_vehicle, model) 
VALUES('1', 'MOTO', 'PCX');
INSERT INTO model(brand_id, type_of_vehicle, model) 
VALUES('1', 'MOTO', 'CBR 600RR');
INSERT INTO model(brand_id, type_of_vehicle, model) 
VALUES('2', 'MOTO', 'YZF-R3');
/*===========================================================*/
INSERT INTO color(color) 
VALUES('Vermelho');
INSERT INTO color(color) 
VALUES('Preto');
INSERT INTO color(color) 
VALUES('Azul');
INSERT INTO color(color) 
VALUES('Rosa');
INSERT INTO color(color) 
VALUES('Verde');
INSERT INTO color(color) 
VALUES('Prata');
INSERT INTO color(color) 
VALUES('Amarelo');
INSERT INTO color(color) 
VALUES('Cinza');
INSERT INTO color(color) 
VALUES('Roxo');
INSERT INTO color(color) 
VALUES('Laranja');
/*===========================================================*/