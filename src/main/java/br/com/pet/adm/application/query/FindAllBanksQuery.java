package br.com.pet.adm.application.query;

import br.com.pet.adm.application.criteria.BankSearchCriteria;

/**
 * CQRS Query — intenção de buscar bancos.
 */
public record FindAllBanksQuery(BankSearchCriteria criteria) {}
