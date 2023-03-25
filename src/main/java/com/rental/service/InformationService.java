package com.rental.service;

import com.rental.domain.enums.InformationStatus;
import com.rental.service.dto.InformationDTO;
import com.rental.service.dto.InformationShowDTO;
import com.rental.service.dto.PagingResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface InformationService {
    PagingResponse<InformationShowDTO> findAllInfoAdmin(Pageable pageable);
    public PagingResponse<InformationShowDTO> findAllInfoUser(Pageable pageable, Long id);
    Integer findAllInfoIsReadByUser(Long id, InformationStatus status);
    Optional<InformationShowDTO> findDetailInfo(Long id);
}
