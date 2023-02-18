package com.rental.repository;

import com.rental.domain.Product;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.rental.domain.Image;

import java.util.Set;


@SuppressWarnings("unused")
@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    void deleteAllByProduct(Product product);

    Set<Image> findAllByProduct(Product product);
}
