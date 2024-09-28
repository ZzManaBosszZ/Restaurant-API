package com.restaurant.restaurantapi.repositories;

import com.restaurant.restaurantapi.entities.OrderDetail;
import com.restaurant.restaurantapi.entities.OrderStatus;
import com.restaurant.restaurantapi.entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
    @Query("SELECT COUNT(o) FROM Orders o")
    Long countOrders();

    @Query("SELECT COUNT(o) FROM Orders o WHERE o.createdDate >= :date")
    Long countOrdersFromDate(@Param("date") Date date);

    @Query("SELECT COUNT(o) FROM Orders o WHERE o.status = :status")
    Long countByStatus(@Param("status") OrderStatus status);

    @Query("SELECT COUNT(o) FROM Orders o WHERE o.status = :status AND o.createdDate >= :date")
    Long countDeliveredOrdersFromDate(@Param("status") OrderStatus status, @Param("date") Date date);

    @Query("SELECT COUNT(o) FROM Orders o WHERE o.status = :status AND o.createdDate >= :date")
    Long countCancelledOrdersFromDate(@Param("status") OrderStatus status, @Param("date") Date date);

    @Query("SELECT SUM(o.total) FROM Orders o")
    Double sumTotalRevenue();

    @Query("SELECT SUM(o.total) FROM Orders o WHERE o.createdDate >= :date")
    Double sumTotalRevenueFromDate(@Param("date") Date date);




}
