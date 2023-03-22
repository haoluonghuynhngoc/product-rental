package com.rental.service;

import com.rental.service.dto.InformationDTO;
import com.rental.service.dto.PagingResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface InformationService {
    PagingResponse<InformationDTO> findAllInfoAdmin(Pageable pageable);

    Integer findAllInfoIsReadByUser(Long id);
    Optional<InformationDTO> findDetailInfo(Long id);
}
