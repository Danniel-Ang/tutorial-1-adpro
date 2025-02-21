package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    private Product sampleProduct;

    @BeforeEach
    void setUp() {
        sampleProduct = new Product();
        sampleProduct.setProductId("12345");
        sampleProduct.setProductName("Test Product");
        sampleProduct.setProductQuantity(10);
    }

    @Test
    void testCreateProduct() {
        when(productRepository.create(sampleProduct)).thenReturn(sampleProduct);
        Product createdProduct = productService.create(sampleProduct);
        assertEquals(sampleProduct, createdProduct);
        verify(productRepository).create(sampleProduct);
    }

    @Test
    void testFindAllProducts() {
        List<Product> productList = new ArrayList<>();
        productList.add(sampleProduct);
        Iterator<Product> iterator = productList.iterator();
        when(productRepository.findAll()).thenReturn(iterator);

        List<Product> foundProducts = productService.findAll();
        assertEquals(1, foundProducts.size());
        assertEquals(sampleProduct, foundProducts.get(0));
    }

    @Test
    void testDeleteProduct() {
        productService.delete("12345");
        verify(productRepository).delete("12345");
    }

    @Test
    void testFindProductById() {
        when(productRepository.findById("12345")).thenReturn(sampleProduct);
        Product found = productService.findById("12345");
        assertEquals(sampleProduct, found);
    }

    @Test
    void testUpdateProduct() {
        when(productRepository.update(sampleProduct)).thenReturn(sampleProduct);
        Product updatedProduct = productService.update(sampleProduct);
        assertEquals(sampleProduct, updatedProduct);
        verify(productRepository).update(sampleProduct);
    }
}
