package br.com.pet.adm.application.port.input;

import br.com.pet.adm.application.command.QueryBaseCommand;
import br.com.pet.adm.domain.valueobject.RagAnswer;

/**
 * Driving Port — expõe o caso de uso de pergunta direcionada a uma base.
 */
public interface QueryBasePort {
    RagAnswer query(QueryBaseCommand  command);
}
