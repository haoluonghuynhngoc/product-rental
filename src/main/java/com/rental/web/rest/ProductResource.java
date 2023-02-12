package com.rental.web.rest;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

import com.rental.repository.ProductRepository;
import com.rental.service.ProductService;
import com.rental.service.dto.ProductDTO;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class ProductResource {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;


    @PostMapping("/products")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {

        if (productDTO.getId() != null) {
            throw new IllegalArgumentException("A new product cannot already have an ID  : idexists");
        }
        ProductDTO result = productService.save(productDTO);
        return ResponseEntity.ok()
                .body(result);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody ProductDTO productDTO) {

        if (productDTO.getId() == null) {
            throw new IllegalArgumentException("Invalid id  : idnull");
        }
        if (!Objects.equals(id, productDTO.getId())) {
            throw new IllegalArgumentException("Invalid ID  : idinvalid");
        }

        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Entity not found  : idnotfound");
        }

        ProductDTO result = productService.update(productDTO);
        return ResponseEntity
                .ok()
                .body(result);
    }

    @PatchMapping(value = "/products/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<ProductDTO> partialUpdateProduct(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody ProductDTO productDTO) {

        if (productDTO.getId() == null) {
            throw new IllegalArgumentException("Invalid id  : idnull");
        }
        if (!Objects.equals(id, productDTO.getId())) {
            throw new IllegalArgumentException("Invalid ID  : idinvalid");
        }

        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Entity not found  : idnotfound");
        }

        Optional<ProductDTO> result = productService.partialUpdate(productDTO);
        return result.map(response -> ResponseEntity.ok().body(response))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {

        Optional<ProductDTO> productDTO = productService.findOne(id);
        return productDTO.map(response -> ResponseEntity.ok().body(response))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {

        productService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
