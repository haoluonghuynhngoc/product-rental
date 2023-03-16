package com.rental.web.rest;

import java.util.List;

import com.rental.service.dto.CategoryShowDTO;
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
import com.rental.repository.CategoryRepository;
import com.rental.service.CategoryService;
import com.rental.service.dto.CategoryDTO;

@RestController
@RequestMapping("/api/categories")
public class CategoryResource {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;


    @PostMapping("/create")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
        if (categoryRepository.findByName(categoryDTO.getName()) != null) {
            throw new IllegalArgumentException("Tên của chuyên mục "+categoryDTO.getName()+" đã tồn tại");
        }
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.save(categoryDTO));
    }


    @PutMapping("/update")
    public ResponseEntity<CategoryDTO> updateCategory(
            @RequestBody CategoryDTO categoryDTO) {
        if (categoryDTO.getId() == null) {
            throw new IllegalArgumentException("Invalid id : id null");
        }
        if (!categoryRepository.existsById(categoryDTO.getId())) {
            throw new IllegalArgumentException("Không thể tìm thấy chuyên mục có id là " + categoryDTO.getId() + " trong dữ liệu ");
        }
        return categoryService.updateCategory(categoryDTO).map(
                categoryData -> ResponseEntity.status(HttpStatus.OK).body(categoryData)).orElseThrow(
                () -> new IllegalArgumentException("Không thể cập nhật chuyên mục")
        );
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CategoryDTO>> getAllCategories(
            @org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        Page<CategoryDTO> page = categoryService.findAll(pageable);
        if (page.isEmpty())
            throw new IllegalArgumentException("Không thể tìm thấy bất kỳ chuyên mục trong dữ liệu ");
        return ResponseEntity.status(HttpStatus.OK).body(page.getContent());
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<CategoryShowDTO> getCategory(@PathVariable Long id,
                                                       @org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        return categoryService.findOne(id, pageable).map(response -> ResponseEntity.ok().body(response))
                .orElseThrow(() -> new IllegalArgumentException("Không thể tìm thấy thư mục có id : " + id + " trong dữ liệu "));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        if (!categoryRepository.existsById(id))
            throw new IllegalArgumentException("Không thể tìm thấy chuyên mục có Id là : " + id + " trong dữ liệu ");
        categoryService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
