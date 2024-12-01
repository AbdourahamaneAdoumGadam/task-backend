package com.fad.tasktracker.service;

import com.fad.tasktracker.entities.Order;
import com.fad.tasktracker.entities.OrderProduct;
import com.fad.tasktracker.entities.User;
import com.fad.tasktracker.entities.enums.OrderStatus;
import com.fad.tasktracker.repository.OrderProductRepository;
import com.fad.tasktracker.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderProductRepository orderProductRepository) {
        this.orderRepository = orderRepository;
        this.orderProductRepository = orderProductRepository;
    }

    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUser(user);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(UUID orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public List<OrderProduct> getOrderProducts(UUID orderId) {
        Optional<Order> optionOrder = orderRepository.findById(orderId);
        if (optionOrder.isPresent()) {
            Order order = optionOrder.get();
            return orderProductRepository.findByOrder(order);
        }
        return null;
    }

    public Order addProductToOrder(UUID orderId, OrderProduct orderProduct) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            orderProduct.setOrder(order);
            orderProductRepository.save(orderProduct);
            return order;
        }
        return null;
    }

    public Order updateOrderStatus(UUID orderId, OrderStatus status) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setStatus(status);
            return orderRepository.save(order);
        }
        return null;
    }
}