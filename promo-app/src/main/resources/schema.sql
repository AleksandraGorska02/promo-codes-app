CREATE TABLE promoCode (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         code VARCHAR(255) NOT NULL,
                         discount DECIMAL(10,2) NOT NULL,
                         currency VARCHAR(255) NOT NULL,
                         expiration_date DATE NOT NULL,
                         TYPE VARCHAR(255) NOT NULL,
                         max_usage INT NOT NULL,
                         usage_count INT NOT NULL DEFAULT 0
);

CREATE TABLE product (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         description VARCHAR(255) ,
                         price DECIMAL(10,2) NOT NULL,
                         currency VARCHAR(255) NOT NULL
);

CREATE TABLE purchase (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         date DATE NOT NULL,
                         regularPrice DECIMAL(10,2) NOT NULL,
                         discount DECIMAL(10,2) ,
                         product_name VARCHAR(255) NOT NULL,
                         currency VARCHAR(255) NOT NULL
);