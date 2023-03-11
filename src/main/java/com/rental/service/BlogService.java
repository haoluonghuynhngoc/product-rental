package com.rental.service;

import com.rental.service.dto.BlogDTO;
import com.rental.service.dto.NotificationDTO;
import com.rental.service.dto.PagingResponse;
import com.rental.service.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BlogService {
    BlogDTO createBlog(BlogDTO blogDTO);
    Optional<BlogDTO> updateBlog(BlogDTO blogDTO);
    PagingResponse<BlogDTO> searchByTitle(String title, Pageable pageable);
    Page<BlogDTO> findAll(Pageable pageable);

    Optional<BlogDTO> findOne(Long id);

    void delete(Long id);
}
