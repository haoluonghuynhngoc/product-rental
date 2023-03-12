package com.rental.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rental.domain.enums.InformationStatus;
import com.rental.domain.enums.NotificationStatus;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "information")
@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
@RequiredArgsConstructor
@SuppressWarnings("common-java:DuplicatedBlocks")
@EntityListeners(AuditingEntityListener.class)
public class Information {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", columnDefinition = "nvarchar(250)")
    private String title;
    @Column(name = "description",columnDefinition="nvarchar(max)")
    private String description;
    @Column(name = "is_read")
    private Boolean isRead;
    @Column(name = "created_date")
    @CreatedDate
    private Instant createdDate;
    @Column(name = "modified_date")
    @LastModifiedDate
    private Instant modifiedDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties(value = { "images", "brand", "category", "orderDetails" }, allowSetters = true)
    private User user;
    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnoreProperties(value = { "order_details", "product", "users" }, allowSetters = true)
    private Order order;
}
