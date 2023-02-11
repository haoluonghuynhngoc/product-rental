package com.rental.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rental.domain.enumeration.NotificationStatus;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Notification.
 */
@Entity
@Table(name = "notification")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Notification implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "sort_descripsion")
    private String sortDescripsion;

    @Column(name = "description")
    private String description;

    @Column(name = "is_read")
    private Boolean isRead;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private NotificationStatus status;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified_date")
    private Instant modifiedDate;

    @Column(name = "modified_by")
    private String modifiedBy;

    @ManyToMany(mappedBy = "notifications")
    @JsonIgnoreProperties(value = { "role", "orders", "notifications" }, allowSetters = true)
    private Set<User> user = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Notification id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Notification title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSortDescripsion() {
        return this.sortDescripsion;
    }

    public Notification sortDescripsion(String sortDescripsion) {
        this.setSortDescripsion(sortDescripsion);
        return this;
    }

    public void setSortDescripsion(String sortDescripsion) {
        this.sortDescripsion = sortDescripsion;
    }

    public String getDescription() {
        return this.description;
    }

    public Notification description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsRead() {
        return this.isRead;
    }

    public Notification isRead(Boolean isRead) {
        this.setIsRead(isRead);
        return this;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public NotificationStatus getStatus() {
        return this.status;
    }

    public Notification status(NotificationStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Notification createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Notification createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getModifiedDate() {
        return this.modifiedDate;
    }

    public Notification modifiedDate(Instant modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public Notification modifiedBy(String modifiedBy) {
        this.setModifiedBy(modifiedBy);
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Set<User> getUser() {
        return this.user;
    }

    public void setUser(Set<User> user) {
        if (this.user != null) {
            this.user.forEach(i -> i.removeNotification(this));
        }
        if (user != null) {
            user.forEach(i -> i.addNotification(this));
        }
        this.user = user;
    }

    public Notification user(Set<User> user) {
        this.setUser(user);
        return this;
    }

    public Notification addUser(User applicationUser) {
        this.user.add(applicationUser);
        applicationUser.getNotifications().add(this);
        return this;
    }

    public Notification removeAppUser(User applicationUser) {
        this.user.remove(applicationUser);
        applicationUser.getNotifications().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Notification)) {
            return false;
        }
        return id != null && id.equals(((Notification) o).id);
    }

    @Override
    public int hashCode() {
        // see
        // https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Notification{" +
                "id=" + getId() +
                ", title='" + getTitle() + "'" +
                ", sortDescripsion='" + getSortDescripsion() + "'" +
                ", description='" + getDescription() + "'" +
                ", isRead='" + getIsRead() + "'" +
                ", status='" + getStatus() + "'" +
                ", createdDate='" + getCreatedDate() + "'" +
                ", createdBy='" + getCreatedBy() + "'" +
                ", modifiedDate='" + getModifiedDate() + "'" +
                ", modifiedBy='" + getModifiedBy() + "'" +
                "}";
    }
}
