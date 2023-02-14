package com.rental.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import com.rental.domain.enumeration.OrderStatus;

/**
 * A DTO for the {@link com.swp391.domain.Order} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrderDTO implements Serializable {

    private Long id;

    private Integer totalQuantity;

    private Double totalPrice;

    private Instant orderBrorrowDate;

    private Instant orderReturnDate;

    private OrderStatus status;

    private Instant createdDate;

    private String createdBy;

    private Instant modifiedDate;

    private String modifiedBy;

    private VoucherDTO voucher;

    private AccountDTO appUser;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Instant getOrderBrorrowDate() {
        return orderBrorrowDate;
    }

    public void setOrderBrorrowDate(Instant orderBrorrowDate) {
        this.orderBrorrowDate = orderBrorrowDate;
    }

    public Instant getOrderReturnDate() {
        return orderReturnDate;
    }

    public void setOrderReturnDate(Instant orderReturnDate) {
        this.orderReturnDate = orderReturnDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public VoucherDTO getVoucher() {
        return voucher;
    }

    public void setVoucher(VoucherDTO voucher) {
        this.voucher = voucher;
    }

    public UserDTO getAppUser() {
        return appUser;
    }

    public void setAppUser(UserDTO appUser) {
        this.appUser = appUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderDTO)) {
            return false;
        }

        OrderDTO orderDTO = (OrderDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, orderDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderDTO{" +
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
                ", voucher=" + getVoucher() +
                ", appUser=" + getAppUser() +
                "}";
    }
}
