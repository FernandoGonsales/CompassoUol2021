package br.com.uolcompasso.productapi.interfaces.iservice;

import br.com.uolcompasso.productapi.dto.request.ProductRequest;
import br.com.uolcompasso.productapi.dto.response.ProductResponse;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

public interface IProductService {

    ResponseEntity<ProductResponse> create(ProductRequest productRequest);

    ResponseEntity<?> update(String id, ProductRequest productRequest);

    ResponseEntity<?> findById(String id);

    ResponseEntity<List<ProductResponse>> findAll();

    ResponseEntity<List<ProductResponse>> findByFilter(BigDecimal min_price, BigDecimal max_price, String q);

    ResponseEntity<?> delete(String id);
}
