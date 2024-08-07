CREATE TABLE categories
(
    id          VARCHAR(255) NOT NULL,
    name        VARCHAR(255),
    description VARCHAR(255),
    CONSTRAINT pk_categories PRIMARY KEY (id)
);

CREATE TABLE clients
(
    id    VARCHAR(255) NOT NULL,
    name  VARCHAR(255),
    email VARCHAR(255),
    cpf   VARCHAR(255),
    CONSTRAINT pk_clients PRIMARY KEY (id)
);

CREATE TABLE orders
(
    id        VARCHAR(255) NOT NULL,
    timestamp TIMESTAMP WITHOUT TIME ZONE,
    client_id VARCHAR(255),
    total     DECIMAL,
    status    VARCHAR(255),
    CONSTRAINT pk_orders PRIMARY KEY (id)
);

CREATE TABLE orders_products
(
    order_id   VARCHAR(255) NOT NULL,
    product_id VARCHAR(255) NOT NULL,
    CONSTRAINT pk_orders_products PRIMARY KEY (order_id, product_id)
);

CREATE TABLE products
(
    id          VARCHAR(255) NOT NULL,
    name        VARCHAR(255),
    category_id VARCHAR(255),
    price       DECIMAL,
    description VARCHAR(255),
    CONSTRAINT pk_products PRIMARY KEY (id)
);

ALTER TABLE orders_products
    ADD CONSTRAINT FK_ORDERS_PRODUCTS_ON_ORDER FOREIGN KEY (order_id) REFERENCES orders (id);

ALTER TABLE products
    ADD CONSTRAINT FK_PRODUCTS_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES categories (id);