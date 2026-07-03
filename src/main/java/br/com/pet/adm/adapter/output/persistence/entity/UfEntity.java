package br.com.pet.adm.adapter.output.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

/**
 @INFO: Entidade JPA que representa a tabela TB_UF no banco de dados. O ID esta incorreto para padrões de PROJETOS PRODUTIVOS
 */

@Entity
@Table(name = "TB_UF")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UfEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CD_UF")
    private Long id;

    @Column(name = "UF")
    private String ufName;

    @Column(name = "FL_ATIVO")
    private Integer activeFlag;

    @CreatedDate
    @Column(name = "DT_CRIACAO")
    private Date creationDate;

    @LastModifiedDate
    @Column(name = "DT_ALTERACAO")
    private Date changeDate;

    @Column(name = "DT_INATIVACAO")
    private Date inactivationDate;

    @Column(name = "ID_USUARIO")
    private Long userId;
}
