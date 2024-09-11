package com.restaurant.restaurantapi.repositories;

import com.restaurant.restaurantapi.entities.OrderDetail;
import com.restaurant.restaurantapi.entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    OrderDetail findFirstByOrder(Orders order);

    @Query("SELECT od FROM OrderDetail od WHERE od.createdDate BETWEEN :startDate AND :endDate")
    List<OrderDetail> findOrderDetailsByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
