package com.rental.repository;

import com.rental.domain.Blog;
import com.rental.service.dto.BlogDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findByTitleLike(String title);
}
