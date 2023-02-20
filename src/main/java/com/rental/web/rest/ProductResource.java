package com.rental.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rental.repository.ProductRepository;
import com.rental.service.ProductService;
import com.rental.service.dto.ProductDTO;
import java.util.List;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/products")
public class ProductResource {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;
    @PostMapping("/create")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        if (productDTO.getId() != null)
            throw new IllegalArgumentException("A new product cannot already have an ID : exists  id ");
        return ResponseEntity.status(HttpStatus.OK).body(productService.save(productDTO));
    }
    @PutMapping("/update")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO) {
        if (productDTO.getId() == null)
            throw new IllegalArgumentException("ID can not be null");
        if (!productRepository.existsById(productDTO.getId()))
            throw new IllegalArgumentException("Entity not found : Can not find the id in the data ");
        return productService.update(productDTO).map(
                productData -> ResponseEntity.status(HttpStatus.OK).body(productData)).orElseThrow(
                () -> new IllegalArgumentException("Cant not update product")
        );
    }
    @GetMapping("/getOne/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
        if (!productRepository.existsById(id))
            throw new IllegalArgumentException("Cant not find the Product have id : " + id + " in the data ");
        return productService.findOne(id).map(
                        productData -> ResponseEntity.status(HttpStatus.OK).body(productData))
                .orElseThrow(() -> new IllegalArgumentException("Can not find the product "));
    }
    @GetMapping("/getAllProduct")
    public ResponseEntity<List<ProductDTO>> getAllCategories(
            @org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        Page<ProductDTO> page = productService.findAll(pageable);
        if (page.isEmpty())
            throw new IllegalArgumentException("Cant not find any product in the data ");
        return ResponseEntity.status(HttpStatus.OK).body(page.getContent());
    }
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (!productRepository.existsById(id))
            throw new IllegalArgumentException("Cant not find the Product have id : " + id + " in the data ");
        productService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);

    }

}
