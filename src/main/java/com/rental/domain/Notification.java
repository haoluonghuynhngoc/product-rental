package com.rental.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rental.domain.enums.NotificationStatus;


@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "notification")
@SuppressWarnings("common-java:DuplicatedBlocks")
@EntityListeners(AuditingEntityListener.class)
public class Notification implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", columnDefinition = "nvarchar(250)")
    private String title;

    @Column(name = "sort_description", columnDefinition = "nvarchar(250)")
    private String sortDescription;

    @Column(name = "description",columnDefinition="TEXT")
    private String description;

    @Column(name = "is_read")
    private Boolean isRead;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private NotificationStatus status;

    @Column(name = "created_date")
    @CreatedDate
    private Instant createdDate;

//    @Column(name = "created_by", columnDefinition = "nvarchar(250)")
//    private String createdBy;

    @Column(name = "modified_date")
    @LastModifiedDate
    private Instant modifiedDate;

//    @Column(name = "modified_by", columnDefinition = "nvarchar(250)")
//    private String modifiedBy;

    @ManyToMany(mappedBy = "notifications")
    @JsonIgnoreProperties(value = { "role", "orders", "notifications", "product" }, allowSetters = true)
    @ToString.Exclude
    private Set<User> users = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Notification that = (Notification) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
