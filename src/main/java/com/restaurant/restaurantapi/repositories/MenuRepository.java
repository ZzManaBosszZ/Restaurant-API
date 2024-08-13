package com.restaurant.restaurantapi.repositories;

import com.restaurant.restaurantapi.entities.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    // Thêm các phương thức tìm kiếm tùy chỉnh nếu cần
}
