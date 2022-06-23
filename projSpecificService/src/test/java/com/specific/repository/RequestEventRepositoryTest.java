package com.specific.repository;

import java.sql.Date;
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
import com.specific.model.RequestEvents;
import com.specific.model.RequestStatus;
import com.specific.repository.RequestEventsRepository;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RequestEventRepositoryTest {
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
    private RequestEventsRepository rEventsRepository;

    @Autowired
    private TestEntityManager entityManager;

    // Valid

    // @Test
    // void testRpAndFindById_thenReturn() {
    //     RequestEvents rp = new RequestEvents(1, RequestStatus.DELIVERED);
    //     entityManager.persistAndFlush(rp);
    //     RequestEvents res = rEventsRepository.findRequestEventsByRequestId(rp.getId());
    //     assertThat(rp).isEqualTo(res);
    // }


    // Invalid

    @Test
    void testRpAndFindByInvalidId_thenReturn() {
        RequestEvents res = rEventsRepository.findRequestEventsByRequestId(-6500L);
        assertThat(res).isEqualTo(null);
    }

}