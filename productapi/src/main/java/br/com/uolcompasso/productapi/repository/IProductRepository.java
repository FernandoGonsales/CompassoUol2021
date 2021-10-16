package br.com.uolcompasso.productapi.repository;

import br.com.uolcompasso.productapi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product, String> {

    List<Product>findByPriceBetweenAndNameContainingAndDescriptionContainingAllIgnoreCase(BigDecimal priceMin, BigDecimal priceMax, String name, String description);
}


