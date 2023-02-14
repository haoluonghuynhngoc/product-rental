package com.rental.web.rest;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.rental.repository.BrandRepository;
import com.rental.service.BrandService;
import com.rental.service.dto.BrandDTO;

@RestController
@RequestMapping("/api")
public class BrandResource {

    @Autowired
    private BrandService brandService;
    @Autowired
    private BrandRepository brandRepository;

    @PostMapping("/brands")
    public ResponseEntity<BrandDTO> createBrand(@RequestBody BrandDTO brandDTO) {
        if (brandDTO.getId() != null) {
            throw new IllegalArgumentException("A   : idexists");
        }
        BrandDTO result = brandService.save(brandDTO);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/brands/{id}")
    public ResponseEntity<BrandDTO> updateBrand(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody BrandDTO brandDTO) {
        if (brandDTO.getId() == null) {
            throw new IllegalArgumentException("Invalid id : idnull");
        }
        if (!Objects.equals(id, brandDTO.getId())) {
            throw new IllegalArgumentException("Invalid ID : idinvalid");
        }
        if (!brandRepository.existsById(id)) {
            throw new IllegalArgumentException("Entity not found : idnotfound");
        }
        BrandDTO result = brandService.update(brandDTO);
        return ResponseEntity.ok(result);
    }

    @PatchMapping(value = "/brands/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<BrandDTO> partialUpdateBrand(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody BrandDTO brandDTO) {

        if (brandDTO.getId() == null) {
            throw new IllegalArgumentException("Invalid id " + "idnull");
        }
        if (!Objects.equals(id, brandDTO.getId())) {
            throw new IllegalArgumentException("Invalid ID : idinvalid");
        }

        if (!brandRepository.existsById(id)) {
            throw new IllegalArgumentException("Entity not found : idnotfound");
        }

        Optional<BrandDTO> result = brandService.partialUpdate(brandDTO);

        return result.map(response -> ResponseEntity.ok().body(response))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/brands")
    public ResponseEntity<List<BrandDTO>> getAllBrands(
            @org.springdoc.api.annotations.ParameterObject Pageable pageable) {

        Page<BrandDTO> page = brandService.findAll(pageable);

        return ResponseEntity.ok(page.getContent());
    }

    @GetMapping("/brands/{id}")
    public ResponseEntity<BrandDTO> getBrand(@PathVariable Long id) {

        Optional<BrandDTO> brandDTO = brandService.findOne(id);

        return brandDTO.map(response -> ResponseEntity.ok().body(response))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    @DeleteMapping("/brands/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable Long id) {
        brandService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
