package br.com.uolcompasso.productapi.controller;

import br.com.uolcompasso.productapi.model.request.ProductRequest;
import br.com.uolcompasso.productapi.model.response.ProductResponse;
import br.com.uolcompasso.productapi.interfaces.icontroller.IProductController;
import br.com.uolcompasso.productapi.interfaces.iservice.IProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController implements IProductController {

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
    public ResponseEntity<List<ProductResponse>> findByFilter(@RequestParam(required = false, value = "min_price") BigDecimal minPrice,
                                                              @RequestParam(required = false, value = "max_price") BigDecimal maxPrice,
                                                              @RequestParam(required = false, value = "q") String q) {
        return productService.findByFilter(minPrice, maxPrice, q);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deleção de um produto")
    public ResponseEntity<?> delete(@PathVariable String id) {
        return productService.delete(id);
    }

}
