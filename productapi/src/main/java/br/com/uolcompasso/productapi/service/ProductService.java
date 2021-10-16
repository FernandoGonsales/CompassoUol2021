package br.com.uolcompasso.productapi.service;

import br.com.uolcompasso.productapi.dto.request.ProductRequest;
import br.com.uolcompasso.productapi.dto.response.ProductResponse;
import br.com.uolcompasso.productapi.interfaces.iservice.IProductService;
import br.com.uolcompasso.productapi.model.Product;
import br.com.uolcompasso.productapi.repository.IProductRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Service
public class ProductService implements IProductService{

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private EntityManager entityManager;

    @Override
    public ResponseEntity<ProductResponse> create(ProductRequest productRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(new ProductResponse(productRepository.save(new Product(productRequest))));
    }

    @Override
    public ResponseEntity<?> update(String id, ProductRequest productRequest) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ProductResponse(productRepository.save(new Product(id, productRequest))));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Override
    public ResponseEntity<?> delete(String id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            productRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Override
    public ResponseEntity<?> findById(String id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(new ProductResponse(product.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Override
    public ResponseEntity<List<ProductResponse>> findByFilter(BigDecimal min_price, BigDecimal max_price, String q) {
        //TODO IMPLEMENTAR: ENTITYMANAGER (PARA QUERY DINAMICA), TESTES UNITARIOS/INTEGRACAO, DOCKER, DOCKER-COMPOSE
        List<Product> products = productRepository.findByPriceBetweenAndNameContainingAndDescriptionContainingAllIgnoreCase(min_price, max_price, q, q);
        return ResponseEntity.status(HttpStatus.OK).body(products.stream()
                .map(ProductResponse::new)
                .collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<List<ProductResponse>> findAll() {
        List<Product> products = productRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(products.stream()
                .map(ProductResponse::new)
                .collect(Collectors.toList()));
    }

}
