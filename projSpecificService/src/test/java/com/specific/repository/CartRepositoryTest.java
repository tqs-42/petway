package com.specific.repository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.specific.model.Cart;
import com.specific.model.Client;


@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CartRepositoryTest {
    /* 
    @Container
    public static MySQLContainer<?> container = new MySQLContainer<>("mysql:8.0").withUsername("admin")
            .withPassword("admin");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testCreateCartAndFindById_thenReturn() {
        //ELIMINAR ESTA FUNCAO MAYBE
        Cart cart = new Cart();

        entityManager.persistAndFlush(cart);

        Cart res = cartRepository.findById(cart.getId()).get();

        assertThat(res).isEqualTo(cart);
    }

    @Test
    void testCreateCategoryAndFindByClient_thenReturn() {
        Client client = new Client("eva@ua.pt", "12345", "Eva Bartolomeu", "Rua M");
        Cart cart = new Cart(client);

        entityManager.persistAndFlush(cart);

        Cart res = cartRepository.findByClient(client);

        assertThat(res).isEqualTo(cart);


    }
    */
}
