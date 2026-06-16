package br.com.pet.adm.domain.entity;

import br.com.pet.adm.domain.valueobject.Money;

/**
 * Entity interna do Aggregate Order.
 * Só acessível pela Order (Aggregate Root).
 */
public class OrderItem {

    private final String productId;
    private int          quantity;
    private final Money  unitPrice;

    public OrderItem(String productId, int quantity, Money unitPrice) {
        if (productId == null || productId.isBlank())
            throw new IllegalArgumentException("Produto é obrigatório");
        if (quantity <= 0)
            throw new IllegalArgumentException("Quantidade deve ser positiva");
        this.productId = productId;
        this.quantity  = quantity;
        this.unitPrice = unitPrice;
    }

    public void increaseQuantity(int extra) {
        if (extra <= 0)
            throw new IllegalArgumentException("Quantidade deve ser positiva");
        this.quantity += extra;
    }

    public Money subtotal() {
        return unitPrice.multiply(quantity);
    }

    public String getProductId() { return productId; }
    public int    getQuantity()  { return quantity; }
    public Money  getUnitPrice() { return unitPrice; }
}
