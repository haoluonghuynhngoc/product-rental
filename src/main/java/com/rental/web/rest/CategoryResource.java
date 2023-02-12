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

import com.rental.repository.CategoryRepository;
import com.rental.service.CategoryService;
import com.rental.service.dto.CategoryDTO;

@RestController
@RequestMapping("/api")
public class CategoryResource {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;


    @PostMapping("/categories")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {

        if (categoryDTO.getId() != null) {
            throw new IllegalArgumentException("A new category cannot already have an ID  : idexists");
        }
        CategoryDTO result = categoryService.save(categoryDTO);
        return ResponseEntity.ok(result);

    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody CategoryDTO categoryDTO) {

        if (categoryDTO.getId() == null) {
            throw new IllegalArgumentException("Invalid id  : idnull");
        }
        if (!Objects.equals(id, categoryDTO.getId())) {
            throw new IllegalArgumentException("Invalid ID  : idinvalid");
        }

        if (!categoryRepository.existsById(id)) {
            throw new IllegalArgumentException("Entity not found  : idnotfound");
        }

        CategoryDTO result = categoryService.update(categoryDTO);
        return ResponseEntity.ok(result);

    }

    @PatchMapping(value = "/categories/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<CategoryDTO> partialUpdateCategory(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody CategoryDTO categoryDTO) {
        if (categoryDTO.getId() == null) {
            throw new IllegalArgumentException("Invalid id  : idnull");
        }
        if (!Objects.equals(id, categoryDTO.getId())) {
            throw new IllegalArgumentException("Invalid ID  : idinvalid");
        }

        if (!categoryRepository.existsById(id)) {
            throw new IllegalArgumentException("Entity not found  : idnotfound");
        }

        Optional<CategoryDTO> result = categoryService.partialUpdate(categoryDTO);

        return result.map(response -> ResponseEntity.ok().body(response))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> getAllCategories(
            @org.springdoc.api.annotations.ParameterObject Pageable pageable) {

        Page<CategoryDTO> page = categoryService.findAll(pageable);
        return ResponseEntity.ok(page.getContent());
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable Long id) {
        Optional<CategoryDTO> categoryDTO = categoryService.findOne(id);
        return categoryDTO.map(response -> ResponseEntity.ok().body(response))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
