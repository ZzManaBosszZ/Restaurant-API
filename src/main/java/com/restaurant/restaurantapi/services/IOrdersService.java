package com.restaurant.restaurantapi.services;
import com.restaurant.restaurantapi.dtos.cart.CartDTO;
import com.restaurant.restaurantapi.dtos.orderdetail.OrderDetailDTO;
import com.restaurant.restaurantapi.entities.User;
import com.restaurant.restaurantapi.dtos.orders.OrdersDTO;
import com.restaurant.restaurantapi.entities.*;
import com.restaurant.restaurantapi.exceptions.AppException;
import com.restaurant.restaurantapi.mappers.OrderDetailMapper;
import com.restaurant.restaurantapi.mappers.OrdersMapper;
import com.restaurant.restaurantapi.models.food.FoodQuantity;
import com.restaurant.restaurantapi.models.orders.CreateOrders;
import com.restaurant.restaurantapi.repositories.FoodOrderDetailRepository;
import com.restaurant.restaurantapi.repositories.FoodRepository;
import com.restaurant.restaurantapi.repositories.OrderDetailRepository;
import com.restaurant.restaurantapi.repositories.OrdersRepository;
import com.restaurant.restaurantapi.services.impl.OrdersService;
import com.restaurant.restaurantapi.exceptions.ErrorCode;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IOrdersService implements OrdersService {

    private final OrdersRepository ordersRepository;
    private final OrdersMapper ordersMapper;
    private final FoodRepository foodRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final FoodOrderDetailRepository foodOrderDetailRepository;
    private final OrderDetailMapper orderDetailMapper;
//    private final CartServiceImpl cartService;


    private String generateOrderCode() {
        return UUID.randomUUID().toString();
    }
    @Transactional(rollbackFor = AppException.class)
    @Override
    public OrdersDTO create(CreateOrders createOrders, User user) throws AppException {
        String orderCode = generateOrderCode();
        Orders order = Orders.builder()
                .orderCode(orderCode)
                .total(BigDecimal.ZERO)
                .isPaid(false)
                .status(OrderStatus.pending)
                .user(user)
                .paymentMethod(createOrders.getPaymentMethod())
                .createdBy(user.getFullName())
                .modifiedBy(user.getFullName())
                .createdDate(new Timestamp(System.currentTimeMillis()))
                .modifiedDate(new Timestamp(System.currentTimeMillis()))
                .build();

        // Create OrderDetail
        OrderDetail orderDetail = OrderDetail.builder()
                .order(order)
                .discount(createOrders.getDiscount())
                .createdDate(new Timestamp(System.currentTimeMillis()))
                .modifiedDate(new Timestamp(System.currentTimeMillis()))
                .createdBy(user.getFullName())
                .modifiedBy(user.getFullName())
                .user(user)
                .foodOrderDetails(new ArrayList<>())
                .build();

        // Get food quantities from CreateOrders
        List<FoodQuantity> foodQuantities = createOrders.getFoodQuantities();

        // Get food list from the database
        List<Food> foods = foodRepository.findAllById(
                foodQuantities.stream()
                        .map(FoodQuantity::getFoodId)
                        .collect(Collectors.toList())
        );

        // Calculate total and create FoodOrderDetail
        BigDecimal total = BigDecimal.ZERO;
        List<FoodOrderDetail> foodOrderDetails = new ArrayList<>();

        for (FoodQuantity foodQuantity : foodQuantities) {
            Food food = foods.stream()
                    .filter(f -> f.getId().equals(foodQuantity.getFoodId()))
                    .findFirst()
                    .orElseThrow(() -> new AppException(ErrorCode.FOOD_NOTFOUND));

            BigDecimal unitPrice = BigDecimal.valueOf(food.getPrice());
            BigDecimal itemTotal = unitPrice.multiply(BigDecimal.valueOf(foodQuantity.getQuantity()));
            total = total.add(itemTotal);

            FoodOrderDetail foodOrderDetail = new FoodOrderDetail();
            foodOrderDetail.setOrderDetail(orderDetail);
            foodOrderDetail.setFood(food);
            foodOrderDetail.setQuantity(foodQuantity.getQuantity());
            foodOrderDetail.setUnitPrice(unitPrice);
            foodOrderDetail.setCreatedDate(new Timestamp(System.currentTimeMillis()));
            foodOrderDetails.add(foodOrderDetail);
        }

        // Set total price in Order
        order.setTotal(total);

        if ("paypal".equalsIgnoreCase(createOrders.getPaymentMethod())) {
          order.setPaid(true);
//            order.setStatus(OrderStatus.paid);
        }
        else if ("cod".equalsIgnoreCase(createOrders.getPaymentMethod())) {
            order.setPaid(false);  // Thanh toán qua COD thì mặc định là chưa thanh toán
        }

        // Save Order and FoodOrderDetail
        OrderDetail savedOrderDetail = orderDetailRepository.save(orderDetail);
        foodOrderDetailRepository.saveAll(foodOrderDetails);

        order.setOrderDetail(savedOrderDetail);
        Orders savedOrder = ordersRepository.save(order);

        return ordersMapper.toOrdersDTO(savedOrder);
    }


//    @Transactional(rollbackFor = AppException.class)
//    @Override
//    public OrdersDTO create(User user, HttpSession session) throws AppException {
//        CartDTO cart = cartService.getCartByUser(user, session);
//        if (cart == null || cart.getItems().isEmpty()) {
//            throw new AppException(ErrorCode.CART_EMPTY);
//        }
//        String orderCode = generateOrderCode();
//        Orders order = Orders.builder()
//                .orderCode(orderCode)
//                .total(BigDecimal.ZERO)
//                .isPaid(false)
//                .status(OrderStatus.pending)
//                .user(user)
//                .createdBy(user.getFullName())
//                .modifiedBy(user.getFullName())
//                .createdDate(new Timestamp(System.currentTimeMillis()))
//                .modifiedDate(new Timestamp(System.currentTimeMillis()))
//                .build();
//        OrderDetail orderDetail = OrderDetail.builder()
//                .order(order)
//                .discount(BigDecimal.ZERO)
//                .createdDate(new Timestamp(System.currentTimeMillis()))
//                .modifiedDate(new Timestamp(System.currentTimeMillis()))
//                .createdBy(user.getFullName())
//                .modifiedBy(user.getFullName())
//                .user(user)
//                .foodOrderDetails(new ArrayList<>())
//                .build();
//        List<FoodQuantity> foodQuantities = cart.getItems().stream()
//                .map(item -> {
//                    FoodQuantity foodQuantity = new FoodQuantity();
//                    foodQuantity.setFoodId(item.getFoodId());
//                   foodQuantity.setQuantity(item.getQuantity());
//                   return foodQuantity;
//                }).toList();
//        // Lấy danh sách thực phẩm từ cơ sở dữ liệu
//        List<Food> foods = foodRepository.findAllById(
//                foodQuantities.stream()
//                        .map(FoodQuantity::getFoodId)
//                        .collect(Collectors.toList())
//        );
//
//        // Tính tổng và tạo FoodOrderDetail
//        BigDecimal total = BigDecimal.ZERO;
//        List<FoodOrderDetail> foodOrderDetails = new ArrayList<>();
//
//        for (FoodQuantity foodQuantity : foodQuantities) {
//            Food food = foods.stream()
//                    .filter(f -> f.getId().equals(foodQuantity.getFoodId()))
//                    .findFirst()
//                    .orElseThrow(() -> new AppException(ErrorCode.FOOD_NOTFOUND));
//
//            BigDecimal unitPrice = BigDecimal.valueOf(food.getPrice());
//            BigDecimal itemTotal = unitPrice.multiply(BigDecimal.valueOf(foodQuantity.getQuantity()));
//            total = total.add(itemTotal);
//            FoodOrderDetail foodOrderDetail = new FoodOrderDetail();
//            foodOrderDetail.setOrderDetail(orderDetail);
//            foodOrderDetail.setFood(food);
//            foodOrderDetail.setQuantity(foodQuantity.getQuantity());
//            foodOrderDetail.setUnitPrice(unitPrice);
//            foodOrderDetail.setCreatedDate(new Timestamp(System.currentTimeMillis()));
//            foodOrderDetails.add(foodOrderDetail);
//        }
//        order.setTotal(total);
//        OrderDetail savedOrderDetail = orderDetailRepository.save(orderDetail);
//        foodOrderDetailRepository.saveAll(foodOrderDetails);
//
//        order.setOrderDetail(savedOrderDetail);
//        Orders savedOrder = ordersRepository.save(order);
//
//        // Xóa giỏ hàng sau khi tạo đơn hàng
//        cartService.clearCart(user, session);
//
//        return ordersMapper.toOrdersDTO(savedOrder);
//    }

    @Override
    public OrdersDTO findById(Long id) {
        Orders order = ordersRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        return ordersMapper.toOrdersDTO(order);
    }

    @Override
    public List<OrdersDTO> findAll() {
        return ordersRepository.findAll().stream()
                .map(ordersMapper::toOrdersDTO)
                .collect(Collectors.toList());
    }


    @Override
    public List<OrdersDTO> findOrdersByUser(User user) {
        List<Orders> orders = ordersRepository.findAllByUser(user);
        return orders.stream()
                .map(ordersMapper::toOrdersDTO)
                .collect(Collectors.toList());
    }


    @Override
    public void delete(Long id) {
        Orders order = ordersRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        ordersRepository.delete(order);
    }

    @Transactional(rollbackFor = AppException.class)
    @Override
    public void updateStatus(Long orderId, OrderStatus newStatus) throws AppException {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        if (order.getStatus().equals(OrderStatus.completed) || order.getStatus().equals(OrderStatus.cancelled)) {
            throw new AppException(ErrorCode.INVALID_ORDER_STATUS_CHANGE);
        }

        if (newStatus.equals(OrderStatus.completed)) {
            if (!order.getStatus().equals(OrderStatus.process)) {
                throw new AppException(ErrorCode.INVALID_ORDER_STATUS_CHANGE);
            }
            if ("cod".equalsIgnoreCase(order.getPaymentMethod())) {
                order.setPaid(true);
            }
        }
        if (newStatus.ordinal() < order.getStatus().ordinal()) {
            throw new AppException(ErrorCode.INVALID_ORDER_STATUS_CHANGE);
        }

        order.setStatus(newStatus);
        order.setModifiedDate(new Timestamp(System.currentTimeMillis()));
        ordersRepository.save(order);
    }




    @Override
    public OrderDetailDTO getOrderDetailByIdAndUser(Long orderId, User currentUser) {

        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        if (!order.getUser().getId().equals(currentUser.getId())) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        OrderDetail orderDetail = order.getOrderDetail();
        if (orderDetail == null) {
            throw new AppException(ErrorCode.ORDER_DETAIL_NOT_FOUND);
        }
        return orderDetailMapper.toOrderDetailDTO(orderDetail);
    }



}
