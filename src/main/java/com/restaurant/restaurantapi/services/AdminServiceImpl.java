package com.restaurant.restaurantapi.services;

import com.restaurant.restaurantapi.dtos.UserDTO;
import com.restaurant.restaurantapi.dtos.menuadmin.Menu;
import com.restaurant.restaurantapi.dtos.menuadmin.MenuItem;
import com.restaurant.restaurantapi.dtos.orders.*;
import com.restaurant.restaurantapi.entities.*;
import com.restaurant.restaurantapi.mappers.UserMapper;
import com.restaurant.restaurantapi.repositories.FoodOrderDetailRepository;
import com.restaurant.restaurantapi.repositories.MenuRepository;
import com.restaurant.restaurantapi.repositories.OrdersRepository;
import com.restaurant.restaurantapi.repositories.UserRepository;
import com.restaurant.restaurantapi.services.impl.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class AdminServiceImpl implements AdminService {

    private final OrdersRepository ordersRepository;
    private final FoodOrderDetailRepository foodOrderDetailRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<Menu> getMenu(User currenUser) {
        List<Menu> menus = new ArrayList<>();
        Set<Role> roles = convertAuthoritiesToRoles(currenUser.getAuthorities());
        if (roles.contains(Role.ADMIN)) {
            List<MenuItem> menuItems1 = new ArrayList<>();
            List<MenuItem> menuItems2 = new ArrayList<>();
            List<MenuItem> menuItems3 = new ArrayList<>();
            List<MenuItem> menuItems4 = new ArrayList<>();
            List<MenuItem> menuItems5 = new ArrayList<>();
            List<MenuItem> menuItems6 = new ArrayList<>();
            List<MenuItem> menuItems7 = new ArrayList<>();
            menuItems1.add(new MenuItem("Dashboard", "/", "far fa-chart-bar"));
            menuItems2.add(new MenuItem("Course Online", "/course-online", "fas fa-book-reader"));
            menuItems2.add(new MenuItem("Course Offline", "/course-offline", "fas fa-book-reader"));
            menuItems2.add(new MenuItem("Categories", "/category-list", "fas fa-stream"));
            menuItems2.add(new MenuItem("Order History", "/order-history", "fas fa-history"));
            menuItems3.add(new MenuItem("All Classes", "/classes", "fas fa-school"));
            menuItems3.add(new MenuItem("Classroom", "/classroom", "fas fa-door-closed"));
            menuItems4.add(new MenuItem("Booking", "/booking", "fas fa-calendar-alt"));
            menuItems4.add(new MenuItem("Room Online", "/room", "fas fa-door-open"));
            menuItems4.add(new MenuItem("Tutor Registration", "/tutor/registration", "fas fa-user-edit"));
            menuItems5.add(new MenuItem("Entrance Test", "/entrance-test", "fas fa-window-restore"));
            menuItems6.add(new MenuItem("Student", "/student", "fas fa-user-graduate"));
            menuItems6.add(new MenuItem("Teacher", "/teacher", "fas fa-chalkboard-teacher"));
            menuItems7.add(new MenuItem("Profile", "/profile", "fas fa-user-shield"));
            menus.add(new Menu("Dashboard", menuItems1));
            menus.add(new Menu("Course & Lesson", menuItems2));
            menus.add(new Menu("Classes & Room", menuItems3));
            menus.add(new Menu("Teacher & Tutor", menuItems4));
            menus.add(new Menu("Entrance Test", menuItems5));
            menus.add(new Menu("Staff & Student", menuItems6));
            menus.add(new Menu("Information", menuItems7));
        } else if (roles.contains(Role.USER)) {
            List<MenuItem> menuItems1 = new ArrayList<>();
            List<MenuItem> menuItems2 = new ArrayList<>();
            List<MenuItem> menuItems4 = new ArrayList<>();
            List<MenuItem> menuItems5 = new ArrayList<>();
            menuItems1.add(new MenuItem("Dashboard", "/dashboard-teacher", "far fa-chart-bar"));
            menuItems2.add(new MenuItem("Teaching Class", "/teacher/class", "fas fa-door-open"));
            menuItems4.add(new MenuItem("Room", "/room", "fas fa-door-open"));
            menuItems4.add(new MenuItem("Booking Waiting", "/booking-waiting", "fas fa-calendar-alt"));
            menuItems4.add(new MenuItem("Tutoring Schedule", "/tutoring-schedule", "fas fa-chalkboard-teacher"));
            menuItems4.add(new MenuItem("Tutor Registration", "/tutor/registration", "fas fa-user-edit"));
            menuItems5.add(new MenuItem("Profile", "/profile", "fas fa-user-shield"));
            menus.add(new Menu("Dashboard", menuItems1));
            menus.add(new Menu("Teacher & Tutor", menuItems4));
            menus.add(new Menu("Teaching Class", menuItems2));
            menus.add(new Menu("Information", menuItems5));
        }
        return menus;
    }
    private Set<Role> convertAuthoritiesToRoles(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map(grantedAuthority -> Role.valueOf(grantedAuthority.getAuthority()))
                .collect(Collectors.toSet());
    }


    @Override
    public List<TotalOrderDTO> getTotalOrdersLast12Months(User currentUser) {
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.minusMonths(12).withDayOfMonth(1);
        Timestamp startTimestamp = Timestamp.valueOf(startDate.atStartOfDay());

        // Lấy tổng số lượng đơn hàng
        List<Object[]> ordersData = ordersRepository.getTotalOrdersLast12Months(startTimestamp);
        Map<YearMonth, Long> ordersMap = new HashMap<>();

        // Chuyển đổi dữ liệu từ cơ sở dữ liệu thành Map để dễ dàng xử lý
        for (Object[] result : ordersData) {
            Integer year = (Integer) result[0];
            Integer month = (Integer) result[1];
            Long totalOrders = ((Number) result[2]).longValue(); // Chuyển đổi từ Number sang Long
            YearMonth yearMonth = YearMonth.of(year, month);
            ordersMap.put(yearMonth, totalOrders);
        }

        List<TotalOrderDTO> result = new ArrayList<>();
        Long previousMonthOrders = 0L;

        // Tạo dữ liệu cho 12 tháng gần nhất và tính toán tỷ lệ phần trăm tăng trưởng
        for (int i = 0; i < 12; i++) {
            YearMonth yearMonth = YearMonth.now().minusMonths(i);
            Long totalOrders = ordersMap.getOrDefault(yearMonth, 0L);

            double percentageGrowth = 0.0;
            if (previousMonthOrders > 0) {
                percentageGrowth = calculatePercentageGrowth(previousMonthOrders, totalOrders);
            }

            result.add(TotalOrderDTO.builder()
                    .year(yearMonth.getYear())
                    .month(yearMonth.getMonthValue())
                    .totalOrders(totalOrders)
                    .percentageGrowth(percentageGrowth)
                    .build());

            previousMonthOrders = totalOrders;
        }

        return result;
    }

    private double calculatePercentageGrowth(Long oldValue, Long newValue) {
        if (oldValue == 0) {
            return newValue > 0 ? 100.0 : 0.0;
        }
        return ((newValue - oldValue) * 100.0) / oldValue;
    }



    @Override
    public List<TotalRevenueDTO> getTotalMonthlyRevenueLast12Months(User currentUser) {
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.minusMonths(12).withDayOfMonth(1);
        Timestamp startTimestamp = Timestamp.valueOf(startDate.atStartOfDay());

        List<Object[]> revenueData = ordersRepository.getMonthlyRevenueLast12Months(startTimestamp);
        Map<YearMonth, BigDecimal> revenueMap = new HashMap<>();

        for (Object[] result : revenueData) {
            Integer year = (Integer) result[0];
            Integer month = (Integer) result[1];
            BigDecimal totalRevenue = (BigDecimal) result[2]; // Sử dụng BigDecimal
            YearMonth yearMonth = YearMonth.of(year, month);
            revenueMap.put(yearMonth, totalRevenue);
        }

        List<TotalRevenueDTO> result = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            YearMonth yearMonth = YearMonth.now().minusMonths(i);
            BigDecimal totalRevenue = revenueMap.getOrDefault(yearMonth, BigDecimal.ZERO); // Sử dụng BigDecimal.ZERO
            result.add(new TotalRevenueDTO(yearMonth.getYear(), yearMonth.getMonthValue(), totalRevenue));
        }

        return result;
    }

    @Override
    public List<DeliveredOrderDTO> getDeliveredOrdersRevenueLast12Months(User currentUser) {
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.minusMonths(12).withDayOfMonth(1);
        Timestamp startTimestamp = Timestamp.valueOf(startDate.atStartOfDay());

        List<Object[]> deliveredOrdersData = ordersRepository.getDeliveredOrdersRevenueLast12Months(startTimestamp);
        Map<YearMonth, Long> ordersMap = new HashMap<>();

        for (Object[] result : deliveredOrdersData) {
            Integer year = (Integer) result[0];
            Integer month = (Integer) result[1];
            Long totalOrders = ((Number) result[2]).longValue();
            YearMonth yearMonth = YearMonth.of(year, month);
            ordersMap.put(yearMonth, totalOrders);
        }

        List<DeliveredOrderDTO> result = new ArrayList<>();
        Long previousMonthOrders = 0L;

        for (int i = 0; i < 12; i++) {
            YearMonth yearMonth = YearMonth.now().minusMonths(i);
            Long totalOrders = ordersMap.getOrDefault(yearMonth, 0L);

            double percentageGrowth = 0.0;
            if (previousMonthOrders > 0) {
                percentageGrowth = calculatePercentageGrowth(previousMonthOrders, totalOrders);
            }

            result.add(DeliveredOrderDTO.builder()
                    .year(yearMonth.getYear())
                    .month(yearMonth.getMonthValue())
                    .totalOrders(totalOrders)
                    .percentageGrowth(percentageGrowth)
                    .build());

            previousMonthOrders = totalOrders;
        }

        return result;
    }

    @Override
    public List<CancelledOrderDTO> getCancelledOrdersRevenueLast12Months(User currentUser) {
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.minusMonths(12).withDayOfMonth(1);
        Timestamp startTimestamp = Timestamp.valueOf(startDate.atStartOfDay());

        List<Object[]> cancelledOrdersData = ordersRepository.getCancelledOrdersRevenueLast12Months(startTimestamp);
        Map<YearMonth, Long> ordersMap = new HashMap<>();

        for (Object[] result : cancelledOrdersData) {
            Integer year = (Integer) result[0];
            Integer month = (Integer) result[1];
            Long totalOrders = ((Number) result[2]).longValue();
            YearMonth yearMonth = YearMonth.of(year, month);
            ordersMap.put(yearMonth, totalOrders);
        }

        List<CancelledOrderDTO> result = new ArrayList<>();
        Long previousMonthOrders = 0L;

        for (int i = 0; i < 12; i++) {
            YearMonth yearMonth = YearMonth.now().minusMonths(i);
            Long totalOrders = ordersMap.getOrDefault(yearMonth, 0L);

            double percentageGrowth = 0.0;
            if (previousMonthOrders > 0) {
                percentageGrowth = calculatePercentageGrowth(previousMonthOrders, totalOrders);
            }

            result.add(CancelledOrderDTO.builder()
                    .year(yearMonth.getYear())
                    .month(yearMonth.getMonthValue())
                    .totalOrders(totalOrders)
                    .percentageGrowth(percentageGrowth)
                    .build());

            previousMonthOrders = totalOrders;
        }

        return result;
    }

    @Override
    public List<DailyRevenueDTO> getDailyRevenue(User currentUser) {
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.minusMonths(12).withDayOfMonth(1);
        Timestamp startTimestamp = Timestamp.valueOf(startDate.atStartOfDay());

        List<Object[]> dailyRevenueData = ordersRepository.getDailyRevenue(startTimestamp);
        Map<LocalDate, BigDecimal> revenueMap = new HashMap<>();

        for (Object[] result : dailyRevenueData) {
            LocalDate date = ((Timestamp) result[0]).toLocalDateTime().toLocalDate();
            BigDecimal totalRevenue = (BigDecimal) result[1];
            revenueMap.put(date, totalRevenue);
        }

        List<DailyRevenueDTO> result = new ArrayList<>();
        LocalDate endDate = LocalDate.now();

        BigDecimal previousDayRevenue = BigDecimal.ZERO;

        for (LocalDate date = startDate; date.isBefore(endDate) || date.isEqual(endDate); date = date.plusDays(1)) {
            BigDecimal totalRevenue = revenueMap.getOrDefault(date, BigDecimal.ZERO);
            double percentageGrowth = calculatePercentageGrowth(previousDayRevenue.doubleValue(), totalRevenue.doubleValue());

            result.add(DailyRevenueDTO.builder()
                    .date(date)
                    .totalRevenue(totalRevenue.doubleValue())
                    .percentageGrowth(percentageGrowth)
                    .build());

            previousDayRevenue = totalRevenue;
        }

        return result;
    }

    private double calculatePercentageGrowth(double oldValue, double newValue) {
        if (oldValue == 0) return 0.0;
        return ((newValue - oldValue) / oldValue) * 100;
    }

    @Override
    public List<UserDTO> getUser(User currenUser) {
        return userRepository.findAll().stream()
                .map(userMapper::toUserSummaryDTO)
                .toList();
    }

    @Override
    public UserOrdersResponseDTO getOrdersByUser(Long userId, User currentUser) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<OrdersUserDTO> orders = ordersRepository.findByUserId(userId).stream()
                .map(order -> OrdersUserDTO.builder()
                        .id(order.getId())
                        .orderCode(order.getOrderCode())
                        .total(order.getTotal())
                        .status(order.getStatus())
                        .isPaid(order.isPaid())
                        .createdDate(order.getCreatedDate())
                        .modifiedDate(order.getModifiedDate())
                        .createdBy(order.getCreatedBy())
                        .modifiedBy(order.getModifiedBy())
                        .build())
                .collect(Collectors.toList());

        return UserOrdersResponseDTO.builder()
                .user(userMapper.toUserSummaryDTO(user))
                .orders(orders)
                .build();
    }

}