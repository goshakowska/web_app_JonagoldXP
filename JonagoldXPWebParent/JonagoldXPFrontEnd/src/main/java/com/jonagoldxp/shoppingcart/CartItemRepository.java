package com.jonagoldxp.shoppingcart;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CartItemRepository extends CrudRepository<CartItem, Integer> {
    public List<CartItem> findByUser
}
