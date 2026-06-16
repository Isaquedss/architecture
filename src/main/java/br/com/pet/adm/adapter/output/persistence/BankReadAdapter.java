package br.com.pet.adm.adapter.output.persistence;

import br.com.pet.adm.adapter.output.persistence.entity.BankEntity;
import br.com.pet.adm.adapter.output.persistence.repository.BankJpaRepository;
import br.com.pet.adm.application.criteria.BankSearchCriteria;
import br.com.pet.adm.application.port.output.BankReadRepositoryPort;
import br.com.pet.adm.application.query.result.BankResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Driven Adapter — leitura otimizada de bancos (CQRS Read Side).
 * Retorna BankResult diretamente, sem instanciar o Domain Model.
 */
@Component
@RequiredArgsConstructor
public class BankReadAdapter implements BankReadRepositoryPort {

    private final BankJpaRepository jpaRepository;

    @Override
    public Page<BankResult> findAll(BankSearchCriteria criteria) {
        Specification<BankEntity> spec = buildSpec(criteria);
        PageRequest pageRequest = PageRequest.of(
                criteria.getPage(),
                criteria.getPageSize() == Integer.MAX_VALUE ? Integer.MAX_VALUE : criteria.getPageSize(),
                buildSort(criteria.getOrderBy())
        );
        return jpaRepository.findAll(spec, pageRequest).map(this::toResult);
    }

    @Override
    public Optional<BankResult> findById(String cdBank) {
        return jpaRepository.findById(cdBank).map(this::toResult);
    }

    private BankResult toResult(BankEntity e) {
        return new BankResult(e.getCdBank(), e.getDsBank(), e.getActiveFlag(),
                e.getCreationDate(), e.getChangeDate());
    }

    private Specification<BankEntity> buildSpec(BankSearchCriteria c) {
        return Specification
                .where(like("cdBank", c.getCdBank()))
                .and(like("dsBank", c.getDsBank()))
                .and(equal("activeFlag", c.getActiveFlag()));
    }

    private Specification<BankEntity> like(String field, String value) {
        if (value == null || value.isBlank()) return null;
        return (root, query, cb) ->
                cb.like(cb.lower(root.get(field)), "%" + value.toLowerCase() + "%");
    }

    private Specification<BankEntity> equal(String field, Object value) {
        if (value == null) return null;
        return (root, query, cb) -> cb.equal(root.get(field), value);
    }

    private Sort buildSort(String orderBy) {
        if (orderBy == null || orderBy.isBlank()) return Sort.by("dsBank").ascending();
        String[] parts = orderBy.split(",");
        String field = parts[0].trim();
        boolean asc = parts.length < 2 || parts[1].trim().equalsIgnoreCase("asc");
        return asc ? Sort.by(field).ascending() : Sort.by(field).descending();
    }
}
