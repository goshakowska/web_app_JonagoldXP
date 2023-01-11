package com.jonagoldxp.admin.product;


import com.jonagoldxp.common.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Long countById(Integer id);
}

