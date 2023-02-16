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
@RequestMapping("/api/brands")
public class BrandResource {

    @Autowired
    private BrandService brandService;
    @Autowired
    private BrandRepository brandRepository;

    @PostMapping("/create")
    public ResponseEntity<BrandDTO> createBrand(@RequestBody BrandDTO brandDTO) {
        if (brandDTO.getId() != null)
            throw new IllegalArgumentException("A new brand cannot already have an ID  : exists the id ");
        if (brandRepository.findByName(brandDTO.getName()) != null)
            throw new IllegalArgumentException("Exist name brand ");
        return ResponseEntity.status(HttpStatus.OK).body(brandService.save(brandDTO));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<BrandDTO> updateBrand(@RequestBody BrandDTO brandDTO) {
        if (brandDTO.getId() == null)
            throw new IllegalArgumentException("The Id brand is null");
        if (!brandRepository.existsById(brandDTO.getId())) {
            throw new IllegalArgumentException("Can not find the brand have Id : " + brandDTO.getId());
        }
        return brandService.update(brandDTO).map(
                brandData -> ResponseEntity.status(HttpStatus.OK).body(brandData)).orElseThrow(
                () -> new IllegalArgumentException("Cant not update Brand")
        );
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<BrandDTO>> getAllBrands(
            @org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        Page<BrandDTO> page = brandService.findAll(pageable);
        if (page.isEmpty())
            throw new IllegalArgumentException("Cant not find any category in the data");
        return ResponseEntity.status(HttpStatus.OK).body(page.getContent());
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<BrandDTO> getBrand(@PathVariable Long id) {
        if (!brandRepository.existsById(id))
            throw new IllegalArgumentException("Cant not find the Id : "+ id +" in the data");
        return brandService.findOne(id).map(response -> ResponseEntity.status(HttpStatus.OK).body(response))
                .orElseThrow(() -> new IllegalArgumentException("Cant not get brand "));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable Long id) {
        if (!brandRepository.existsById(id))
            throw new IllegalArgumentException("Can not find the brand have Id : " + id + " In the data ");
        brandService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
