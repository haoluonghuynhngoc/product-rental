package com.rental.web.rest;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.rental.repository.VoucherRepository;
import com.rental.service.VoucherService;
import com.rental.service.dto.VoucherDTO;

/**
 * REST controller for managing {@link com.swp391.domain.Voucher}.
 */
@RestController
@RequestMapping("/api")
public class VoucherResource {

    private final VoucherService voucherService;

    private final VoucherRepository voucherRepository;

    public VoucherResource(VoucherService voucherService, VoucherRepository voucherRepository) {
        this.voucherService = voucherService;
        this.voucherRepository = voucherRepository;
    }

    @PostMapping("/vouchers")
    public ResponseEntity<VoucherDTO> createVoucher(@RequestBody VoucherDTO voucherDTO) {

        if (voucherDTO.getId() != null) {
            throw new IllegalArgumentException("A new voucher cannot already have an ID  : idexists");
        }
        VoucherDTO result = voucherService.save(voucherDTO);
        return ResponseEntity
                .ok().body(result);
    }

    @PutMapping("/vouchers/{id}")
    public ResponseEntity<VoucherDTO> updateVoucher(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody VoucherDTO voucherDTO) {

        if (voucherDTO.getId() == null) {
            throw new IllegalArgumentException("Invalid id  : idnull");
        }
        if (!Objects.equals(id, voucherDTO.getId())) {
            throw new IllegalArgumentException("Invalid ID  : idinvalid");
        }

        if (!voucherRepository.existsById(id)) {
            throw new IllegalArgumentException("Entity not found  : idnotfound");
        }

        VoucherDTO result = voucherService.update(voucherDTO);
        return ResponseEntity
                .ok()
                .body(result);
    }

    @PatchMapping(value = "/vouchers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<VoucherDTO> partialUpdateVoucher(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody VoucherDTO voucherDTO) {

        if (voucherDTO.getId() == null) {
            throw new IllegalArgumentException("Invalid id  : idnull");
        }
        if (!Objects.equals(id, voucherDTO.getId())) {
            throw new IllegalArgumentException("Invalid ID  : idinvalid");
        }

        if (!voucherRepository.existsById(id)) {
            throw new IllegalArgumentException("Entity not found  : idnotfound");
        }

        Optional<VoucherDTO> result = voucherService.partialUpdate(voucherDTO);

        return result.map(response -> ResponseEntity.ok().body(response))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/vouchers")
    public ResponseEntity<List<VoucherDTO>> getAllVouchers(
            @org.springdoc.api.annotations.ParameterObject Pageable pageable) {

        Page<VoucherDTO> page = voucherService.findAll(pageable);
        return ResponseEntity.ok().body(page.getContent());
    }

    @GetMapping("/vouchers/{id}")
    public ResponseEntity<VoucherDTO> getVoucher(@PathVariable Long id) {

        Optional<VoucherDTO> voucherDTO = voucherService.findOne(id);
        return voucherDTO.map(response -> ResponseEntity.ok().body(response))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/vouchers/{id}")
    public ResponseEntity<Void> deleteVoucher(@PathVariable Long id) {

        voucherService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
