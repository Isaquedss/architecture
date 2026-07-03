package br.com.pet.adm.adapter.input.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateUfRequest {

    @NotBlank(message = "Nome da UF é obrigatório")
    private String ufName;
}
