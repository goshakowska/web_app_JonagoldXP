package com.jonagoldxp.admin.product;

import com.jonagoldxp.common.entity.Product;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTests {
    @Autowired
    private ProductService service;
    @MockBean
    private ProductRepository repo;

    @Test
    public void getAllProductsTest(){
        when(repo.findAll()).thenReturn(List.of(new Product(), new Product()));
        assertEquals(2, service.listAll().size());
    }

    @Test
    public void getProductByIdTest(){
        Integer id = 1;
        Product product = new Product(id);
        when(repo.findById(id)).thenReturn(Optional.of(product));
        assertEquals(product, repo.findById(id).get());
    }

    @Test
    public void saveProductTest(){
        Product product = new Product("Nokia 2137");
        when(repo.save(product)).thenReturn(product);
        assertEquals(product.getAlias(), null);
        assertEquals(product.getUpdatedTime(), null);
        assertEquals(product.getCreatedTime(), null);

        assertEquals(product, service.save(product));
        assertEquals(product.getAlias(), "Nokia-2137");
        assertNotEquals(product.getUpdatedTime(), null);
        assertNotEquals(product.getCreatedTime(), null);
    }

    @Test
    public void saveProductWithIdTest(){
        Product product = new Product(1,"Nokia 2137");
        when(repo.save(product)).thenReturn(product);
        assertEquals(product.getCreatedTime(), null);

        assertEquals(product, service.save(product));
        assertEquals(product.getCreatedTime(), null);
    }

    @Test
    public void deleteProductTest(){
        Integer id = 2;
        long count = 1;
        when(repo.countById(id)).thenReturn(count);
        service.delete(id);
        Mockito.verify(repo, times(1)).deleteById(id);
    }
}
