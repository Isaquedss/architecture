package br.com.pet.adm.application.criteria;

import lombok.Builder;

import java.util.Date;

/**
 * Critério de busca da camada Application.
 * Versão limpa do BankFilter — sem anotações Spring/Swagger.
 */
@Builder
public record BankSearchCriteria(
        String cdBank,
        String dsBank,
        Integer activeFlag,
        Date creationDate,
        Date changeDate,
        Date inactivationDate,
        Long userId,
        String orderBy,
        Integer page,
        Integer pageSize) {

}
