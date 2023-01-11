package com.jonagoldxp.admin.product;

import com.jonagoldxp.common.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repo;

    public List<Product> listAll(){
        return repo.findAll();
    }

    public Product save(Product product) {
        if (product.getId() == null) {
            product.setCreatedTime(new Date());
        }

        if (product.getAlias() == null || product.getAlias().isEmpty()) {
            String defaultAlias = product.getName().replaceAll(" ", "-");
            product.setAlias(defaultAlias);
        } else {
            product.setAlias(product.getAlias().replaceAll(" ", "-"));
        }

        product.setUpdatedTime(new Date());

        Product updatedProduct = repo.save(product);

        return updatedProduct;
    }

    public Product get(Integer id) throws NoSuchElementException {
        try {
            return repo.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw new NoSuchElementException("Could not find any product with ID " + id);
        }
    }

    public void delete(Integer id) throws NoSuchElementException {
        Long countById = repo.countById(id);

        if (countById == null || countById == 0) {
            throw new NoSuchElementException("Could not find any product with ID " + id);
        }

        repo.deleteById(id);
    }
}
