package br.com.pet.adm.application.port.input;

import br.com.pet.adm.application.command.IngestToBaseCommand;
import br.com.pet.adm.domain.valueobject.KnowledgeBase;

import java.util.List;

public interface IngestToBasePort {

    void ingest(IngestToBaseCommand command);

    List<KnowledgeBase> listBases();
}
