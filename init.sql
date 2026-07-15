-- Criar a extensão pgvector
CREATE EXTENSION IF NOT EXISTS vector;

-- Criar tabela vector_store
CREATE TABLE IF NOT EXISTS public.vector_store (
    id TEXT PRIMARY KEY,
    content TEXT NOT NULL,
    metadata JSONB,
    embedding vector(768)
);

-- Criar índice HNSW para melhor desempenho em buscas de similaridade
CREATE INDEX IF NOT EXISTS on_vector_store_embedding 
ON public.vector_store USING hnsw (embedding vector_cosine_ops);
