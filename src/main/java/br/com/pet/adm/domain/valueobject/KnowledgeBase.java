package br.com.pet.adm.domain.valueobject;

public record KnowledgeBase(
        String name,
        String description
) {
    public KnowledgeBase {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Nome da base é obrigatório");
        if (description == null || description.isBlank())
            throw new IllegalArgumentException("Descrição da base é obrigatória");
    }

    // Bases pré-definidas do sistema
    public static final KnowledgeBase ARQUITETURA = new KnowledgeBase(
            "arquitetura", "Documentos sobre padrões arquiteturais: DDD, Hexagonal, CQRS"
    );
    public static final KnowledgeBase NEGOCIO = new KnowledgeBase(
            "negocio", "Documentos sobre regras de negócio do sistema"
    );
    public static final KnowledgeBase TECNICO = new KnowledgeBase(
            "tecnico", "Documentos sobre APIs, integrações e infraestrutura"
    );
}
