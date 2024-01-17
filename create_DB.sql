DROP DATABASE IF EXISTS pixelrushdb;
CREATE DATABASE pixelrushdb;-- Creamos base de datos

USE pixelrushdb;-- seleccionamos nuestra nueva BBDD

CREATE TABLE Users(
	username VARCHAR(30) PRIMARY KEY NOT NULL,
	password VARCHAR(40) NOT NULL,
	mail VARCHAR(50) NOT NULL,
	name VARCHAR(20) NOT NULL,
	surname VARCHAR(20) NOT NULL,
	photo VARCHAR(500),
	state VARCHAR(300),
	birthDate VARCHAR(15),
	pointsEarned int
)ENGINE = InnoDB;

CREATE TABLE StoreObject(
	objectID VARCHAR(20) PRIMARY KEY NOT NULL,
	articleName VARCHAR(20) NOT NULL,
	price int NOT NULL,
	description VARCHAR(200),
	articlePhoto VARCHAR(500)
)ENGINE = InnoDB;

CREATE TABLE Matches(
	id int NOT NULL AUTO_INCREMENT,
	username VARCHAR(30) NOT NULL,
	totalPoints int,
	currentLVL int NOT NULL,
	maxLVL int NOT NULL,
	PRIMARY KEY(id),
	FOREIGN KEY (username) REFERENCES Users(username)
)ENGINE = InnoDB;

CREATE TABLE OwnedObjects(
	id int NOT NULL AUTO_INCREMENT,
	username VARCHAR(30) NOT NULL,
	objectID VARCHAR(20) NOT NULL,
	PRIMARY KEY(id),
	FOREIGN KEY (username) REFERENCES Users(username),
	FOREIGN KEY (objectID) REFERENCES StoreObject(objectID)
)ENGINE = InnoDB;

INSERT INTO Users VALUES('Roger01','123','Roger@gmail.com','Roger','Jimenez','https://assets.stickpng.com/images/584df3ad6a5ae41a83ddee08.png','no state','17/11/1998',200);
INSERT INTO Users VALUES('titi','456','titi@gmail.com','Carles','Sanchez','no photo','https://media.formula1.com/image/upload/content/dam/fom-website/manual/2023/rb19.png.transform/9col/image.png','17/11/1998',9000);

INSERT INTO Matches (username, totalPoints, currentLVL, maxLVL) VALUES ('titi','300','2','3');


INSERT INTO StoreObject VALUES ('telephone','telephone','100','a telephone to call','https://pngimg.com/d/phone_PNG49004.png');
INSERT INTO StoreObject VALUES ('radio','radio','100','a radio to listen to music','https://static.vecteezy.com/system/resources/previews/001/207/935/original/radio-png.png');

INSERT INTO OwnedObjects (username, objectID) VALUES ('titi','telephone');
