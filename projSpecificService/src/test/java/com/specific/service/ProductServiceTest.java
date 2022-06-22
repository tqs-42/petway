package com.specific.service;

import java.util.Arrays;
import java.util.List;

import com.specific.model.Category;
import com.specific.model.Product;
import com.specific.model.Store;
import com.specific.repository.CategoryRepository;
import com.specific.repository.ProductRepository;
import com.specific.repository.StoreRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock(lenient = true)
    private CategoryRepository categoryRepository;

    @Mock(lenient = true)
    private StoreRepository storeRepository;

    @Mock(lenient = true)
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setUp() throws Exception {

        Category category = new Category();
        category.setId(1L);
        category.setName("Toys");

        Store store = new Store();
        store.setId(2L);
        store.setName("Loja1");

        Product p1 = new Product();
        p1.setName("P1");
        p1.setDescription("P1 muito lindo");
        p1.setCategory(category);
        p1.setStore(store);
        p1.setId(1L);

        Product p2 = new Product();
        p2.setName("P2");
        p2.setDescription("P2 muito lindo tambem");
        p2.setCategory(category);
        p2.setStore(store);
        p2.setId(2L);

        List<Product> allProducts = Arrays.asList(p1, p2);

        // Mock calls

        Mockito.when(productRepository.findAll()).thenReturn(allProducts);
        Mockito.when(categoryRepository.findById(1L)).thenReturn(category);
        Mockito.when(storeRepository.findById(2L)).thenReturn(store);
        Mockito.when(productRepository.findByName("P1")).thenReturn(p1);

    }

    @Test
    void testGetAll_thenReturn2Products() {

        Product p1 = new Product();
        p1.setId(1L);

        Product p2 = new Product();
        p2.setId(2L);

        List<Product> allProducts = productService.getProducts();

        Mockito.verify(productRepository, VerificationModeFactory.times(1)).findAll();

        assertThat(allProducts).hasSize(2).extracting(Product::getId).contains(p1.getId(), p2.getId());
    }

    // ! Nao sei se este teste se deve fazer, e ver a cena de mandar mapa
    // @Test
    // void whenCreateProduct_thenReturnIt() throws ConflictException {
    // Map<String, String> product = new HashMap<>();
    // product.put("name", "Produto 1");
    // product.put("description", "Descricaozinha");
    // product.put("image", "/path");
    // product.put("price", "15.0");
    // product.put("store", "2L");
    // product.put("category", "1L");

    // Mockito.when(productRepository.save(product)).thenReturn(product);

    // assertThat(productService.saveProduct(product)).isEqualTo(product);

    // Mockito.verify(categoryRepository,
    // VerificationModeFactory.times(1)).save(category);
    // }

    // ! TESTES PARA A STORE E CATEGORY NAO SEI SE É PRECISO AQUI MAS TEM DE SE
    // ! FAZER MOCK (???)
}