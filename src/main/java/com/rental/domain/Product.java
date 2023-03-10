package com.rental.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rental.domain.enums.ProductStatus;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.*;


@Entity
@Table(name = "product")
@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
@RequiredArgsConstructor
@SuppressWarnings("common-java:DuplicatedBlocks")
@EntityListeners(AuditingEntityListener.class)
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", columnDefinition = "nvarchar(250)")
    private String name;
    @Column(name = "price")
    private Double price;
    @Column(name = "deposit")
    private Double deposit;
    @Column(name = "description" ,columnDefinition="nvarchar(max)")
    private String description;
    @Column(name = "quantity")
    private Integer quantity;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProductStatus status;
    @Column(name = "created_date")
    @CreatedDate
    private Instant createdDate;
    @Column(name = "modified_date")
    @LastModifiedDate
    private Instant modifiedDate;
    @OneToMany( mappedBy = "product",cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REMOVE})
    @JsonIgnoreProperties(value = {"product"}, allowSetters = true)
    @ToString.Exclude
    private List<Image> images = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnoreProperties(value = {"products"}, allowSetters = true)
    private Category category;
    @OneToMany(mappedBy = "product")
    @JsonIgnoreProperties(value = {"order", "product"}, allowSetters = true)
    @ToString.Exclude
    private Set<OrderDetails> orderDetails = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties(value = {"orders", "notifications"}, allowSetters = true)
    private User user;

//===
    @OneToMany(mappedBy = "product")
    @JsonIgnoreProperties(value = {"order", "product"}, allowSetters = true)
    @ToString.Exclude
    private Set<CartItems> cartItems = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here


}
