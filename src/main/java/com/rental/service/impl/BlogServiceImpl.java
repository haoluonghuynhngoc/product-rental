package com.rental.service.impl;

import com.rental.domain.Blog;
import com.rental.domain.Product;
import com.rental.domain.enums.BlogStatus;
import com.rental.repository.BlogRepository;
import com.rental.repository.UserRepository;
import com.rental.service.BlogService;
import com.rental.service.dto.BlogDTO;
import com.rental.service.dto.NotificationDTO;
import com.rental.service.dto.ProductDTO;
import com.rental.service.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public BlogDTO createBlog(BlogDTO blogDTO) {
        blogDTO.setStatus(BlogStatus.PENDING);
        blogDTO.setUser(modelMapper.map(userRepository.findByUsername("admin"),UserDTO.class));
        return modelMapper.map(blogRepository.save(modelMapper.map(blogDTO, Blog.class)), BlogDTO.class);
    }

    @Override
    public Optional<BlogDTO> updateBlog(BlogDTO blogDTO) {
        return blogRepository.findById(blogDTO.getId()).map(
                blogEntity -> {
                    blogDTO.setCreatedDate(blogEntity.getCreatedDate());
                    blogDTO.setUser(modelMapper.map(blogEntity.getUser(), UserDTO.class));
                    modelMapper.map(blogDTO, blogEntity);
                    return blogEntity;
                }
        ).map(blogRepository::save).map(
                b -> {
                    return modelMapper.map(b, BlogDTO.class);
                }
        );
    }

    @Override
    public List<BlogDTO> searchByTitle(String title) {
        List<BlogDTO> listDTO = new ArrayList<>();
        for (Blog listBlog : blogRepository.findByTitleLike("%" + title + "%")) {
            listDTO.add(modelMapper.map(listBlog, BlogDTO.class));
        }
        return listDTO;
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
