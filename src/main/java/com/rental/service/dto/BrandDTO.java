package com.rental.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.swp391.domain.Brand} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BrandDTO implements Serializable {

    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BrandDTO)) {
            return false;
        }

        BrandDTO brandDTO = (BrandDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, brandDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BrandDTO{" +
                "id=" + getId() +
                ", name='" + getName() + "'" +
                "}";
    }
}
