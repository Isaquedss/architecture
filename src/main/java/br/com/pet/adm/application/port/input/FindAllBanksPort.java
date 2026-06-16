package br.com.pet.adm.application.port.input;

import br.com.pet.adm.application.criteria.BankSearchCriteria;
import br.com.pet.adm.application.query.result.BankResult;
import org.springframework.data.domain.Page;

/**
 * Driving Port — expõe o caso de uso de busca de bancos.
 */
public interface FindAllBanksPort {
    Page<BankResult> findAll(BankSearchCriteria criteria);
}
