CREATE TABLE incidents (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    titulo VARCHAR(120) NOT NULL,
    descricao VARCHAR(5000) NULL,
    prioridade VARCHAR(10) NOT NULL CHECK (prioridade IN ('BAIXA', 'MEDIA', 'ALTA', 'CRITICA')),
    status VARCHAR(20) NOT NULL CHECK (status IN ('ABERTO', 'EM_ANDAMENTO', 'RESOLVIDO', 'FECHADO')),
    responsavel_email VARCHAR(255) NOT NULL,
    data_abertura TIMESTAMP,
    data_atualizacao TIMESTAMP,
    tags VARCHAR(255)[]
);
