package com.jonagoldxp.common.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private int quantity;

    public Customer getCustomer(){
        return customer;
    }

    public Product getProduct(){
        return product;
    }

    public void setCustomer(Customer customer){
        this.customer = customer;
    }

    public void setProduct(Product product){
        this.product = product;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartItem{" + "id=" + id + ", customer=" + customer +
                ", product=" + product + ", quantity=" + quantity + '}';
    }




}
