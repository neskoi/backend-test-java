INSERT INTO user(name, password, cpf, email) 
VALUES('Koi Nes', '$2a$10$pEPL.hl1EP4i6oDWmpzar.XJmwkrfb/YKSkonHax00URRJRVeK0rm', '42673187006', 'koi@com.com');

INSERT INTO user(name, password, cpf, email) 
VALUES('Jan Nes', '$2a$10$ENa9esVD8dBOzWKegTaHY.IsucQ.m29dXKhSoyJYgGDd1rCNFqcka', '10510159010', 'jannes@com.com');

INSERT INTO user(name, password, cpf, email) 
VALUES('Jeovano Andrade', '$2a$10$ENa9esVD8dBOzWKegTaHY.IsucQ.m29dXKhSoyJYgGDd1rCNFqcka', '55765444040', 'jeovano@com.com');

INSERT INTO user(name, password, cpf, email) 
VALUES('Tais Fernanda', '$2a$10$ENa9esVD8dBOzWKegTaHY.IsucQ.m29dXKhSoyJYgGDd1rCNFqcka', '77960554048', 'tuca@com.com');
/*===========================================================*/
INSERT INTO car_park(name, password, cnpj, email, phone) 
VALUES('Estaciona Legal', '$2a$10$pEPL.hl1EP4i6oDWmpzar.XJmwkrfb/YKSkonHax00URRJRVeK0rm', '36388702000131', 'estalegal@com.com', '1140028922');

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
VALUES('2', '1', '5', '15', '50', 'CARRO');
INSERT INTO vacancy(adress_id, amount, hour_price, day_price, month_price, type_of_vehicle) 
VALUES('2', '50', '6.0', '25.0', '150.0', 'CARRO');
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
INSERT INTO vehicle(plate, color_id, model_id, user_id) 
VALUES('AAA0000', '1', '1', '1');
INSERT INTO vehicle(plate, color_id, model_id, user_id) 
VALUES('BBB0000', '2', '2', '2');
INSERT INTO vehicle(plate, color_id, model_id, user_id) 
VALUES('CCC0000', '3', '3', '3');
INSERT INTO vehicle(plate, color_id, model_id, user_id) 
VALUES('DDD0000', '4', '4', '4');
INSERT INTO vehicle(plate, color_id, model_id, user_id) 
VALUES('EEE0000', '5', '5', '4');
INSERT INTO vehicle(plate, color_id, model_id, user_id) 
VALUES('FFF0000', '4', '5', '4');
/*===========================================================*/
INSERT INTO car_park_usage(entrance_time, exit_time, base_paid_price, total_price, type_of_payment, vacancy_id, vehicle_id) 
VALUES('2021-01-15T16:20:23.8374058-03:00','2021-01-15T21:21:23.8374058-03:00','10.0','10.0', 'HORA', '1', '1');
INSERT INTO car_park_usage(entrance_time, exit_time, base_paid_price, total_price, type_of_payment, vacancy_id, vehicle_id) 
VALUES('2021-02-15T16:20:23.8374058-03:00','2021-02-15T22:22:23.8374058-03:00','10.0','10.0', 'HORA', '1', '2');
INSERT INTO car_park_usage(entrance_time, exit_time, base_paid_price, total_price, type_of_payment, vacancy_id, vehicle_id) 
VALUES('2021-02-15T18:20:23.8374058-03:00','2021-02-15T22:20:23.8374058-03:00','10.0','10.0', 'HORA', '1', '3');
INSERT INTO car_park_usage(entrance_time, exit_time, base_paid_price, total_price, type_of_payment, vacancy_id, vehicle_id) 
VALUES('2021-03-15T16:20:23.8374058-03:00','2021-03-15T23:23:23.8374058-03:00','10.0','0', 'MES', '1', '3');
INSERT INTO car_park_usage(entrance_time, exit_time, base_paid_price, total_price, type_of_payment, vacancy_id, vehicle_id) 
VALUES('2021-04-15T16:20:23.8374058-03:00','2021-04-15T23:23:23.8374058-03:00','10.0','10.0', 'HORA', '5', '5');
INSERT INTO car_park_usage(entrance_time, exit_time, base_paid_price, total_price, type_of_payment, vacancy_id, vehicle_id) 
VALUES('2021-05-15T16:20:23.8374058-03:00','2021-05-15T22:22:23.8374058-03:00','10.0','10.0', 'DIA', '5', '6');
INSERT INTO car_park_usage(entrance_time, exit_time, base_paid_price, total_price, type_of_payment, vacancy_id, vehicle_id) 
VALUES('2021-06-15T14:14:23.8374058-03:00','2021-06-15T21:21:23.8374058-03:00','10.0','10.0', 'DIA', '5', '2');
INSERT INTO car_park_usage(entrance_time, exit_time, base_paid_price, total_price, type_of_payment, vacancy_id, vehicle_id) 
VALUES('2021-07-15T15:15:23.8374058-03:00','2021-07-15T20:20:23.8374058-03:00','10.0','10.0', 'HORA', '2', '4');
INSERT INTO car_park_usage(entrance_time, exit_time, base_paid_price, total_price, type_of_payment, vacancy_id, vehicle_id) 
VALUES('2021-08-15T16:16:23.8374058-03:00','2021-08-15T19:19:23.8374058-03:00','10.0','10.0', 'HORA', '5', '5');
INSERT INTO car_park_usage(entrance_time, exit_time, base_paid_price, total_price, type_of_payment, vacancy_id, vehicle_id) 
VALUES('2020-09-15T17:17:23.8374058-03:00','2020-09-15T18:18:23.8374058-03:00','10.0','10.0', 'HORA', '2', '6');
/*===========================================================*/
INSERT INTO role(name) 
VALUES('CAROWNER');
INSERT INTO role(name) 
VALUES('CARPARK');
/*===========================================================*/
INSERT INTO car_park_roles(car_park_id, roles_id) 
VALUES('1', '2');
INSERT INTO car_park_roles(car_park_id, roles_id) 
VALUES('2', '2');
INSERT INTO car_park_roles(car_park_id, roles_id) 
VALUES('3', '2');
/*===========================================================*/
INSERT INTO user_roles(user_id, roles_id) 
VALUES('1', '1');
INSERT INTO user_roles(user_id, roles_id) 
VALUES('2', '1');
INSERT INTO user_roles(user_id, roles_id) 
VALUES('3', '1');
INSERT INTO user_roles(user_id, roles_id) 
VALUES('4', '1');
/*===========================================================*/