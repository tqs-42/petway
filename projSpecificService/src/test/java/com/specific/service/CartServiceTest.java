package com.specific.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

import com.specific.exception.ConflictException;
import com.specific.exception.ResourceNotFoundException;
import com.specific.model.Client;
import com.specific.model.Product;
import com.specific.model.RequestProducts;
import com.specific.model.Store;
import com.specific.repository.CartRepository;
import com.specific.repository.ClientRepository;
import com.specific.repository.ProductRepository;
import com.specific.repository.RequestProductsRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {
    @Mock(lenient = true)
    private CartRepository cartRepository;

    @Mock(lenient = true)
    private ClientRepository clientRepository;

    @Mock(lenient = true)
    private ProductRepository productRepository;

    @Mock(lenient = true)
    private RequestProductsRepository requestProductsRepository;

    @InjectMocks
    private CartService service;

    private Client client;
    private Product product;
    private Product product2;
    private Product product3;
    private RequestProducts requestProducts;
    private RequestProducts requestProducts2;
    private RequestProducts requestProducts3;
    private Store store;

    //FALTA TESTAR QUANDO ADICIONAMOS PRODUTOS DE DIFERENTES LOJAS, GERA A EXECECAO DE CONFLITO
    @BeforeEach
    public void setUp() throws Exception {
        client = new Client("eva@ua.pt", "qwertyui", "Eva Bartolomeu", "Aveiro");
        client.getCart().setId(1L);

        store = new Store("PetyCity", "Rua Maria", true);

        product = new Product("Corda", "Fixe", 12.2 , 20, store);
        product.setId(1L);
        product2 = new Product("Friskies", "Muito bom!", 15.2 , 26, store);
        product2.setId(2L);
        product3 = new Product("Racao Peru", "UAU", 7.2 , 36, store);
        product3.setId(3L);

        requestProducts = new RequestProducts(3, client.getCart(), product);
        requestProducts.setId(1L);
        requestProducts2 = new RequestProducts(4, client.getCart(), product2);
        requestProducts2.setId(2L);
        requestProducts3 = new RequestProducts(5, client.getCart(), product3);
        requestProducts3.setId(3L);

        Set<RequestProducts> setRequestesProducts = new HashSet<>();
        setRequestesProducts.add(requestProducts);
        setRequestesProducts.add(requestProducts2);
        setRequestesProducts.add(requestProducts3);

        client.getCart().setProducts(setRequestesProducts);
        
        Mockito.when(clientRepository.findByEmail("eva@ua.pt")).thenReturn(client);
        Mockito.when(cartRepository.findByClient(client)).thenReturn(client.getCart());
    }

    @Test
    void testWhenValidEmailAndValidproductId_thenRequestProductsShouldBeFound() throws ResourceNotFoundException {
        RequestProducts fromDb = service.getProductAmout("eva@ua.pt", 1L).get();
        
        assertThat(fromDb.getId()).isEqualTo(1L);
        assertThat(fromDb.getAmount()).isEqualTo(3);
        assertThat(fromDb.getCart().getId()).isEqualTo(1);
        assertThat(fromDb.getProduct().getId()).isEqualTo(1);

        Mockito.verify(clientRepository, VerificationModeFactory.times(1)).findByEmail("eva@ua.pt");
        Mockito.verify(cartRepository, VerificationModeFactory.times(1)).findByClient(client);
    }

    @Test
    void testWhenInvalidEmailAndValidproductId_thenReturnEmpty() throws ResourceNotFoundException {
        assertThrows(ResourceNotFoundException.class, () -> {
            service.getProductAmout("b", 1L);
        });
        Mockito.verify(clientRepository, VerificationModeFactory.times(1)).findByEmail("b");
        Mockito.verify(cartRepository, VerificationModeFactory.times(0)).findByClient(any());
    }

    @Test
    void testWhenValidEmailAndInvalidproductId_thenReturnEmpty() throws ResourceNotFoundException {
        assertThrows(ResourceNotFoundException.class, () -> {
            service.getProductAmout("eva@ua.pt", 20L);
        });

        Mockito.verify(clientRepository, VerificationModeFactory.times(1)).findByEmail("eva@ua.pt");
        Mockito.verify(cartRepository, VerificationModeFactory.times(1)).findByClient(client);
    }

    @Test
    void testWhenInvalidEmailAndInvalidproductId_thenReturnEmpty() throws ResourceNotFoundException {
        assertThrows(ResourceNotFoundException.class, () -> {
            service.getProductAmout("b", 20L);
        });
        
        Mockito.verify(clientRepository, VerificationModeFactory.times(1)).findByEmail("b");
        Mockito.verify(cartRepository, VerificationModeFactory.times(0)).findByClient(any());
    }

    @Test
    void testWhenValidEmail_thenReturnRequestsProducts() throws ResourceNotFoundException{
        List<RequestProducts> allRequestsProducts = service.getProducts("eva@ua.pt").get();
        
        assertThat(allRequestsProducts).hasSize(3).extracting(RequestProducts::getId).contains(1L, 2L, 3L);

        Mockito.verify(clientRepository, VerificationModeFactory.times(1)).findByEmail("eva@ua.pt");
        Mockito.verify(cartRepository, VerificationModeFactory.times(1)).findByClient(client);
    }

    @Test
    void testWhenInvalidEmail_thenReturnEmpty() throws ResourceNotFoundException{
        
        assertThrows(ResourceNotFoundException.class, () -> {
            service.getProducts("b");
        });

        Mockito.verify(clientRepository, VerificationModeFactory.times(1)).findByEmail("b");
        Mockito.verify(cartRepository, VerificationModeFactory.times(0)).findByClient(any());
    }

    @Test
    void testWhenPutProductAmountValidEmailValidproductId_thenReturnRequestProducts() throws ResourceNotFoundException, ConflictException {
        RequestProducts requestProducts4 = new RequestProducts(10, client.getCart(), product);
        requestProducts4.setId(1L);
        Mockito.when(requestProductsRepository.save(requestProducts)).thenReturn(requestProducts4);
        
        RequestProducts fromDb = service.putProductAmout("eva@ua.pt", 1, 10).get();

        assertThat(fromDb.getId()).isEqualTo(1);
        assertThat(fromDb.getAmount()).isEqualTo(10);
        assertThat(fromDb.getCart().getId()).isEqualTo(1);
        assertThat(fromDb.getProduct().getId()).isEqualTo(1);

        Mockito.verify(clientRepository, VerificationModeFactory.times(1)).findByEmail("eva@ua.pt");
        Mockito.verify(cartRepository, VerificationModeFactory.times(1)).findByClient(client);
        Mockito.verify(requestProductsRepository, VerificationModeFactory.times(1)).save(requestProducts);
    }
    
    @Test
    void testWhenPostProductAmountValidEmailValidproductId_thenReturnRequestProducts() throws ResourceNotFoundException, ConflictException {
        Product product1 = new Product("Racao Frango", "HIHI", 18.4 , 5, store);
        product1.setId(20L);

        Mockito.when(productRepository.findById(20L)).thenReturn(product1);

        RequestProducts requestProducts4 = new RequestProducts(10, client.getCart(), product1);
        requestProducts4.setId(25L);
        Mockito.when(requestProductsRepository.save(any())).thenReturn(requestProducts4);

        RequestProducts fromDb = service.putProductAmout("eva@ua.pt", 20, 10).get();

        assertThat(fromDb.getId()).isEqualTo(25);
        assertThat(fromDb.getAmount()).isEqualTo(10);
        assertThat(fromDb.getCart().getId()).isEqualTo(1);
        assertThat(fromDb.getProduct().getId()).isEqualTo(20);

        Mockito.verify(clientRepository, VerificationModeFactory.times(1)).findByEmail("eva@ua.pt");
        Mockito.verify(cartRepository, VerificationModeFactory.times(1)).findByClient(client);
        Mockito.verify(productRepository, VerificationModeFactory.times(1)).findById(20L);
        Mockito.verify(requestProductsRepository, VerificationModeFactory.times(1)).save(any());
    }

    @Test
    void testWhenDeleteProductAmountValidEmailValidproductId_thenReturnRequestProducts() throws ResourceNotFoundException, ConflictException {
        RequestProducts fromDb = service.putProductAmout("eva@ua.pt", 1, 0).get();

        assertThat(fromDb.getId()).isEqualTo(1);
        assertThat(fromDb.getAmount()).isEqualTo(0);
        assertThat(fromDb.getCart().getId()).isEqualTo(1);
        assertThat(fromDb.getProduct().getId()).isEqualTo(1);

        Mockito.verify(clientRepository, VerificationModeFactory.times(1)).findByEmail("eva@ua.pt");
        Mockito.verify(cartRepository, VerificationModeFactory.times(1)).findByClient(client);
    }

    @Test
    void testWhenProductAmountInvalidEmailValidproductId_thenReturnRequestProducts() throws ResourceNotFoundException, ConflictException {
        assertThrows(ResourceNotFoundException.class, () -> {
            service.putProductAmout("b", 1, 10);
        });

        Mockito.verify(clientRepository, VerificationModeFactory.times(1)).findByEmail("b");
    }

    @Test
    void testWhenProductAmountValidEmailInvalidproductId_thenReturnRequestProducts() throws ResourceNotFoundException, ConflictException {
        assertThrows(ResourceNotFoundException.class, () -> {
            service.putProductAmout("eva@ua.pt", 100, 10);
        });

        Mockito.verify(clientRepository, VerificationModeFactory.times(1)).findByEmail("eva@ua.pt");
        Mockito.verify(cartRepository, VerificationModeFactory.times(1)).findByClient(client);
    }

    @Test
    void testWhenProductAmountInvalidEmailInvalidproductId_thenReturnRequestProducts() throws ResourceNotFoundException, ConflictException {
        assertThrows(ResourceNotFoundException.class, () -> {
            service.putProductAmout("b", 100, 10);
        });

        Mockito.verify(clientRepository, VerificationModeFactory.times(1)).findByEmail("b");
    }
}
