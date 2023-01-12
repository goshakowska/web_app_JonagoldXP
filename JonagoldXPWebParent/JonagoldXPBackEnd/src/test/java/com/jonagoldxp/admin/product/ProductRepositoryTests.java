package com.jonagoldxp.admin.product;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.Optional;

import com.jonagoldxp.common.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.jonagoldxp.common.entity.Category;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateProduct() {
        Category category = entityManager.find(Category.class, 2);

        Product product = new Product();
        product.setName("Nokia 3310");
        product.setAlias("brick_nokia");
        product.setShortDescription("Will survive nuclear explosion.");
        product.setFullDescription("The most iconic phone that existed on Earth. Sadly technology did develop, therefore the model was thrown out to trash bin. ");

        product.setCategory(category);

        product.setPrice(100);
        product.setCost(100);
        product.setEnabled(true);
        product.setInStock(true);

        product.setCreatedTime(new Date());
        product.setUpdatedTime(new Date());

        Product savedProduct = repo.save(product);

        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getId()).isGreaterThan(0);
    }
    @Test
    public void testCreateProduct_2() {
        Category category = entityManager.find(Category.class, 2);

        Product product = new Product();
        product.setName("Airpods");
        product.setAlias("air_muuuusic");
        product.setShortDescription("Will make You look cool.");
        product.setFullDescription("Nothing completes better Your look than a pair of headphones that will (eventually) effortlessly drop out of your ears. ");

        product.setCategory(category);

        product.setPrice(1000);
        product.setCost(1000);
        product.setEnabled(true);
        product.setInStock(true);

        product.setCreatedTime(new Date());
        product.setUpdatedTime(new Date());

        Product savedProduct = repo.save(product);

        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAllProducts() {
        Iterable<Product> iterableProducts = repo.findAll();

        iterableProducts.forEach(System.out::println);
    }

    @Test
    public void testGetProduct() {
        Integer id = 2;
        Product product = repo.findById(id).get();
        System.out.println(product);

        assertThat(product).isNotNull();
    }

    @Test
    public void testUpdateProduct() {
        Integer id = 1;
        Product product = repo.findById(id).get();
        product.setPrice(499);

        repo.save(product);

        Product updatedProduct = entityManager.find(Product.class, id);

        assertThat(updatedProduct.getPrice()).isEqualTo(499);
    }

    @Test
    public void testDeleteProduct() {
        Integer id = 3;
        repo.deleteById(id);

        Optional<Product> result = repo.findById(id);

        assertThat(!result.isPresent());
    }
}
