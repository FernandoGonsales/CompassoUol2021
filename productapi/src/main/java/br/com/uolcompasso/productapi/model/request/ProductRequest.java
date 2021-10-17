package br.com.uolcompasso.productapi.model.request;

import br.com.uolcompasso.productapi.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @Positive
    @NotNull
    private BigDecimal price;

    public ProductRequest(Product product) {
        this.name = product.getName();;
        this.description = product.getDescription();
        this.price = product.getPrice();
    }
}
