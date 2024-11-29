package com.restaurant.restaurantapi.repositories;

import com.restaurant.restaurantapi.entities.FoodOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FoodOrderDetailRepository extends JpaRepository<FoodOrderDetail, Long> {
    @Query("SELECT f FROM FoodOrderDetail f WHERE f.createdDate BETWEEN :startDate AND :endDate")
    List<FoodOrderDetail> findOrderDetailsByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}

