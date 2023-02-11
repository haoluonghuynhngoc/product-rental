package com.rental.service.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rental.domain.Product;
import com.rental.repository.ProductRepository;
import com.rental.service.ProductService;
import com.rental.service.dto.ProductDTO;

/**
 * Service Implementation for managing {@link Product}.
 */
@Service
// @Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;

    }

    @Override
    public ProductDTO save(ProductDTO productDTO) {

        Product product = modelMapper.map(productDTO, Product.class);
        product = productRepository.save(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductDTO update(ProductDTO productDTO) {

        Product product = modelMapper.map(productDTO, Product.class);
        product = productRepository.save(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public Optional<ProductDTO> partialUpdate(ProductDTO productDTO) {

        return productRepository
                .findById(productDTO.getId())
                .map(existingBrand -> {
                    modelMapper.map(productDTO, existingBrand);
                    return existingBrand;
                })
                .map(productRepository::save)
                .map(b -> {
                    return modelMapper.map(b, ProductDTO.class);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable) {

        return productRepository.findAll(pageable).map(p -> {
            return modelMapper.map(p, ProductDTO.class);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductDTO> findOne(Long id) {

        return productRepository.findById(id).map(p -> {
            return modelMapper.map(p, ProductDTO.class);
        });
    }

    @Override
    public void delete(Long id) {

        productRepository.deleteById(id);
    }
}
