package br.com.pet.adm.application.query.handler;

import br.com.pet.adm.application.port.input.FindAllBanksPort;
import br.com.pet.adm.application.port.output.BankReadRepositoryPort;
import br.com.pet.adm.application.criteria.BankSearchCriteria;
import br.com.pet.adm.application.query.result.BankResult;
import org.springframework.data.domain.Page;

/**
 * Query Handler — busca bancos via Read Side.
 * Zero side effects — só lê.
 */
public class FindAllBanksHandler implements FindAllBanksPort {

    private final BankReadRepositoryPort readRepository;

    public FindAllBanksHandler(BankReadRepositoryPort readRepository) {
        this.readRepository = readRepository;
    }

    @Override
    public Page<BankResult> findAll(BankSearchCriteria criteria) {
        return readRepository.findAll(criteria);
    }
}
