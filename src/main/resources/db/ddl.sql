CREATE TABLE IF NOT EXISTS Categories (
                            id INT PRIMARY KEY AUTO_INCREMENT,
                            name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS Manufacturers (
                               id INT PRIMARY KEY AUTO_INCREMENT,
                               name VARCHAR(100) NOT NULL,
                               country VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS Products (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          name VARCHAR(100) NOT NULL,
                          category_id INT,
                          manufacturer_id INT,
                          price DECIMAL(10, 2),
                          FOREIGN KEY (category_id) REFERENCES Categories(id),
                          FOREIGN KEY (manufacturer_id) REFERENCES Manufacturers(id)
);
CREATE TABLE IF NOT EXISTS Clients (
                         id INT PRIMARY KEY AUTO_INCREMENT,
                         name VARCHAR(100) NOT NULL,
                         email VARCHAR(100)
);


CREATE TABLE IF NOT EXISTS Sales (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       product_id INT,
                       client_id INT,
                       quantity INT,
                       sale_date DATE,
                       FOREIGN KEY (product_id) REFERENCES Products(id),
                       FOREIGN KEY (client_id) REFERENCES Clients(id)
);