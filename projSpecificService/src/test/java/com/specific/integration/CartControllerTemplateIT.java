package com.specific.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.specific.model.Category;
import com.specific.model.Client;
import com.specific.model.JwtRequest;
import com.specific.model.Product;
import com.specific.model.RequestProducts;
import com.specific.model.Store;
import com.specific.repository.CartRepository;
import com.specific.repository.CategoryRepository;
import com.specific.repository.ClientRepository;
import com.specific.repository.ProductRepository;
import com.specific.repository.RequestProductsRepository;
import com.specific.repository.StoreRepository;

import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CartControllerTemplateIT {
    @LocalServerPort
    int randomServerPort;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RequestProductsRepository requestProductsRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private String token;
    private Client client;
    private Product product;
    private Store store;
    private RequestProducts requestProducts;
    private Category category;

    @Container
    public static MySQLContainer<?> container = new MySQLContainer<>("mysql:8.0").withUsername("admin")
            .withPassword("admin");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    @BeforeEach
    public void setUp(){
        client = clientRepository.saveAndFlush(new Client("eva@ua.pt",  bcryptEncoder.encode("qwertyui"), "Eva Bartolomeu", "Aveiro"));
        JwtRequest request = new JwtRequest(client.getEmail(), "qwertyui");
        ResponseEntity<Map> response = testRestTemplate.postForEntity("http://localhost:" + randomServerPort + "/login", request, Map.class);
        token = response.getBody().get("token").toString();

        store = storeRepository.saveAndFlush(new Store("PetyCity", "Rua Maria", true));
        category = categoryRepository.saveAndFlush(new Category("Toy"));
        product = productRepository.saveAndFlush(new Product("Corda", "Fixe", "image", 12.2 , 20, category, store));

        //nao salvar??
        //requestProducts = requestProductsRepository.saveAndFlush(new RequestProducts(3, client.getCart(), product));
        requestProducts = new RequestProducts(3, client.getCart(), product);
    }

    @AfterEach
    public void resetDb() {
        requestProductsRepository.deleteAll();
        requestProductsRepository.flush();

        productRepository.deleteAll();
        productRepository.flush();

        categoryRepository.deleteAll();
        categoryRepository.flush();

        storeRepository.deleteAll();
        storeRepository.flush();

        cartRepository.deleteAll();;
        cartRepository.flush();
    }


    @Test
    public void testGetProductsInvalidTokenByInvalidEmail_thenUnauthorized(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + "bad_token");
        ResponseEntity<RequestProducts> response = testRestTemplate.exchange(
            getBaseUrl() + "/user/ve/product/" + product.getId(), HttpMethod.GET, new HttpEntity<>(headers),
            RequestProducts.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.UNAUTHORIZED));
    }

    /* 
    @Test
    @DisplayName("Get Person Addresses: Invalid Token, return UNAUTHORIZED")
    public void testGetAddressesInvalidToken_thenUnauthorized() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + "bad_token");
        ResponseEntity<List> response = testRestTemplate.exchange(
                getBaseUrl()+ "/getAll", HttpMethod.GET, new HttpEntity<Object>(headers),
                List.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.UNAUTHORIZED));
    }


    @Test
    @DisplayName("Add Address: Invalid Token returns UNAUTHORIZED")
    public void testAddAddressInvalidToken_thenUnauthorized() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + "bad_token");

        ResponseEntity<Address> response = testRestTemplate.exchange(
                getBaseUrl() + "/add", HttpMethod.POST, new HttpEntity<>(addressDTO, headers),
                Address.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.UNAUTHORIZED));
    }


    @Test
    void testGetCarById_withIdInvalid(){
        ResponseEntity<Car> response = restTemplate
        .exchange("/api/carById/-9", HttpMethod.GET, null, new ParameterizedTypeReference<Car>() {
        });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
    */

    public String getBaseUrl() {
        return "http://localhost:" + randomServerPort + "/carts";
    }
}