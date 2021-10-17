package br.com.uolcompasso.productapi.interfaces.icontroller;

import br.com.uolcompasso.productapi.model.request.ProductRequest;
import br.com.uolcompasso.productapi.model.response.ProductResponse;
import org.springframework.http.ResponseEntity;
import java.math.BigDecimal;
import java.util.List;

public interface IProductController {

    ResponseEntity<ProductResponse> create(ProductRequest productRequest);

    ResponseEntity<?> update(String id, ProductRequest productRequest);

    ResponseEntity<?> findById(String id);

    ResponseEntity<List<ProductResponse>> findAll();

    ResponseEntity<List<ProductResponse>> findByFilter(BigDecimal minPrice, BigDecimal maxPrice, String q);

    ResponseEntity<?> delete(String id);
}
