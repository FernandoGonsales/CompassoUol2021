package br.com.uolcompasso.productapi.controller;

import br.com.uolcompasso.productapi.dto.request.ProductRequest;
import br.com.uolcompasso.productapi.dto.response.ProductResponse;
import br.com.uolcompasso.productapi.interfaces.iservice.IProductService;
import br.com.uolcompasso.productapi.model.Product;
import br.com.uolcompasso.productapi.repository.IProductRepository;
import br.com.uolcompasso.productapi.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

//    @Autowired
//    private ProductService productService;

    @Autowired
    private IProductService productService;

    @PostMapping
    @ApiOperation(value = "Criação de um produto")
    public ResponseEntity<ProductResponse> create(@RequestBody @Valid ProductRequest productRequest) {
        return productService.create(productRequest);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Atualização de um produto")
    public ResponseEntity<?> update(@PathVariable @Valid String id, @RequestBody @Valid ProductRequest productRequest) {
        return productService.update(id, productRequest);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Busca de um produto por ID")
    public ResponseEntity<?> findById(@PathVariable String id) {
        return productService.findById(id);
    }

    @GetMapping
    @ApiOperation(value = "Lista de produtos")
    public ResponseEntity<List<ProductResponse>> findAll() {
        return productService.findAll();
    }

    @GetMapping("/search")
    @ApiOperation(value = "Lista de produtos filtrados")
    public ResponseEntity<List<ProductResponse>> findAllByFilter(@RequestParam(required = false, value = "q", defaultValue = "") String q,
                                                                 @RequestParam(required = false, value = "min_price", defaultValue = "0.00") BigDecimal minPrice,
                                                                 @RequestParam(required = false, value = "max_price", defaultValue = "10000.00") BigDecimal maxPrice) {
        return productService.findByFilter(minPrice, maxPrice, q);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deleção de um produto")
    public ResponseEntity<?> delete(@PathVariable String id) {
        return productService.delete(id);
    }

}
