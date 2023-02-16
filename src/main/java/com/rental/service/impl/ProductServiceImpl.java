package com.rental.service.impl;

import java.util.Optional;

import com.rental.domain.Image;
import com.rental.repository.BrandRepository;
import com.rental.repository.CategoryRepository;
import com.rental.repository.ImageRepository;
import com.rental.service.dto.ImageDTO;
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


@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private BrandRepository brandRepository;

    @Override
    public ProductDTO save(ProductDTO productDTO) {
        Product product = modelMapper.map(productDTO, Product.class);
        // set image
        for (Image imageClient: product.getImages()  ) {
            imageClient.setProduct(product);
        }
        //
        product = productRepository.save(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public Optional<ProductDTO> update(ProductDTO productDTO) {
        return productRepository
                .findById(productDTO.getId())
                .map(existingProduct -> {
                    modelMapper.map(productDTO, existingProduct);
                    return existingProduct;
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
