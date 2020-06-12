package com.springboot.ecom.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CART_PRODUCT")
public class CartProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartProductId;

    @ManyToOne
    @JoinColumn(name = "CART_ID", referencedColumnName = "CART_ID")
    private Cart cart;

    @OneToOne
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID")
    private Product product;

    private int quantity;

    /**
     * @return the cartProductId
     */
    public int getCartProductId() {
        return cartProductId;
    }

    /**
     * @param cartProductId
     *            the cartProductId to set
     */
    public void setCartProductId(int cartProductId) {
        this.cartProductId = cartProductId;
    }

    /**
     * @return the cart
     */
    public Cart getCart() {
        return cart;
    }

    /**
     * @param cart
     *            the cart to set
     */
    public void setCart(Cart cart) {
        this.cart = cart;
    }

    /**
     * @return the product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * @param product
     *            the product to set
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity
     *            the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
