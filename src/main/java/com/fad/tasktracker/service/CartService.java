package com.fad.tasktracker.service;

import com.fad.tasktracker.entities.Cart;
import com.fad.tasktracker.entities.CartItem;
import com.fad.tasktracker.entities.Product;
import com.fad.tasktracker.entities.User;
import com.fad.tasktracker.repository.CartRepository;
import com.fad.tasktracker.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CartService {

    private final CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Cart addCart(User user) {

        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    public Optional<Cart> getCartByUser(User user) {
        return cartRepository.findByUser(user);
    }

    public Cart addProductToCart(UUID cartId, Product product, int quantity) {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cart.getItems().add(cartItem);
            cartRepository.save(cart);
            return cart;
        }
        return null;
    }

    public void deleteProductFromCart(UUID cartId, Long itemId) {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            List<CartItem> items = cart.getItems();
            items.removeIf(item -> item.getId().equals(itemId));
            cartRepository.save(cart);
        }
    }

    public Cart updateProductInCart(UUID cartId, Long itemId, int quantity) {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            List<CartItem> items = cart.getItems();
            for (CartItem item : items) {
                if (item.getId().equals(itemId)) {

                    if(item.getProduct().getQuantity() >= quantity){
                        item.setQuantity(quantity);
                        cartRepository.save(cart);
                        return cart;
                    }
                }
            }
        }
        return null;
    }

}
