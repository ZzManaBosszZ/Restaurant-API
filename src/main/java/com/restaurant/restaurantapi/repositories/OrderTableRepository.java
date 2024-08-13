package com.restaurant.restaurantapi.repositories;

import com.restaurant.restaurantapi.entities.OrderTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderTableRepository extends JpaRepository<OrderTable, Long> {
    // Thêm các phương thức truy vấn tùy chỉnh nếu cần
}
