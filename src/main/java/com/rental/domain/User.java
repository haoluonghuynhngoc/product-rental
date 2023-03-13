package com.rental.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rental.domain.enums.UserStatus;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    private String username;

    @JsonIgnore
    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "first_name", columnDefinition = "nvarchar(60)")
    private String firstName;

    @Column(name = "address", columnDefinition = "nvarchar(250)")
    private String address;

    @Column(name = "avatar", columnDefinition = "nvarchar(max)")
    private String avatar;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "phone")
    private String phone;

    @Column(name = "last_name", columnDefinition = "nvarchar(250)")
    private String lastName;


    @Column(length = 254)
    private String email;

    @Column(name = "image_url", columnDefinition = "nvarchar(max)")
    private String imageUrl;

    @Column(name = "created_date")
    @CreatedDate
    private Instant createdDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserStatus status;

    @Column(name = "modified_date")
    @LastModifiedDate
    private Instant modifiedDate;
//,orphanRemoval = true
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = { "voucher", "user", "orderDetails" }, allowSetters = true)
    private Set<Order> orders = new HashSet<>();

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_notification",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "notification_id"))
    @JsonIgnoreProperties(value = { "user","products" }, allowSetters = true)
    @ToString.Exclude
    private Set<Notification> notifications = new HashSet<>();

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "user_role",
            joinColumns =  @JoinColumn(name = "user_id"),
           inverseJoinColumns =@JoinColumn(name = "role_id"))
    @ToString.Exclude
    private Set<Role> role = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties(value = { "voucher", "user", "orderDetails" }, allowSetters = true)
    @ToString.Exclude
    private Set<Product> products = new HashSet<>();
    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private Set<Blog> blog = new HashSet<>();


    //=====
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnoreProperties(value = { "voucher", "user", "orderDetails" }, allowSetters = true)
    private Set<CartItems> cartItems = new HashSet<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnoreProperties(value = { "voucher", "user", "orderDetails" }, allowSetters = true)
    private List<Information> information = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
