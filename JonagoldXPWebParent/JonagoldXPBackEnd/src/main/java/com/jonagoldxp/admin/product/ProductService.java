package com.jonagoldxp.admin.product;

import com.jonagoldxp.admin.paging.PagingAndSortingHelper;
import com.jonagoldxp.common.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {
    public static final int PRODUCTS_PER_PAGE = 4;
    @Autowired
    private ProductRepository repo;

    public List<Product> listAll(){
        return repo.findAll();
    }

    public void listByPage(int pageNum, PagingAndSortingHelper helper) {
        helper.listEntities(pageNum, PRODUCTS_PER_PAGE, repo);
    }

    public void searchProducts(int pageNum, PagingAndSortingHelper helper) {
        Pageable pageable = helper.createPageable(PRODUCTS_PER_PAGE, pageNum);
        String keyword = helper.getKeyword();
        Page<Product> page = repo.searchProductsByName(keyword, pageable);
        helper.updateModelAttributes(pageNum, page);
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

    public void updateProductEnabledStatus(Integer id, boolean enabled) {
        repo.updateEnabledStatus(id, enabled);
    }
}
