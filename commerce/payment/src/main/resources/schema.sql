CREATE TABLE IF NOT EXISTS payments (
    payment_id UUID default gen_random_uuid() PRIMARY KEY,
    order_id UUID NOT NULL,
    total_payment NUMERIC(15, 2) NOT NULL,
    delivery_total NUMERIC(15, 2) NOT NULL,
    fee_total NUMERIC(15, 2) NOT NULL,
    payment_state VARCHAR(50) NOT NULL
);