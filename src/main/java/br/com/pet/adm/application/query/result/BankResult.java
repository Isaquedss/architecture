package br.com.pet.adm.application.query.result;

import java.util.Date;

/**
 * Query Result — modelo de leitura para Bank (CQRS Read Side).
 * Retornado diretamente pelo Read Adapter, sem instanciar a Entity.
 */
public record BankResult(
    String  cdBank,
    String  dsBank,
    Integer activeFlag,
    Date    creationDate,
    Date    changeDate
) {}
