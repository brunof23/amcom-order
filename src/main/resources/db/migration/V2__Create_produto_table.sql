CREATE TABLE produto (
    id SERIAL PRIMARY KEY,
    codigo_produto VARCHAR(50) NOT NULL,
    nome_produto VARCHAR(100) NOT NULL,
    valor_unitario DECIMAL(10, 2) NOT NULL,
    quantidade INT NOT NULL,
    valor_total DECIMAL(10, 2) NOT NULL,
    pedido_id BIGINT,
    CONSTRAINT fk_pedido FOREIGN KEY (pedido_id) REFERENCES pedido (id) ON DELETE CASCADE
);