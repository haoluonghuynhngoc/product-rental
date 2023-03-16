package com.rental.service;

import com.rental.service.dto.InformationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface InformationService {
    Optional<InformationDTO> getOneInfo(Long id);

    Page<InformationDTO> findAll(Pageable pageable);

    Integer findALLInfoIsRead();
}
