package com.fad.tasktracker.controller.user;

import com.fad.tasktracker.entities.Cart;
import com.fad.tasktracker.entities.Order;
import com.fad.tasktracker.entities.OrderProduct;
import com.fad.tasktracker.entities.Product;
import com.fad.tasktracker.entities.User;
import com.fad.tasktracker.entities.enums.OrderStatus;
import com.fad.tasktracker.entities.others.AddOrderProductRequest;
import com.fad.tasktracker.service.OrderService;
import com.fad.tasktracker.service.ProductService;
import com.fad.tasktracker.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.time.LocalDate;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/user/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final ProductService productService;

    @Autowired
    public OrderController(OrderService orderService,  UserService userService, ProductService productService) {
        this.orderService = orderService;
        this.userService = userService;
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Order>> getOrdersByUser(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        Optional<User> optionalUser = userService.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            List<Order> orders = orderService.getOrdersByUser(user);
            return ResponseEntity.ok(orders);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable UUID orderId) {
        Order order = orderService.getOrderById(orderId);
        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(Authentication authentication, @RequestBody Order order) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        Optional<User> optionalUser = userService.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            order.setUser(user);
            order.setOrderDate(LocalDate.now());
            order.setStatus(OrderStatus.PENDING);
            Order createdOrder = orderService.createOrder(order);
            return ResponseEntity.ok(createdOrder);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{orderId}/products/add")
    public ResponseEntity<Order> addProductToOrder(@PathVariable UUID orderId, @RequestBody AddOrderProductRequest addOrderProductRequest) {
        Product product = productService.getProductById(addOrderProductRequest.getProductId());
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setQuantity(addOrderProductRequest.getQuantity());
        orderProduct.setProduct(product);
        Order order = orderService.addProductToOrder(orderId, orderProduct);
        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable UUID orderId, @RequestBody OrderStatus status) {
        Order updatedOrder = orderService.updateOrderStatus(orderId, status);
        if (updatedOrder != null) {
            return ResponseEntity.ok(updatedOrder);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}