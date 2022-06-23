package com.specific.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.specific.dto.CategoryDTO;
import com.specific.exception.ConflictException;
import com.specific.model.Category;
import com.specific.model.Manager;
import com.specific.model.Product;
import com.specific.model.Store;
import com.specific.repository.CategoryRepository;
import com.specific.repository.ManagerRepository;
import com.specific.service.ManagerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ManagerServiceTest {

    @Mock(lenient = true)
    private ManagerRepository managerRepository;

    @InjectMocks
    private ManagerService managerService;

    private Manager manager;
    private Store store;

    @BeforeEach
    public void setUp() throws Exception {

        store = new Store("name", "address", true);
        manager = new Manager("email", "password", "fullname", store);

        // Mock calls
        Mockito.when(managerRepository.findByEmail("email")).thenReturn(manager);

    }

    @Test
    void testSaveManager_thenReturnIt() throws ConflictException {

        Manager m1 = managerRepository.findByEmail("email");

        assertThat(m1.getEmail()).isEqualTo(manager.getEmail());

    }

    @Test
    void testSaveManagerThatAlreadyExists_thenReturnConflict() throws ConflictException {

        Mockito.when(managerRepository.save(manager)).thenReturn(manager);

        assertThrows(ConflictException.class, () -> {
            managerService.saveManager(manager);
        });

    }

}