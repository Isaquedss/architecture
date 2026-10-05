package br.com.pet.adm.adapter.input.ai.tools;

import br.com.pet.adm.application.criteria.BankSearchCriteria;
import br.com.pet.adm.application.port.input.FindAllBanksPort;
import br.com.pet.adm.application.query.result.BankResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

import java.util.List;

/**
 * Tool adapter (somente leitura) — expõe a consulta de bancos para o LLM.
 */
public class BankQueryTools {

    private static final Logger log = LoggerFactory.getLogger(BankQueryTools.class);
    private static final int PAGE_SIZE = 5;

    private final FindAllBanksPort findAllBanksPort;

    public BankQueryTools(FindAllBanksPort findAllBanksPort) {
        this.findAllBanksPort = findAllBanksPort;
    }

    @Tool(description = "Busca bancos cadastrados pelo código ou por parte da descrição (nome). "
            + "Use quando o usuário perguntar sobre bancos. Retorna no máximo 5 bancos.")
    public List<BankResult> buscarBancos(
            @ToolParam(description = "Código do banco, opcional", required = false) String cdBank,
            @ToolParam(description = "Parte do nome do banco, opcional", required = false) String dsBank) {

        log.info("Tool call: buscarBancos(cdBank={}, dsBank={})", cdBank, dsBank);

        BankSearchCriteria criteria = BankSearchCriteria.builder()
                .cdBank(blankToNull(cdBank))
                .dsBank(blankToNull(dsBank))
                .page(0)
                .pageSize(PAGE_SIZE)
                .build();

        return findAllBanksPort.findAll(criteria).getContent();
    }

    private static String blankToNull(String value) {
        return value == null || value.isBlank() ? null : value;
    }
}
