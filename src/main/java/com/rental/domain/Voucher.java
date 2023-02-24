package com.rental.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rental.domain.enums.VorcherStatus;
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
@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
@RequiredArgsConstructor
@Table(name = "voucher")
@SuppressWarnings("common-java:DuplicatedBlocks")
@EntityListeners(AuditingEntityListener.class)
public class Voucher implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "name", columnDefinition = "nvarchar(200)")
    private String name;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private VorcherStatus status;

    @Column(name = "created_date")
    @CreatedDate
    private Instant createdDate;

    @Column(name = "modified_date")
    @LastModifiedDate
    private Instant modifiedDate;

    @OneToMany(mappedBy = "voucher",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnoreProperties(value = { "voucher", "user", "orderDetails" }, allowSetters = true)
    @ToString.Exclude
    private Set<Order> orders = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Voucher voucher = (Voucher) o;
        return id != null && Objects.equals(id, voucher.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
