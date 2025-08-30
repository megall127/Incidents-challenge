CREATE TABLE comments (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    incident_id UUID NOT NULL,
    autor VARCHAR(255) NOT NULL,
    mensagem TEXT NOT NULL CHECK (LENGTH(mensagem) >= 1 AND LENGTH(mensagem) <= 2000),
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (incident_id) REFERENCES incidents(id) ON DELETE CASCADE
);

-- Índice para melhorar performance nas consultas por incidente
CREATE INDEX idx_comments_incident_id ON comments(incident_id);

-- Índice para ordenação por data de criação
CREATE INDEX idx_comments_data_criacao ON comments(data_criacao);
