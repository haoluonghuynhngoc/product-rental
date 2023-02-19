package com.rental.web.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rental.repository.VoucherRepository;
import com.rental.service.VoucherService;
import com.rental.service.dto.VoucherDTO;

@RestController
@RequestMapping("/api/voucher")
public class VoucherResource {

    @Autowired
    private VoucherService voucherService;
    @Autowired
    private VoucherRepository voucherRepository;

    @PostMapping("/create")
    public ResponseEntity<VoucherDTO> createVoucher(@RequestBody VoucherDTO voucherDTO) {
        if (voucherDTO.getId() != null) {
            throw new IllegalArgumentException("A new voucher cannot already have an ID  ");
        }
        return ResponseEntity.status(HttpStatus.OK).body(voucherService.save(voucherDTO));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<VoucherDTO> updateVoucher(@RequestBody VoucherDTO voucherDTO) {
        if (!voucherRepository.existsById(voucherDTO.getId())) {
            throw new IllegalArgumentException("Cant not find the voucher have Id :" + voucherDTO.getId());
        }
        return voucherService.update(voucherDTO).map(
                voucherData -> ResponseEntity.status(HttpStatus.OK).body(voucherData)).orElseThrow(
                () -> new IllegalArgumentException("Cant not update voucher have Id :" + voucherDTO.getId()));
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<VoucherDTO>> getAllVouchers(
            @org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        Page<VoucherDTO> page = voucherService.findAll(pageable);
        if (page.isEmpty())
            throw new IllegalArgumentException("Cant not find any voucher in data ");
        return ResponseEntity.ok().body(page.getContent());
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<VoucherDTO> getVoucher(@PathVariable Long id) {
        if (!voucherRepository.existsById(id))
            throw new IllegalArgumentException("Cant not find the voucher have Id : " + id);
        return voucherService.findOne(id).map(
                voucherData -> ResponseEntity.status(HttpStatus.OK).body(voucherData)
        ).orElseThrow(
                () -> new IllegalArgumentException("Cant not get the voucher ")
        );
    }

    @DeleteMapping("/vouchers/{id}")
    public ResponseEntity<Void> deleteVoucher(@PathVariable Long id) {
        voucherService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);

    }
}
