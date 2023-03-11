package com.rental.web.rest;

import com.rental.repository.BlogRepository;
import com.rental.service.BlogService;
import com.rental.service.dto.BlogDTO;
import com.rental.service.dto.PagingResponse;
import com.rental.service.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blog")
public class BlogResource {
    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private BlogService blogService;

    @PostMapping("/create")
    public ResponseEntity<BlogDTO> createUser(@RequestBody BlogDTO blogDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(blogService.createBlog(blogDTO));
    }

    @PutMapping("/update")
    public ResponseEntity<BlogDTO> updateProduct(@RequestBody BlogDTO blogDTO) {
        if (blogDTO.getId() == null)
            throw new IllegalArgumentException("ID can not be null");
        if (!blogRepository.existsById(blogDTO.getId()))
            throw new IllegalArgumentException("Entity not found : Can not find the id in the data ");
        return blogService.updateBlog(blogDTO).map(
                blogData -> ResponseEntity.status(HttpStatus.OK).body(blogData)).orElseThrow(
                () -> new IllegalArgumentException("Cant not update blog")
        );
    }

    @GetMapping("/{title}")
    public ResponseEntity<PagingResponse<BlogDTO>> getProductByName(@PathVariable(name = "title") String title,
                                                                    @org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(blogService.searchByTitle(title,pageable));
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<BlogDTO> getProduct(@PathVariable Long id) {
        if (!blogRepository.existsById(id))
            throw new IllegalArgumentException("Cant not find the blog have id : " + id + " in the data ");
        return blogService.findOne(id).map(
                        blogData -> ResponseEntity.status(HttpStatus.OK).body(blogData))
                .orElseThrow(() -> new IllegalArgumentException("Can not find the blog "));
    }

    @GetMapping("/getAllBlog")
    public ResponseEntity<List<BlogDTO>> getAllCategories(
            @org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        Page<BlogDTO> page = blogService.findAll(pageable);
        if (page.isEmpty())
            throw new IllegalArgumentException("Cant not find any blog in the data ");
        return ResponseEntity.status(HttpStatus.OK).body(page.getContent());
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (!blogRepository.existsById(id))
            throw new IllegalArgumentException("Cant not find the blog have id : " + id + " in the data ");
        blogService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);

    }
}
