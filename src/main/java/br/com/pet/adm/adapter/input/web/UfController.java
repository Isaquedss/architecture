package br.com.pet.adm.adapter.input.web;

import br.com.pet.adm.adapter.input.web.dto.CreateUfRequest;
import br.com.pet.adm.application.command.CreateUfCommand;
import br.com.pet.adm.application.port.input.CreatedUfPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/uf")
@RequiredArgsConstructor
@Tag(name = "UF", description = "Endpoints de gerenciamento de unidades federativas")
public class UfController {

    // COMMAND side
    private final CreatedUfPort createUfPort;

    @PostMapping
    @Operation(summary = "Cria uma nova unidade federativa")
    public ResponseEntity<Map<String, String>> create(@RequestBody @Valid CreateUfRequest request) {

        String cdUf = createUfPort.create(new CreateUfCommand(request.getUfName()));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("uf", cdUf));
    }
}
