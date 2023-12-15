DROP DATABASE IF EXISTS pixelrushdb;
CREATE DATABASE pixelrushdb;-- Creamos base de datos

USE pixelrushdb;-- seleccionamos nuestra nueva BBDD

CREATE TABLE User(
	username VARCHAR(30) PRIMARY KEY NOT NULL,
	password VARCHAR(40) NOT NULL,
	mail VARCHAR(50) NOT NULL,
	name VARCHAR(20) NOT NULL,
	surname VARCHAR(20) NOT NULL,
	photo VARCHAR(500),
	state VARCHAR(300),
	birthDate VARCHAR(15),
	pointsEarned INT
)ENGINE = InnoDB;

CREATE TABLE StoreObject(
	objectID VARCHAR(20) PRIMARY KEY NOT NULL,
	articleName VARCHAR(20) NOT NULL,
	price INT NOT NULL,
	description VARCHAR(200),
	articlePhoto VARCHAR(500)
)ENGINE = InnoDB;

CREATE TABLE Matches(
	id INT NOT NULL AUTO_INCREMENT,
	username VARCHAR(30) NOT NULL,
	totalPoints INT,
	currentLVL INT NOT NULL,
	maxLVL INT NOT NULL,
	PRIMARY KEY(id),
	FOREIGN KEY (username) REFERENCES User(username)
)ENGINE = InnoDB;

CREATE TABLE OwnedObjects(
	id INT NOT NULL AUTO_INCREMENT,
	username VARCHAR(30) NOT NULL,
	objectID VARCHAR(20) NOT NULL,
	PRIMARY KEY(id),
	FOREIGN KEY (username) REFERENCES User(username),
	FOREIGN KEY (objectID) REFERENCES StoreObject(objectID)
)ENGINE = InnoDB;

INSERT INTO User VALUES('Roger01','password','Roger@gmail.com','Roger','Jimenez',null,null,'17/11/1998',90);
INSERT INTO User VALUES('titi','456','titi@gmail.com','Carles','Sanchez',null,null,'17/11/1998',9000);



