package com.rental.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;


@Entity
@Table(name = "order_details")
@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
@RequiredArgsConstructor
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrderDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @Column(name = "name", columnDefinition = "nvarchar(250)")
//    private String name;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "price")
    private Double price;
    @Column(name = "deposit")
    private Double deposit;
    @Column(name = "order_borrow_date")//
    private Date orderBorrowDate;
    @Column(name = "order_return_date")//
    private Date orderReturnDate;
    @Column(name = "image_borrow")//
    private String imageBorrow;
    @Column(name = "image_return")//
    private String imageReturn;
    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnoreProperties(value = { "voucher", "user", "orderDetails" }, allowSetters = true)
    private Order order;
    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnoreProperties(value = { "images", "brand", "category", "orderDetails" }, allowSetters = true)
    private Product product;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        OrderDetails that = (OrderDetails) o;
        return id != null && Objects.equals(id, that.id);
    }
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
