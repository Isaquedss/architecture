package br.com.pet.adm.adapter.output.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

/**
 * JPA Entity — existe APENAS na Infra.
 * O Domain nunca vê essa classe.
 */
@Entity
@Table(name = "TB_BANCO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankEntity {

    @Id
    @Column(name = "CD_BANCO", nullable = false, unique = true)
    private String cdBank;

    @Column(name = "DS_BANCO", nullable = false)
    private String dsBank;

    @Column(name = "FL_ATIVO", nullable = false)
    private Integer activeFlag;

    @Column(name = "DT_CRIACAO")
    private Date creationDate;

    @Column(name = "DT_ALTERACAO")
    private Date changeDate;

    @Column(name = "DT_INATIVACAO")
    private Date inactivationDate;

    @Column(name = "ID_USUARIO")
    private Long userId;
}
