package com.rental.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rental.domain.enums.BlogStatus;
import com.rental.domain.enums.NotificationStatus;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
@RequiredArgsConstructor
@Table(name = "blog")
@SuppressWarnings("common-java:DuplicatedBlocks")
@EntityListeners(AuditingEntityListener.class)
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "image_title")
    private String imageTitle;
    @Column(name = "image_cover")
    private String imageCover;
    @Column(name = "title", columnDefinition = "nvarchar(250)")
    private String title;
    @Column(name = "author", columnDefinition = "nvarchar(100)")
    private String author;
    @Column(name = "description", columnDefinition = "nvarchar(max)")
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private BlogStatus status;

    @Column(name = "created_date")
    @CreatedDate
    private Instant createdDate;

    @Column(name = "modified_date")
    @LastModifiedDate
    private Instant modifiedDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
