ALTER TABLE orders
    ADD payment_method VARCHAR(255);

UPDATE orders
set payment_method="cash";