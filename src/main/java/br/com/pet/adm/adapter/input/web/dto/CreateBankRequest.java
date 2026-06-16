package br.com.pet.adm.adapter.input.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO de entrada para criação de banco.
 */
@Data
public class CreateBankRequest {

    @NotBlank(message = "Código do banco é obrigatório")
    private String cdBank;

    @NotBlank(message = "Nome do banco é obrigatório")
    private String dsBank;

    private Long userId;
}
