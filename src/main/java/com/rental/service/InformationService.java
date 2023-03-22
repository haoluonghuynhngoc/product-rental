package com.rental.service;

import com.rental.domain.enums.InformationStatus;
import com.rental.service.dto.InformationDTO;
import com.rental.service.dto.PagingResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface InformationService {
    PagingResponse<InformationDTO> findAllInfoAdmin(Pageable pageable);
    public PagingResponse<InformationDTO> findAllInfoUser(Pageable pageable,Long id);
    Integer findAllInfoIsReadByUser(Long id, InformationStatus status);
    Optional<InformationDTO> findDetailInfo(Long id);
}
