package com.rental.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.swp391.domain.OrderDetails} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrderDetailsDTO implements Serializable {

    private Long id;

    private String name;

    private Integer quantity;

    private Double price;

    private OrderDTO order;

    private ProductDTO product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public OrderDTO getOrder() {
        return order;
    }

    public void setOrder(OrderDTO order) {
        this.order = order;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderDetailsDTO)) {
            return false;
        }

        OrderDetailsDTO orderDetailsDTO = (OrderDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, orderDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrderDetailsDTO{" +
                "id=" + getId() +
                ", name='" + getName() + "'" +
                ", quantity=" + getQuantity() +
                ", price=" + getPrice() +
                ", order=" + getOrder() +
                ", product=" + getProduct() +
                "}";
    }
}
