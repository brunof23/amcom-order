CREATE TABLE IF NOT EXISTS pedido (
    id SERIAL PRIMARY KEY,
    codigo_pedido VARCHAR(50) UNIQUE NOT NULL,
    valor_total DECIMAL(15, 2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    data_recebimento TIMESTAMP NOT NULL
);