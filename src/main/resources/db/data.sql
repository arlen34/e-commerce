insert into users (id, email, name, surname, password, phone_number, role) VALUES
                                                                               (1, 'admin@gmail.com', 'Admin', 'Adminov', '$2a$12$MOY3XQCNQzwgv4lFqnpSrey/ELGiXhyTQZRygWVlAwFu3Zxy3zFbu', '0702003550', 'SUPER_ADMIN'),
                                                                               (2, 'client@gmail.com', 'Client', 'Clientov', '$2a$12$PhRg1tBOK9sA2A8gEa4JZ.lF6yWI5/KKg38CkmNsOSTFWQekA72Y6', '0702010101', 'USER');

insert into categories (id, category_name, parent_category_id) VALUES
                                                                   (1, 'electronic', null),
                                                                   (2, 'mobile phone', 1),
                                                                   (3, 'laptop', 1),
                                                                   (4, 'TV',1),
                                                                   (5, 'appliances', null),
                                                                   (6, 'refrigerator', 5),
                                                                   (7, 'плитка', 5),
                                                                   (8, 'фен', 5);

insert into products (id, amount, description, price, product_name, category_id) VALUES
                                                                                     (1, 5, 'Iphone 14 pro 256g', 1000, 'Iphone 14', 2),
                                                                                     (2, 4, 'Iphone 13 pro 256g', 800, 'Iphone 13', 2),
                                                                                     (3, 3, 'Iphone 12 pro 256g', 600, 'Iphone 12', 2),
                                                                                     (4, 3, 'MacBook pro 256g', 1500, 'macbook', 3),
                                                                                     (5, 4, 'MacBook pro 2  256g', 1800, 'macbook 2', 3),
                                                                                     (6, 5, 'MacBook pro 3  256g', 2100, 'macbook 3', 3),
                                                                                     (7, 7, 'HP 1999x1200', 700, 'HP', 4),
                                                                                     (8, 8, 'LG 1999x1200', 750, 'LG', 4),
                                                                                     (9, 6, 'Samsung 1999x1200', 850, 'Samsung', 4);



insert into carts (id, user_id) VALUES
    (1, 2);

insert into cart_items (id, quantity, cart_id, product_id) VALUES
                                                               (1, 1, 1, 3),
                                                               (2, 2, 1, 5),
                                                               (3, 3, 1, 9);

insert into reviews (id, date, text, product_id, user_id) VALUES
                                                              (1,'2022-12-10', 'This is the best picture in my opinion', 9, 2),
                                                              (2,'2022-12-10', 'This is so good', 8, 2),
                                                              (3,'2022-12-10', 'This is the best ', 7, 2),
                                                              (4,'2022-12-10', 'This is the best product', 6, 2);