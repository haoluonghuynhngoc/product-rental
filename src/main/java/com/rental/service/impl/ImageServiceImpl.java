package com.rental.service.impl;

import java.util.Optional;

import com.rental.repository.UserRepository;
import com.rental.service.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rental.domain.Image;
import com.rental.repository.ImageRepository;
import com.rental.service.ImageService;
import com.rental.service.dto.ImageDTO;


@Service
@Transactional
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;


    @Override
    public ImageDTO save(ImageDTO imageDTO) {

        Image image = modelMapper.map(imageDTO, Image.class);
        image = imageRepository.save(image);
        return modelMapper.map(image, ImageDTO.class);
    }

    @Override
    public Optional<ImageDTO> update(ImageDTO imageDTO) {
        return imageRepository.findById(imageDTO.getId()).map(
                image -> {
                    imageDTO.setName(image.getName());
                    imageDTO.setUrl(image.getUrl());
                    return image;
                }
        ).map(imageRepository::save).map(b -> {
            return modelMapper.map(b, ImageDTO.class);
        });
    }

    @Override
    public Optional<ImageDTO> partialUpdate(ImageDTO imageDTO) {

        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ImageDTO> findAll(Pageable pageable) {

        return imageRepository.findAll(pageable).map(i -> {
            return modelMapper.map(i, ImageDTO.class);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ImageDTO> findOne(Long id) {

        return imageRepository.findById(id).map(i -> {
            return modelMapper.map(i, ImageDTO.class);
        });
    }

    @Override
    public void delete(Long id) {

        imageRepository.deleteById(id);
    }
}
