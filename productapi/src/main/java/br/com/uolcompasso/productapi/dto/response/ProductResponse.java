package br.com.uolcompasso.productapi.dto.response;

import br.com.uolcompasso.productapi.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductResponse {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.name = product.getName();;
        this.description = product.getDescription();
        this.price = product.getPrice();
    }
}
