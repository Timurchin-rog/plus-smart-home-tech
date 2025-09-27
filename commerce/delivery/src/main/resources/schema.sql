CREATE TABLE IF NOT EXISTS addresses (
    address_id UUID default gen_random_uuid() PRIMARY KEY,
    country VARCHAR(100) NOT NULL,
    city VARCHAR(100) NOT NULL,
    street VARCHAR(255) NOT NULL,
    house VARCHAR(5) NOT NULL,
    flat VARCHAR(5)
);

CREATE TABLE IF NOT EXISTS deliveries (
    delivery_id UUID default gen_random_uuid() PRIMARY KEY,
    from_address_id UUID REFERENCES addresses(address_id),
    to_address_id UUID REFERENCES addresses(address_id),
    order_id UUID NOT NULL,
    delivery_state VARCHAR(20) NOT NULL,
    delivery_weight DOUBLE PRECISION NOT NULL,
    delivery_volume DOUBLE PRECISION NOT NULL,
    fragile BOOLEAN NOT NULL
);