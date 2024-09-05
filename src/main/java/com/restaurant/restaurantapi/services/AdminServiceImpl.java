package com.restaurant.restaurantapi.services;

import com.restaurant.restaurantapi.dtos.menuadmin.Menu;
import com.restaurant.restaurantapi.dtos.menuadmin.MenuItem;
import com.restaurant.restaurantapi.dtos.orders.*;
import com.restaurant.restaurantapi.entities.OrderDetail;
import com.restaurant.restaurantapi.entities.OrderStatus;
import com.restaurant.restaurantapi.entities.Role;
import com.restaurant.restaurantapi.entities.User;
import com.restaurant.restaurantapi.repositories.OrderDetailRepository;
import com.restaurant.restaurantapi.repositories.OrdersRepository;
import com.restaurant.restaurantapi.services.impl.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminServiceImpl implements AdminService {

    private final OrdersRepository ordersRepository;

    private final OrderDetailRepository orderDetailRepository;
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
    public TotalOrderDTO getTotalOrders(User currentUser) {
        Long totalOrders = ordersRepository.countOrders();
        Long totalOrdersLast15Days = ordersRepository.countOrdersFromDate(getDate15DaysAgo());

        double percentageGrowth = 0;
        if (totalOrdersLast15Days > 0) {
            percentageGrowth = ((double) (totalOrders - totalOrdersLast15Days) / totalOrdersLast15Days) * 100;
        }

        return TotalOrderDTO.builder()
                .totalOrders(totalOrders)
                .percentageGrowth(percentageGrowth)
                .build();
    }

    @Override
    public DeliveredOrderDTO getDeliveredOrders(User currentUser) {
        Long deliveredOrders = ordersRepository.countByStatus(OrderStatus.completed);
        Long deliveredOrdersLast15Days = ordersRepository.countDeliveredOrdersFromDate(OrderStatus.completed, getDate15DaysAgo());

        double percentageGrowth = 0;
        if (deliveredOrdersLast15Days > 0) {
            percentageGrowth = ((double) (deliveredOrders - deliveredOrdersLast15Days) / deliveredOrdersLast15Days) * 100;
        }

        return DeliveredOrderDTO.builder()
                .deliveredOrders(deliveredOrders)
                .percentageGrowth(percentageGrowth)
                .build();
    }

    @Override
    public CancelledOrderDTO getCancelledOrders(User currentUser) {
        Long cancelledOrders = ordersRepository.countByStatus(OrderStatus.cancelled);
        Long cancelledOrdersLast15Days = ordersRepository.countCancelledOrdersFromDate(OrderStatus.cancelled, getDate15DaysAgo());

        double percentageGrowth = 0;
        if (cancelledOrdersLast15Days > 0) {
            percentageGrowth = ((double) (cancelledOrders - cancelledOrdersLast15Days) / cancelledOrdersLast15Days) * 100;
        }

        return CancelledOrderDTO.builder()
                .cancelledOrders(cancelledOrders)
                .percentageGrowth(percentageGrowth)
                .build();
    }

    @Override
    public TotalRevenueDTO getTotalRevenue(User currentUser) {
        Double totalRevenue = ordersRepository.sumTotalRevenue();
        Double totalRevenueLast15Days = ordersRepository.sumTotalRevenueFromDate(getDate15DaysAgo());

        double percentageGrowth = 0;
        if (totalRevenueLast15Days > 0) {
            percentageGrowth = ((totalRevenue - totalRevenueLast15Days) / totalRevenueLast15Days) * 100;
        }

        return TotalRevenueDTO.builder()
                .totalRevenue(totalRevenue)
                .percentageGrowth(percentageGrowth)
                .build();
    }
    private Date getDate15DaysAgo() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -15);
        return calendar.getTime();
    }
    @Override
    public DailyRevenueDTO getDailyRevenue(User user) {
        // Lấy doanh thu hôm nay
        double totalRevenueToday = calculateTotalRevenueByDate(LocalDate.now());

        // Lấy doanh thu tuần trước
        LocalDate startOfLastWeek = LocalDate.now().minusWeeks(1).with(DayOfWeek.MONDAY);
        LocalDate endOfLastWeek = startOfLastWeek.plusDays(6);
        double totalRevenueLastWeek = calculateTotalRevenueByDateRange(startOfLastWeek, endOfLastWeek);

        // Tính toán phần trăm tăng trưởng so với tuần trước
        double percentageGrowthLastWeek = 0;
        if (totalRevenueLastWeek > 0) {
            percentageGrowthLastWeek = ((totalRevenueToday - totalRevenueLastWeek) / totalRevenueLastWeek) * 100;
        }

        return DailyRevenueDTO.builder()
                .totalRevenueToday(totalRevenueToday)
                .percentageGrowthLastWeek(percentageGrowthLastWeek)
                .build();
    }

    private double calculateTotalRevenueByDate(LocalDate date) {
        Date startDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(date.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        List<OrderDetail> orderDetails = orderDetailRepository.findOrderDetailsByDateRange(startDate, endDate);
        return orderDetails.stream()
                .mapToDouble(OrderDetail::getUnitPrice)
                .sum();
    }

    private double calculateTotalRevenueByDateRange(LocalDate startDate, LocalDate endDate) {
        Date start = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date end = Date.from(endDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        List<OrderDetail> orderDetails = orderDetailRepository.findOrderDetailsByDateRange(start, end);
        return orderDetails.stream()
                .mapToDouble(OrderDetail::getUnitPrice) // Lấy giá trị unitPrice kiểu double
                .sum();
    }
}