package com.rental.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
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
    @NotNull
    @Column(name = "password",nullable = false)
    private String password;

    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "address")
    private String address;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "birthday")
    private Instant birthday;

    @Column(name = "phone")
    private String phone;

    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;

    @Email
    @Column(length = 254)
    private String email;

    @Size(max = 256)
    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "created_date")
    @CreatedDate
    private Instant createdDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified_date")
    @LastModifiedDate
    private Instant modifiedDate;

    @Column(name = "modified_by")
    private String modifiedBy;

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties(value = { "voucher", "user", "orderDetails" }, allowSetters = true)
    @ToString.Exclude
    private Set<Order> orders = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "user_notification", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "notification_id"))
    @JsonIgnoreProperties(value = { "user" }, allowSetters = true)
    @ToString.Exclude
    private Set<Notification> notifications = new HashSet<>();

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = {
                    @JoinColumn(name = "role_id", referencedColumnName = "id") })
    @BatchSize(size = 20)
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
