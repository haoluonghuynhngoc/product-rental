package com.rental.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rental.service.dto.VoucherDTO;

/**
 * Service Interface for managing {@link com.swp391.domain.Voucher}.
 */
public interface VoucherService {

    VoucherDTO save(VoucherDTO voucherDTO);

    VoucherDTO update(VoucherDTO voucherDTO);

    Optional<VoucherDTO> partialUpdate(VoucherDTO voucherDTO);

    Page<VoucherDTO> findAll(Pageable pageable);

    Optional<VoucherDTO> findOne(Long id);

    void delete(Long id);
}
