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
// xóa id
        if (categoryDTO.getId() != null) {
            throw new IllegalArgumentException("A new category cannot already have an ID  : exists the id ");
        }
        if (categoryRepository.findByName(categoryDTO.getName()) != null) {
            throw new IllegalArgumentException("A name category is exist");
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
            throw new IllegalArgumentException("Can not find the id : " + categoryDTO.getId() + " in the date ");
        }
        return categoryService.updateCategory(categoryDTO).map(
                categoryData -> ResponseEntity.status(HttpStatus.OK).body(categoryData)).orElseThrow(
                () -> new IllegalArgumentException("Cant not update Category ")
        );

    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CategoryDTO>> getAllCategories(
            @org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        Page<CategoryDTO> page = categoryService.findAll(pageable);
        if (page.isEmpty())
            throw new IllegalArgumentException("Cant not find any category in the data ");
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
            throw new IllegalArgumentException("Can not find the user have Id : " + id + " In the data ");
        categoryService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
