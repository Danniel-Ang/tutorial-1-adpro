package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @Mock
    private Model model;

    private Product sampleProduct;

    @BeforeEach
    void setUp() {
        sampleProduct = new Product();
        sampleProduct.setProductId("12345");
        sampleProduct.setProductName("Test Product");
        sampleProduct.setProductQuantity(10);
    }

    @Test
    void testCreateProductPage() {
        String view = productController.createProductPage(model);
        verify(model).addAttribute(eq("product"), any(Product.class));
        assertEquals("createProduct", view);
    }

    @Test
    void testCreateProductPost() {
        String view = productController.createProductPost(sampleProduct, model);
        verify(productService).create(sampleProduct);
        assertEquals("redirect:list", view);
    }

    @Test
    void testProductListPage() {
        List<Product> productList = Arrays.asList(sampleProduct);
        when(productService.findAll()).thenReturn(productList);

        String view = productController.productListPage(model);
        verify(model).addAttribute("products", productList);
        assertEquals("productList", view);
    }

    @Test
    void testDeleteProduct() {
        String view = productController.deleteProduct("12345");
        verify(productService).delete("12345");
        assertEquals("redirect:/product/list", view);
    }

    @Test
    void testEditProductPage() {
        when(productService.findById("12345")).thenReturn(sampleProduct);

        String view = productController.editProductPage("12345", model);
        verify(model).addAttribute("product", sampleProduct);
        assertEquals("editProduct", view);
    }

    @Test
    void testEditProductPost() {
        String view = productController.editProductPost(sampleProduct);
        verify(productService).update(sampleProduct);
        assertEquals("redirect:/product/list", view);
    }
}