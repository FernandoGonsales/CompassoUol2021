package br.com.uolcompasso.productapi.controller;

import br.com.uolcompasso.productapi.entity.Product;
import br.com.uolcompasso.productapi.model.request.ProductRequest;
import br.com.uolcompasso.productapi.repository.IProductRepository;
import br.com.uolcompasso.productapi.repository.ProductCustomRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import java.math.BigDecimal;
import java.util.stream.Stream;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class productControllerTest {

    private final String ROUTE_API = "/products";

    private final String expectedBadRequestMessage = "Bad Request: Field cannot be null, empty or negative.";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IProductRepository productRepositoryMock;

    @Autowired
    private ProductCustomRepository productCustomRepositoryMock;

    @BeforeEach
    public void beforeEach() {
        productRepositoryMock.deleteAll();
    }

    @Test
    public void createProductShouldReturnSavedProduct() throws Exception {
        final ProductRequest productRequest = createDefaultProductRequest();
        final String json = objectMapper.writeValueAsString(productRequest);
        mockMvc.perform(post(ROUTE_API)
               .contentType(MediaType.APPLICATION_JSON_VALUE)
               .content(json))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.id").exists())
               .andExpect(jsonPath("$.name").value(productRequest.getName()))
               .andExpect(jsonPath("$.description").value(productRequest.getDescription()))
               .andExpect(jsonPath("$.price").value(productRequest.getPrice()));
    }

    @Test
    public void createProductEmptyNameShouldReturnValidationException() throws Exception {
        final ProductRequest productRequest = createCustomProductRequest("", "Description", BigDecimal.valueOf(25.50));
        final String json = objectMapper.writeValueAsString(productRequest);
        mockMvc.perform(post(ROUTE_API)
               .contentType(MediaType.APPLICATION_JSON_VALUE)
               .content(json))
               .andExpect(status().isBadRequest())
               .andExpect(expectStatusCode(BAD_REQUEST))
               .andExpect(expectedExceptionMessage(expectedBadRequestMessage));
    }

    @Test
    public void createProductNullNameShouldReturnValidationException() throws Exception {
        final ProductRequest productRequest = createCustomProductRequest(null, "Description", BigDecimal.valueOf(25.50));
        final String json = objectMapper.writeValueAsString(productRequest);
        mockMvc.perform(post(ROUTE_API)
               .contentType(MediaType.APPLICATION_JSON_VALUE)
               .content(json))
               .andExpect(status().isBadRequest())
               .andExpect(expectStatusCode(BAD_REQUEST))
               .andExpect(expectedExceptionMessage(expectedBadRequestMessage));
    }

    @Test
    public void createProductEmptyDescriptionShouldReturnValidationException() throws Exception {
        final ProductRequest productRequest = createCustomProductRequest("Name", "", BigDecimal.valueOf(25.50));
        final String json = objectMapper.writeValueAsString(productRequest);
        mockMvc.perform(post(ROUTE_API)
               .contentType(MediaType.APPLICATION_JSON_VALUE)
               .content(json))
               .andExpect(status().isBadRequest())
               .andExpect(expectStatusCode(BAD_REQUEST))
               .andExpect(expectedExceptionMessage(expectedBadRequestMessage));
    }

    @Test
    public void createProductNullDescriptionShouldReturnValidationException() throws Exception {
        final ProductRequest productRequest = createCustomProductRequest("Name", null, BigDecimal.valueOf(25.50));
        final String json = objectMapper.writeValueAsString(productRequest);
        mockMvc.perform(post(ROUTE_API)
               .contentType(MediaType.APPLICATION_JSON_VALUE)
               .content(json))
               .andExpect(status().isBadRequest())
               .andExpect(expectStatusCode(BAD_REQUEST))
               .andExpect(expectedExceptionMessage(expectedBadRequestMessage));
    }

    @Test
    public void createProductPriceZeroShouldReturnValidationException() throws Exception {
        final ProductRequest productRequest = createCustomProductRequest("Name", "Description", BigDecimal.valueOf(0));
        final String json = objectMapper.writeValueAsString(productRequest);
        mockMvc.perform(post(ROUTE_API)
               .contentType(MediaType.APPLICATION_JSON_VALUE)
               .content(json))
               .andExpect(status().isBadRequest())
               .andExpect(expectStatusCode(BAD_REQUEST))
               .andExpect(expectedExceptionMessage(expectedBadRequestMessage));
    }

    @Test
    public void createProductPriceNullShouldReturnValidationException() throws Exception {
        final ProductRequest productRequest = createCustomProductRequest("Name", "Description", null);
        final String json = objectMapper.writeValueAsString(productRequest);
        mockMvc.perform(post(ROUTE_API)
               .contentType(MediaType.APPLICATION_JSON_VALUE)
               .content(json))
               .andExpect(status().isBadRequest())
               .andExpect(expectStatusCode(BAD_REQUEST))
               .andExpect(expectedExceptionMessage(expectedBadRequestMessage));
    }

    @Test
    public void updateProductByIdShouldReturnUpdatedProduct() throws Exception {
        Product saveProduct = productRepositoryMock.save(mockProductDbOne());
        final ProductRequest productRequest = createCustomProductRequest("Name Update", "Description Update", BigDecimal.valueOf(133.21));
        final String json = objectMapper.writeValueAsString(productRequest);
        mockMvc.perform(put(ROUTE_API+"/"+saveProduct.getId())
               .contentType(MediaType.APPLICATION_JSON_VALUE)
               .content(json))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(saveProduct.getId()))
               .andExpect(jsonPath("$.name").value(productRequest.getName()))
               .andExpect(jsonPath("$.description").value(productRequest.getDescription()))
               .andExpect(jsonPath("$.price").value(productRequest.getPrice()));
    }

    @Test
    public void updateProductByIncorrectIdShouldReturnNotFoundException() throws Exception {
        Product saveProduct = productRepositoryMock.save(mockProductDbOne());
        saveProduct.setId("incorrect");
        final ProductRequest productRequest = createCustomProductRequest("Name Update", "Description Update", BigDecimal.valueOf(133.21));
        final String json = objectMapper.writeValueAsString(productRequest);
        mockMvc.perform(put(ROUTE_API+"/"+saveProduct.getId())
               .contentType(MediaType.APPLICATION_JSON_VALUE)
               .content(json))
               .andExpect(status().isNotFound());
    }

    @Test
    public void findByIdProductShouldReturnProductById() throws Exception {
        Product saveProduct = productRepositoryMock.save(mockProductDbOne());
        mockMvc.perform(get(ROUTE_API+"/"+saveProduct.getId())
               .contentType(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(saveProduct.getId()))
               .andExpect(jsonPath("$.name").value(saveProduct.getName()))
               .andExpect(jsonPath("$.description").value(saveProduct.getDescription()))
               .andExpect(jsonPath("$.price").value(saveProduct.getPrice()));
    }

    @Test
    public void findByIncorrectIdProductShouldReturnNotFoundException() throws Exception {
        Product saveProduct = productRepositoryMock.save(mockProductDbOne());
        saveProduct.setId("incorrect");
        mockMvc.perform(get(ROUTE_API+"/"+saveProduct.getId()))
               .andExpect(status().isNotFound());
    }

    @Test
    public void findAllProductShouldReturnAllProducts() throws Exception {
        Product saveProduct = productRepositoryMock.save(mockProductDbOne());
        Product saveProductTwo = productRepositoryMock.save(mockProductDbTwo());
        mockMvc.perform(get(ROUTE_API)
               .contentType(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(2)))
               .andExpect(jsonPath("$.[0].id").value(saveProduct.getId()))
               .andExpect(jsonPath("$.[0].name").value(saveProduct.getName()))
               .andExpect(jsonPath("$.[0].description").value(saveProduct.getDescription()))
               .andExpect(jsonPath("$.[0].price").value(saveProduct.getPrice()))
               .andExpect(jsonPath("$.[1].id").value(saveProductTwo.getId()))
               .andExpect(jsonPath("$.[1].name").value(saveProductTwo.getName()))
               .andExpect(jsonPath("$.[1].description").value(saveProductTwo.getDescription()))
               .andExpect(jsonPath("$.[1].price").value(saveProductTwo.getPrice()));
    }

    @Test
    public void findAllProductWithEmptyListShouldReturnEmptyProductList() throws Exception {
        mockMvc.perform(get(ROUTE_API))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void deleteProductByIdShouldReturnStatusOK() throws Exception {
        Product saveProduct = productRepositoryMock.save(mockProductDbOne());
        mockMvc.perform(delete(ROUTE_API+"/"+saveProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").doesNotExist());
    }

    @Test
    public void deleteProductByIncorrectIdShouldReturnNotFoundException() throws Exception {
        mockMvc.perform(delete(ROUTE_API+"/incorrect"))
               .andExpect(status().isNotFound());
    }

    @ParameterizedTest
    @MethodSource("queryParamsAndExpectedSizesProducts")
    public void findByFilterParamShouldReturnExpectedSizeProducts(final String queryParam, final Integer expectedSize) throws Exception {
        productRepositoryMock.save(mockProductDbOne());
        productRepositoryMock.save(mockProductDbTwo());
        productRepositoryMock.save(mockProductDbThree());
        mockMvc.perform(get(ROUTE_API + "/" + "search?" + queryParam))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(expectedSize)));

    }

    @Test
    public void findByFilterIncorrectParamShouldReturnMethodNotAllowedException() throws Exception {
        mockMvc.perform(post(ROUTE_API + "/find?"))
               .andExpect(status().isMethodNotAllowed())
               .andExpect(expectStatusCode(METHOD_NOT_ALLOWED))
               .andExpect(expectedExceptionMessage("Request method 'POST' not supported"));
    }

    static Stream<Arguments> queryParamsAndExpectedSizesProducts() {
        return Stream.of(
                arguments("q=Mock Product Zero", 0),
                arguments("q=Mock Product", 3),
                arguments("q=Mock Product Name2", 1),
                arguments("min_price=20.99", 2),
                arguments("min_price=30.99", 1),
                arguments("min_price=40.99", 0),
                arguments("max_price=30.99", 3),
                arguments("max_price=20.99", 2),
                arguments("max_price=9.99", 0),
                arguments("q=Mock Product Name&min_price=10.99", 3),
                arguments("q=Mock Product Name&min_price=30.99", 1),
                arguments("q=Mock Product NameZero&min_price=30.99", 0),
                arguments("q=Mock Product Name&min_price=40.99", 0),
                arguments("q=Mock Product Name&max_price=30.99", 3),
                arguments("q=Mock Product Name&max_price=10.99", 1),
                arguments("q=Mock Product NameZero&max_price=30.99", 0),
                arguments("q=Mock Product Name&max_price=9.99", 0),
                arguments("q=Mock Product Name&min_price=9.99&max_price=9.99", 0),
                arguments("q=Mock Product Name&min_price=10.99&max_price=10.99", 1),
                arguments("q=Mock Product Name&min_price=10.99&max_price=20.99", 2),
                arguments("q=Mock Product NameZero&min_price=10.99&max_price=20.99", 0)
        );
    }

    private ProductRequest createDefaultProductRequest() {
        return new ProductRequest("Create Product Name", "Create Product Description", BigDecimal.valueOf(99.99));
    }

    private ProductRequest createCustomProductRequest(String name, String description, BigDecimal price) {
        return new ProductRequest(name, description, price);
    }

    private Product mockProductDbOne() {
        return new Product("1", "Mock Product Name", "Mock Product Description", BigDecimal.valueOf(10.99));
    }

    private Product mockProductDbTwo() {
        return new Product("2", "Mock Product Name2", "Mock Product Description2", BigDecimal.valueOf(20.99));
    }

    private Product mockProductDbThree() {
        return new Product("3", "Mock Product Name3", "Mock Product Description3", BigDecimal.valueOf(30.99));
    }

    private ResultMatcher expectStatusCode(HttpStatus httpStatus) {
        return jsonPath("$.status_code").value(httpStatus.value());
    }

    private ResultMatcher expectedExceptionMessage(String expectedExceptionMessage) {
        return jsonPath("$.message").value(expectedExceptionMessage);
    }

}
