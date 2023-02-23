package com.rental.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rental.domain.enums.UserStatus;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "phone")
    private String phone;

    @Column(name = "last_name", columnDefinition = "nvarchar(250)")
    private String lastName;


    @Column(length = 254)
    private String email;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "created_date")
    @CreatedDate
    private Instant createdDate;//neu sai thi doi thanh date

//    @Column(name = "created_by", columnDefinition = "nvarchar(250)")
//    private String createdBy;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserStatus status;

    @Column(name = "modified_date")
    @LastModifiedDate
    private Instant modifiedDate;//neu sai thi doi thanh date

//    @Column(name = "modified_by", columnDefinition = "nvarchar(250)")
//    private String modifiedBy;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
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
