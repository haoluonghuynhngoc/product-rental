package com.rental.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "cart_items")
@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
@RequiredArgsConstructor
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CartItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnoreProperties(value = { "images", "brand", "category", "orderDetails" }, allowSetters = true)
    private Product product;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties(value = { "images", "brand", "category", "orderDetails" }, allowSetters = true)
    private User user;

    @Column(name = "quantity")
    private Integer quantity;
}
