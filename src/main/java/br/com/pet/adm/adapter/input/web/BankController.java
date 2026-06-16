package br.com.pet.adm.adapter.input.web;

import br.com.pet.adm.adapter.input.web.dto.BankFilter;
import br.com.pet.adm.adapter.input.web.dto.CreateBankRequest;
import br.com.pet.adm.application.command.CreateBankCommand;
import br.com.pet.adm.application.command.DeactivateBankCommand;
import br.com.pet.adm.application.criteria.BankSearchCriteria;
import br.com.pet.adm.application.port.input.CreateBankPort;
import br.com.pet.adm.application.port.input.DeactivateBankPort;
import br.com.pet.adm.application.port.input.FindAllBanksPort;
import br.com.pet.adm.application.query.result.BankResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Driving Adapter — converte HTTP → Commands/Queries.
 * Depende dos Driving Ports (interfaces), nunca dos Handlers diretamente.
 */
@RestController
@RequestMapping("/bank")
@RequiredArgsConstructor
@Tag(name = "Bank", description = "Endpoints de gerenciamento de bancos")
public class BankController {

    // COMMAND side
    private final CreateBankPort     createBankPort;
    private final DeactivateBankPort deactivateBankPort;

    // QUERY side
    private final FindAllBanksPort   findAllBanksPort;

    // --- COMMANDS ---

    @PostMapping
    @Operation(summary = "Cria um novo banco")
    public ResponseEntity<Map<String, String>> create(
            @RequestBody @Valid CreateBankRequest request) {

        String cdBank = createBankPort.create(
                new CreateBankCommand(request.getCdBank(), request.getDsBank(), request.getUserId()));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("cdBank", cdBank));
    }

    @DeleteMapping("/{cdBank}/deactivate")
    @Operation(summary = "Inativa um banco")
    public ResponseEntity<Void> deactivate(
            @PathVariable String cdBank,
            @RequestParam(required = false) Long userId) {

        deactivateBankPort.deactivate(new DeactivateBankCommand(cdBank, userId));
        return ResponseEntity.noContent().build();
    }

    // --- QUERIES ---

    @GetMapping
    @Operation(summary = "Lista bancos com filtro e paginação")
    public ResponseEntity<Page<BankResult>> findAll(BankFilter filter) {

        BankSearchCriteria criteria = BankSearchCriteria.builder()
                .cdBank(filter.getCdBank())
                .dsBank(filter.getDsBank())
                .activeFlag(filter.getActiveFlag())
                .creationDate(filter.getCreationDate())
                .changeDate(filter.getChangeDate())
                .inactivationDate(filter.getInactivationDate())
                .userId(filter.getUserId())
                .orderBy(filter.getOrderBy())
                .page(filter.getPage())
                .pageSize(filter.getPageSize())
                .build();

        return ResponseEntity.ok(findAllBanksPort.findAll(criteria));
    }
}
