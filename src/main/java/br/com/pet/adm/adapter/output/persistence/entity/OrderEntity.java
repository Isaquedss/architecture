package br.com.pet.adm.adapter.output.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * JPA Entity para Order — existe apenas na Infra.
 */
@Entity
@Table(name = "TB_PEDIDO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEntity {

    @Id
    @Column(name = "ID_PEDIDO", nullable = false)
    private String orderId;

    @Column(name = "ID_CLIENTE", nullable = false)
    private String customerId;

    @Column(name = "DS_STATUS", nullable = false)
    private String status;

    @Column(name = "VL_TOTAL", nullable = false)
    private BigDecimal total;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Builder.Default
    private List<OrderItemEntity> items = new ArrayList<>();
}
