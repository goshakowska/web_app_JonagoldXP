package com.jonagoldxp.admin.user;

import com.jonagoldxp.common.entity.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {
}
