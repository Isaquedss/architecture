package br.com.pet.adm.adapter.output.persistence;

import br.com.pet.adm.adapter.output.persistence.entity.UfEntity;
import br.com.pet.adm.adapter.output.persistence.mapper.UfPersistenceMapper;
import br.com.pet.adm.adapter.output.persistence.repository.UfJpaRepository;
import br.com.pet.adm.application.port.output.UfWriteRepositoryPort;
import br.com.pet.adm.domain.entity.Uf;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UfWriteAdapter implements UfWriteRepositoryPort {

    private final UfJpaRepository jpaRepository;
    private final UfPersistenceMapper mapper;

    @Override
    public Uf save(Uf uf) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(uf))); // Retornar a entidade salva
    }

    @Override
    public Optional<Uf> findById(String ufName) {
        // Implementação de busca por ID usando JPA ou outro mecanismo
        return jpaRepository.findById(ufName).map(mapper::toDomain);
    }

    @Override
    public UfEntity findUfName(String ufName) {
        return jpaRepository.findByUfNameIgnoreCase(ufName);
    }
}
