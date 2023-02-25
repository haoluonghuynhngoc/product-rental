package com.rental.service.impl;

import com.rental.domain.Blog;
import com.rental.domain.enums.BlogStatus;
import com.rental.repository.BlogRepository;
import com.rental.service.BlogService;
import com.rental.service.dto.BlogDTO;
import com.rental.service.dto.NotificationDTO;
import com.rental.service.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BlogRepository blogRepository;

    @Override
    public BlogDTO createBlog(BlogDTO blogDTO) {
        blogDTO.setStatus(BlogStatus.PENDING);
        return modelMapper.map(blogRepository.save(modelMapper.map(blogDTO, Blog.class)), BlogDTO.class);
    }

    @Override
    public Optional<BlogDTO> updateBlog(BlogDTO blogDTO) {
        return blogRepository.findById(blogDTO.getId()).map(
                blogEntity -> {
                    blogDTO.setCreatedDate(blogEntity.getCreatedDate());
                    blogDTO.setUser(modelMapper.map(blogEntity.getUser(), UserDTO.class));
                    modelMapper.map(blogDTO,blogEntity);
                    return blogEntity;
                }
        ).map(blogRepository::save).map(
                b -> {
                    return modelMapper.map(b, BlogDTO.class);
                }
        );
    }

    @Override
    public Page<BlogDTO> findAll(Pageable pageable) {
        return blogRepository.findAll(pageable).map(b -> {
            return modelMapper.map(b, BlogDTO.class);
        });
    }

    @Override
    public Optional<BlogDTO> findOne(Long id) {
        return blogRepository.findById(id).map(b -> {
            return modelMapper.map(b, BlogDTO.class);
        });
    }

    @Override
    public void delete(Long id) {
        blogRepository.deleteById(id);
    }
}
