package br.com.pet.adm.adapter.input.web.dto;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * DTO de filtro para busca de bancos — camada Web.
 * Contém anotações Spring/Swagger que não devem vazar para o Domain.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankFilter {

    @Parameter(description = "Id do banco (busca parcial)")
    private String cdBank;

    @Parameter(description = "Descrição do banco (busca parcial)")
    private String dsBank;

    @Parameter(description = "Flag de ativo (0 = Inativo, 1 = Ativo)")
    private Integer activeFlag = 1;

    @Parameter(description = "Data de criação (formato: dd-MM-yyyy)")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date creationDate;

    @Parameter(description = "Data de alteração (formato: dd-MM-yyyy)")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date changeDate;

    @Parameter(description = "Data de inativação (formato: dd-MM-yyyy)")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date inactivationDate;

    @Parameter(description = "ID do usuário")
    private Long userId;

    @Parameter(description = "Ordenação (ex: cdBank,asc ou dsBank,desc)")
    private String orderBy = "dsBank,asc";

    @Parameter(description = "Número da página (começa em 1)")
    private Integer pageNumber = 1;

    @Parameter(description = "Tamanho da página")
    private Integer pageSize = Integer.MAX_VALUE;

    public int getPage() {
        return Math.max(pageNumber - 1, 0);
    }
}
