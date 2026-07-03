package br.com.pet.adm.application.port.output;

import br.com.pet.adm.adapter.output.persistence.entity.UfEntity;
import br.com.pet.adm.domain.entity.Uf;
import java.util.Optional;

public interface UfWriteRepositoryPort {
    Uf save(Uf uf);
    UfEntity findUfName(String ufName);
    Optional<Uf> findById(String cdUf);

}
