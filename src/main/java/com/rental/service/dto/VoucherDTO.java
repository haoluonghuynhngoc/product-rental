package com.rental.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import com.rental.domain.enumeration.VorcherStatus;

/**
 * A DTO for the {@link com.swp391.domain.Voucher} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VoucherDTO implements Serializable {

    private Long id;

    private Double discount;

    private String name;

    private Instant startDate;

    private Instant endDate;

    private VorcherStatus status;

    private Instant createdDate;

    private String createdBy;

    private Instant modifiedDate;

    private String modifiedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public VorcherStatus getStatus() {
        return status;
    }

    public void setStatus(VorcherStatus status) {
        this.status = status;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VoucherDTO)) {
            return false;
        }

        VoucherDTO voucherDTO = (VoucherDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, voucherDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VoucherDTO{" +
                "id=" + getId() +
                ", discount=" + getDiscount() +
                ", name='" + getName() + "'" +
                ", startDate='" + getStartDate() + "'" +
                ", endDate='" + getEndDate() + "'" +
                ", status='" + getStatus() + "'" +
                ", createdDate='" + getCreatedDate() + "'" +
                ", createdBy='" + getCreatedBy() + "'" +
                ", modifiedDate='" + getModifiedDate() + "'" +
                ", modifiedBy='" + getModifiedBy() + "'" +
                "}";
    }
}
