package com.rental.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.rental.service.dto.BrandDTO;

public interface BrandService {
    BrandDTO save(BrandDTO brandDTO);
    Optional<BrandDTO> update(BrandDTO brandDTO);
    Page<BrandDTO> findAll(Pageable pageable);
    Optional<BrandDTO> findOne(Long id);
    void delete(Long id);

}
