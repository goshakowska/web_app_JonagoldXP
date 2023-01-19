package com.jonagoldxp.admin.product;


import com.jonagoldxp.admin.paging.SearchRepository;
import com.jonagoldxp.common.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends SearchRepository<Product, Integer> {
    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1% OR p.shortDescription LIKE %?1% OR p.fullDescription LIKE %?1%")
    Page<Product> findAll(String keyword, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1%")
    Page<Product> searchProductsByName(String keyword, Pageable pageable);

    Long countById(Integer id);

    @Query("UPDATE Product p SET p.enabled = ?2 WHERE p.id = ?1")
    @Modifying
    @Transactional
    void updateEnabledStatus(Integer id, boolean enabled);
}

