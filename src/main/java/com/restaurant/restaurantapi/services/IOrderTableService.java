package com.restaurant.restaurantapi.services;

import com.restaurant.restaurantapi.dtos.ordertable.OrderTableDTO;
import com.restaurant.restaurantapi.entities.Menu;
import com.restaurant.restaurantapi.entities.Notification;
import com.restaurant.restaurantapi.entities.OrderStatus;
import com.restaurant.restaurantapi.entities.OrderTable;
import com.restaurant.restaurantapi.exceptions.AppException;
import com.restaurant.restaurantapi.exceptions.ErrorCode;
import com.restaurant.restaurantapi.mappers.OrderTableMapper;
import com.restaurant.restaurantapi.models.mail.MailStructure;
import com.restaurant.restaurantapi.models.ordertable.CreateOrderTable;
import com.restaurant.restaurantapi.repositories.MenuRepository;
import com.restaurant.restaurantapi.repositories.NotificationRepository;
import com.restaurant.restaurantapi.repositories.OrderTableRepository;

import com.restaurant.restaurantapi.services.impl.INotificationService;
import com.restaurant.restaurantapi.services.impl.MailService;
import com.restaurant.restaurantapi.services.impl.OrderTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IOrderTableService implements OrderTableService {

    private final INotificationService notificationService;
    private final OrderTableRepository orderTableRepository;
    private final MenuRepository menuRepository;
    private final OrderTableMapper orderTableMapper;
    private final MailService mailService;
//    @Override
//    public OrderTableDTO createOrderTable(CreateOrderTable createOrderTable) {
//        Menu menu = menuRepository.findById(createOrderTable.getMenuId())
//                .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));
//        OrderTable orderTable = OrderTable.builder()
//                .name(createOrderTable.getName())
//                .numberOfPerson(createOrderTable.getNumberOfPerson())
//                .email(createOrderTable.getEmail())
//                .phone(createOrderTable.getPhone())
//                .status(OrderStatus.pending)
//                .time(createOrderTable.getTime())
//                .date(createOrderTable.getDate())
//                .menu(menu)
//                .build();
//        OrderTableDTO orderTableDTO = orderTableMapper.toOrderTableDTO(orderTableRepository.save(orderTable));
//        MailStructure mailStructure = new MailStructure();
//        mailStructure.setSubject("Order Table Created");
//        mailStructure.setMessage("Your order has been created successfully. Order details: \nName: " + orderTable.getName() +
//                "\nDate: " + orderTable.getDate() + "\nTime: " + orderTable.getTime());
//        mailService.sendMail(orderTable.getEmail(), mailStructure);
//        return orderTableDTO;
//    }

    @Override
    public OrderTableDTO createOrderTable(CreateOrderTable createOrderTable) {
        Menu menu = menuRepository.findById(createOrderTable.getMenuId())
                .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));
        OrderTable orderTable = OrderTable.builder()
                .name(createOrderTable.getName())
                .numberOfPerson(createOrderTable.getNumberOfPerson())
                .email(createOrderTable.getEmail())
                .phone(createOrderTable.getPhone())
                .status(OrderStatus.pending)
                .time(createOrderTable.getTime())
                .date(createOrderTable.getDate())
                .menu(menu)
                .build();

        OrderTableDTO orderTableDTO = orderTableMapper.toOrderTableDTO(orderTableRepository.save(orderTable));

        MailStructure mailStructure = new MailStructure();
        mailStructure.setSubject("Order Table Created");
        mailStructure.setMessage("Your order has been created successfully. Order details: \nName: " + orderTable.getName() +
                "\nDate: " + orderTable.getDate() + "\nTime: " + orderTable.getTime());
        mailService.sendMail(orderTable.getEmail(), mailStructure);

        String adminMessage = "New table order created by " + orderTable.getName() +
                " on " + orderTable.getDate() + " at " + orderTable.getTime();
        notificationService.createNotification(adminMessage);
        return orderTableDTO;
    }

    @Override
    public OrderTableDTO getOrderTableById(Long id) {
        OrderTable orderTable = orderTableRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));
        return orderTableMapper.toOrderTableDTO(orderTable);
    }

    @Override
    public List<OrderTableDTO> getAllOrderTables() {
        return orderTableRepository.findAll().stream().map(orderTableMapper::toOrderTableDTO).collect(Collectors.toList());
    }

    @Override
    public OrderTableDTO acceptOrderTable(Long id) {
        OrderTable orderTable = orderTableRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));
        orderTable.setStatus(OrderStatus.accepted);
        OrderTableDTO acceptedOrderTableDTO = orderTableMapper.toOrderTableDTO(orderTableRepository.save(orderTable));
        MailStructure mailStructure = new MailStructure();
        mailStructure.setSubject("Order Table Accepted");
        mailStructure.setMessage("Your order has been accepted. Thank you for choosing our restaurant. \nOrder details: \nName: "
                + orderTable.getName() + "\nDate: " + orderTable.getDate() + "\nTime: " + orderTable.getTime());
        mailService.sendMail(orderTable.getEmail(), mailStructure);
        return acceptedOrderTableDTO;
    }

    @Override
    public void deleteOrderTable(Long id) {
        OrderTable orderTable = orderTableRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOTFOUND));

        orderTableRepository.delete(orderTable);
    }
}
