package com.rental.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.rental.domain.Image;

/**
 * Spring Data JPA repository for the Image entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}
