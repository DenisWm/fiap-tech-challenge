ALTER TABLE orders_products
DROP
CONSTRAINT FK_ORDERS_PRODUCTS_ON_ORDER;

CREATE TABLE ordered_item
(
    id         VARCHAR(255) NOT NULL,
    quantity   INTEGER      NOT NULL,
    price      DECIMAL      NOT NULL,
    product_id VARCHAR(255) NOT NULL,
    CONSTRAINT pk_ordered_item PRIMARY KEY (id)
);

CREATE TABLE ordered_items
(
    order_id        VARCHAR(255) NOT NULL,
    ordered_item_id VARCHAR(255) NOT NULL,
    CONSTRAINT pk_ordered_items PRIMARY KEY (order_id, ordered_item_id)
);

ALTER TABLE ordered_items
    ADD CONSTRAINT FK_ORDERED_ITEMS_ON_ORDER FOREIGN KEY (order_id) REFERENCES orders (id);

ALTER TABLE ordered_item
    ADD CONSTRAINT FK_ORDERED_ITEM_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES products (id);

DROP TABLE orders_products CASCADE;