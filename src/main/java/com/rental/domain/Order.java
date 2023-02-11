package com.rental.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rental.domain.enumeration.OrderStatus;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Order.
 */
@Entity
@Table(name = "orders")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_quantity")
    private Integer totalQuantity;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "order_brorrow_date")
    private Instant orderBrorrowDate;

    @Column(name = "order_return_date")
    private Instant orderReturnDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified_date")
    private Instant modifiedDate;

    @Column(name = "modified_by")
    private String modifiedBy;

    @ManyToOne
    @JoinColumn(name = "voucher_id")
    @JsonIgnoreProperties(value = { "orders" }, allowSetters = true)
    private Voucher voucher;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties(value = { "orders", "notifications" }, allowSetters = true)
    private User user;

    @OneToMany(mappedBy = "order")
    @JsonIgnoreProperties(value = { "order", "product" }, allowSetters = true)
    private Set<OrderDetails> orderDetails = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Order id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTotalQuantity() {
        return this.totalQuantity;
    }

    public Order totalQuantity(Integer totalQuantity) {
        this.setTotalQuantity(totalQuantity);
        return this;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Double getTotalPrice() {
        return this.totalPrice;
    }

    public Order totalPrice(Double totalPrice) {
        this.setTotalPrice(totalPrice);
        return this;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Instant getOrderBrorrowDate() {
        return this.orderBrorrowDate;
    }

    public Order orderBrorrowDate(Instant orderBrorrowDate) {
        this.setOrderBrorrowDate(orderBrorrowDate);
        return this;
    }

    public void setOrderBrorrowDate(Instant orderBrorrowDate) {
        this.orderBrorrowDate = orderBrorrowDate;
    }

    public Instant getOrderReturnDate() {
        return this.orderReturnDate;
    }

    public Order orderReturnDate(Instant orderReturnDate) {
        this.setOrderReturnDate(orderReturnDate);
        return this;
    }

    public void setOrderReturnDate(Instant orderReturnDate) {
        this.orderReturnDate = orderReturnDate;
    }

    public OrderStatus getStatus() {
        return this.status;
    }

    public Order status(OrderStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Order createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Order createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getModifiedDate() {
        return this.modifiedDate;
    }

    public Order modifiedDate(Instant modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public Order modifiedBy(String modifiedBy) {
        this.setModifiedBy(modifiedBy);
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Voucher getVoucher() {
        return this.voucher;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

    public Order voucher(Voucher voucher) {
        this.setVoucher(voucher);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User applicationUser) {
        this.user = applicationUser;
    }

    public Order appUser(User applicationUser) {
        this.setUser(applicationUser);
        return this;
    }

    public Set<OrderDetails> getOrderDetails() {
        return this.orderDetails;
    }

    public void setOrderDetails(Set<OrderDetails> orderDetails) {
        if (this.orderDetails != null) {
            this.orderDetails.forEach(i -> i.setOrder(null));
        }
        if (orderDetails != null) {
            orderDetails.forEach(i -> i.setOrder(this));
        }
        this.orderDetails = orderDetails;
    }

    public Order orderDetails(Set<OrderDetails> orderDetails) {
        this.setOrderDetails(orderDetails);
        return this;
    }

    public Order addOrderDetail(OrderDetails orderDetails) {
        this.orderDetails.add(orderDetails);
        orderDetails.setOrder(this);
        return this;
    }

    public Order removeOrderDetail(OrderDetails orderDetails) {
        this.orderDetails.remove(orderDetails);
        orderDetails.setOrder(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Order)) {
            return false;
        }
        return id != null && id.equals(((Order) o).id);
    }

    @Override
    public int hashCode() {
        // see
        // https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Order{" +
                "id=" + getId() +
                ", totalQuantity=" + getTotalQuantity() +
                ", totalPrice=" + getTotalPrice() +
                ", orderBrorrowDate='" + getOrderBrorrowDate() + "'" +
                ", orderReturnDate='" + getOrderReturnDate() + "'" +
                ", status='" + getStatus() + "'" +
                ", createdDate='" + getCreatedDate() + "'" +
                ", createdBy='" + getCreatedBy() + "'" +
                ", modifiedDate='" + getModifiedDate() + "'" +
                ", modifiedBy='" + getModifiedBy() + "'" +
                "}";
    }
}
