package com.restaurant.restaurantapi.repositories;
import com.restaurant.restaurantapi.entities.OrderStatus;
import com.restaurant.restaurantapi.entities.Orders;
import com.restaurant.restaurantapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;


@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    @Query(value = "SELECT YEAR(cos.createdDate) AS year, MONTH(cos.createdDate) AS month, SUM(cos.total) AS totalRevenue " +
            "FROM Orders cos " +
            "WHERE cos.createdDate >= :startDate AND cos.isPaid  = true "  +
            "GROUP BY YEAR(cos.createdDate), MONTH(cos.createdDate) " +
            "ORDER BY YEAR(cos.createdDate), MONTH(cos.createdDate)", nativeQuery = true)
    List<Object[]> getMonthlyRevenueLast12Months(@Param("startDate") Timestamp startDate);



    @Query(value = "SELECT YEAR(cos.createdDate) AS year, " +
            "MONTH(cos.createdDate) AS month, " +
            "COUNT(cos.id) AS totalOrders " +
            "FROM Orders cos " +
            "WHERE cos.createdDate >= :startDate " +
            "GROUP BY YEAR(cos.createdDate), MONTH(cos.createdDate) " +
            "ORDER BY YEAR(cos.createdDate), MONTH(cos.createdDate)", nativeQuery = true)
    List<Object[]> getTotalOrdersLast12Months(@Param("startDate") Timestamp startDate);

    @Query(value = "SELECT YEAR(cos.createdDate) AS year, " +
            "MONTH(cos.createdDate) AS month, " +
            "COUNT(cos.id) AS totalOrders " +
            "FROM Orders cos " +
            "WHERE cos.createdDate >= :startDate AND cos.status = 'completed' " +
            "GROUP BY YEAR(cos.createdDate), MONTH(cos.createdDate) " +
            "ORDER BY YEAR(cos.createdDate), MONTH(cos.createdDate)", nativeQuery = true)
    List<Object[]> getDeliveredOrdersRevenueLast12Months(@Param("startDate") Timestamp startDate);

    @Query(value = "SELECT YEAR(cos.createdDate) AS year, " +
            "MONTH(cos.createdDate) AS month, " +
            "COUNT(cos.id) AS totalOrders " +
            "FROM Orders cos " +
            "WHERE cos.createdDate >= :startDate AND cos.status = 'cancelled' " +
            "GROUP BY YEAR(cos.createdDate), MONTH(cos.createdDate) " +
            "ORDER BY YEAR(cos.createdDate), MONTH(cos.createdDate)", nativeQuery = true)
    List<Object[]> getCancelledOrdersRevenueLast12Months(@Param("startDate") Timestamp startDate);

    @Query(value = "SELECT cos.createdDate AS date, " +
            "SUM(cos.total) AS totalRevenue " +
            "FROM Orders cos " +
            "WHERE cos.createdDate >= :startDate AND  cos.isPaid  = true "  +
            "GROUP BY cos.createdDate " +
            "ORDER BY cos.createdDate", nativeQuery = true)
    List<Object[]> getDailyRevenue(@Param("startDate") Timestamp startDate);

    List<Orders> findByUserId(Long userId);

    List<Orders> findAllByUser(User user);

    List<Orders> findByStatus(OrderStatus status);

}