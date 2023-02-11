package com.rental.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rental.service.dto.BrandDTO;

public interface BrandService {

    BrandDTO save(BrandDTO brandDTO);

    BrandDTO update(BrandDTO brandDTO);

    Optional<BrandDTO> partialUpdate(BrandDTO brandDTO);

    Page<BrandDTO> findAll(Pageable pageable);

    List<BrandDTO> findAllList();

    Optional<BrandDTO> findOne(Long id);

    void delete(Long id);

}
