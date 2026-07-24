package br.com.pet.adm.adapter.config;

import br.com.pet.adm.application.port.output.KnowledgeBaseRepositoryPort;
import br.com.pet.adm.domain.valueobject.KnowledgeBase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner  {

    private final KnowledgeBaseRepositoryPort knowledgeBaseRepository;

    @Override
    public void run(ApplicationArguments args) {
        log.info("Inicializando bases de conhecimento padrão...");

        knowledgeBaseRepository.register(KnowledgeBase.ARQUITETURA);
        knowledgeBaseRepository.register(KnowledgeBase.NEGOCIO);
        knowledgeBaseRepository.register(KnowledgeBase.TECNICO);

        log.info("Bases registradas: {}", knowledgeBaseRepository.findAll()
                .stream().map(KnowledgeBase::name).toList());
    }
}
