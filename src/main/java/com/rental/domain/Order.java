package com.rental.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rental.domain.enums.OrderStatus;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "orders")
@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
@RequiredArgsConstructor
@SuppressWarnings("common-java:DuplicatedBlocks")
@EntityListeners(AuditingEntityListener.class)
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "total_quantity")
    private Integer totalQuantity;
    @Column(name = "total_price")
    private Double totalPrice;
    @Column(name = "message", columnDefinition = "nvarchar(max)")
    private String message;
    @Column(name = "address", columnDefinition = "nvarchar(300)")
    private String address;
    @Column(name = "phone", columnDefinition = "varchar(200)")
    private String phone;
    @Column(name = "name", columnDefinition = "nvarchar(500)")
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;
    @Column(name = "created_date")
    @CreatedDate
    private Instant createdDate;
    @Column(name = "modified_date")
    @LastModifiedDate
    private Instant modifiedDate;
    @ManyToOne
    @JoinColumn(name = "voucher_id")
    @JsonIgnoreProperties(value = {"orders"}, allowSetters = true)
    private Voucher voucher;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties(value = {"orders", "notifications"}, allowSetters = true)
    private User user;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"order", "product"}, allowSetters = true)
    @ToString.Exclude
    private Set<OrderDetails> orderDetails = new HashSet<>();
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Order order = (Order) o;
        return id != null && Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
