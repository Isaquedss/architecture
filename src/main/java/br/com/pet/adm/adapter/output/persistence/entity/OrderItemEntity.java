package br.com.pet.adm.adapter.output.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * JPA Entity para OrderItem — existe apenas na Infra.
 */
@Entity
@Table(name = "TB_PEDIDO_ITEM")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PEDIDO", nullable = false)
    @ToString.Exclude
    private OrderEntity order;

    @Column(name = "ID_PRODUTO", nullable = false)
    private String productId;

    @Column(name = "QT_ITEM", nullable = false)
    private int quantity;

    @Column(name = "VL_UNITARIO", nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "DS_MOEDA", nullable = false)
    private String currency;
}
