package com.rental.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String userName;
   // @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "address")
    private String address;
    @Column(name = "avatar")
    private String avatar;
    @Column(name = "birthday")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
    @Column(name = "phone")
    private String phone;

    @Column(name = "last_name")
    private String lastName;
 //   @Email
//    @Column( name = "email", unique = true)
    @Column( name = "email")
    private String email;
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
    //,CascadeType.MERGE,CascadeType.PERSIST
    @OneToMany(mappedBy = "user",cascade =CascadeType.ALL )
    @JsonIgnoreProperties(value = { "voucher", "user", "orderDetails" }, allowSetters = true)
    @ToString.Exclude
    private Set<Order> orders = new HashSet<>();
    @ManyToMany
    @JoinTable(name = "user__notification",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "notification_id"))
    @JsonIgnoreProperties(value = { "user" }, allowSetters = true)
    @ToString.Exclude
    private Set<Notification> notifications = new HashSet<>();
    //@JsonIgnore
    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "id") },
            inverseJoinColumns = {
                    @JoinColumn(name = "role_id", referencedColumnName = "id") })
    private Set<Role> role = new HashSet<>();

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
