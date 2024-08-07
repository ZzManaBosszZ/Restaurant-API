package com.restaurant.restaurantapi.entities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "order_table")
@Builder
public class OrderTable extends BaseEntity {
    @Column(name = "name", length = 255)
    private String name;

    @Column(name = "number_of_person")
    private Integer numberOfPerson;

    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "phone", length = 255)
    private String phone;

    @Column(name = "time")
    private Time time;

    @Column(name = "date")
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;
}
