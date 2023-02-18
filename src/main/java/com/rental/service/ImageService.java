package com.rental.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rental.service.dto.ImageDTO;

public interface ImageService {

    ImageDTO save(ImageDTO imageDTO);

    Optional<ImageDTO> update(ImageDTO imageDTO);

    Optional<ImageDTO> partialUpdate(ImageDTO imageDTO);

    Page<ImageDTO> findAll(Pageable pageable);

    Optional<ImageDTO> findOne(Long id);

    void delete(Long id);
}
