package com.fad.tasktracker.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fad.tasktracker.entities.Order;
import com.fad.tasktracker.entities.OrderProduct;
import java.util.List;


@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, UUID> {
    List<OrderProduct> findByOrder(Order order);
}