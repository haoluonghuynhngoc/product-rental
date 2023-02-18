package com.rental.service.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rental.domain.Voucher;
import com.rental.repository.VoucherRepository;
import com.rental.service.VoucherService;
import com.rental.service.dto.VoucherDTO;

/**
 * Service Implementation for managing {@link Voucher}.
 */
@Service
@Transactional
public class VoucherServiceImpl implements VoucherService {

    @Autowired
    private ModelMapper modelMapper;

    private final VoucherRepository voucherRepository;

    public VoucherServiceImpl(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;

    }

    @Override
    public VoucherDTO save(VoucherDTO voucherDTO) {

        Voucher voucher = modelMapper.map(voucherDTO, Voucher.class);
        voucher = voucherRepository.save(voucher);
        return modelMapper.map(voucher, VoucherDTO.class);
    }

    @Override
    public VoucherDTO update(VoucherDTO voucherDTO) {
        Voucher voucher = modelMapper.map(voucherDTO, Voucher.class);
        voucher = voucherRepository.save(voucher);
        return modelMapper.map(voucher, VoucherDTO.class);
    }

    @Override
    public Optional<VoucherDTO> partialUpdate(VoucherDTO voucherDTO) {

        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VoucherDTO> findAll(Pageable pageable) {

        return voucherRepository.findAll(pageable).map(v -> {
            return modelMapper.map(v, VoucherDTO.class);

        });
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VoucherDTO> findOne(Long id) {

        return voucherRepository.findById(id).map(v -> {
            return modelMapper.map(v, VoucherDTO.class);
        });
    }

    @Override
    public void delete(Long id) {

        voucherRepository.deleteById(id);
    }
}
