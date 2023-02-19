package com.rental.service;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.rental.service.dto.VoucherDTO;
public interface VoucherService {
    VoucherDTO save(VoucherDTO voucherDTO);
    Optional<VoucherDTO> update(VoucherDTO voucherDTO);
    Page<VoucherDTO> findAll(Pageable pageable);

    Optional<VoucherDTO> findOne(Long id);

    void delete(Long id);
}
