
INSERT INTO Categories (name) VALUES
                                  ('Electronics'),
                                  ('Clothing'),
                                  ('Groceries'),
                                  ('Home Appliances'),
                                  ('Books');

INSERT INTO Manufacturers (name, country) VALUES
                                              ('Sony', 'Japan'),
                                              ('Nike', 'USA'),
                                              ('Nestle', 'Switzerland'),
                                              ('Samsung', 'South Korea'),
                                              ('Penguin Random House', 'USA');

INSERT INTO Products (name, category_id, manufacturer_id, price) VALUES
                                                                     ('Smartphone', 1, 4, 699.99),
                                                                     ('Running Shoes', 2, 2, 89.99),
                                                                     ('Chocolate Bar', 3, 3, 1.99),
                                                                     ('LED TV', 1, 4, 799.99),
                                                                     ('Fiction Book', 5, 5, 12.99);

INSERT INTO Clients (name, email) VALUES
                                      ('John Doe', 'john@example.com'),
                                      ('Alice Smith', 'alice@example.com'),
                                      ('Bob Johnson', 'bob@example.com');

INSERT INTO Sales (product_id, client_id, quantity, sale_date) VALUES
                                                                   (1, 1, 1, '2023-10-01'),
                                                                   (2, 2, 1, '2023-10-02'),
                                                                   (3, 3, 2, '2023-10-03'),
                                                                   (4, 1, 1, '2023-10-04'),
                                                                   (5, 2, 1, '2023-10-05');
