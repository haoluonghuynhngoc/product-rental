package com.rental.service.impl;

import java.util.*;

import com.rental.domain.Image;
import com.rental.repository.*;
import com.rental.service.dto.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.rental.domain.Product;
import com.rental.service.ProductService;


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
    private UserRepository userRepository;

    @Override
    public ProductDTO save(ProductDTO productDTO) {
        Product product = modelMapper.map(productDTO, Product.class);
        // set image
        for (Image imageClient : product.getImages()) {
            imageClient.setProduct(product);
        }
        //
        product.setUser(userRepository.findByUsername("admin"));
        product = productRepository.save(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    @Transactional
    public Optional<ProductDTO> update(ProductDTO productDTO) {
        productDTO.setCategory(modelMapper.map(
                categoryRepository.findById(productDTO.getCategory().getId()).orElse(null), CategoryDTO.class
        ));
        return productRepository
                .findById(productDTO.getId())
                .map(existingProduct -> {
                    if (productDTO.getCategory() == null)
                        productDTO.setCategory(modelMapper.map(existingProduct.getCategory(), CategoryDTO.class));
//      Tạo list SET để dễ map qua Entity update
                    Set<Image> image = new HashSet<>();
                    for (ImageDTO img : productDTO.getImages()) {
                        image.add(imageRepository.findById(img.getId()).map(
                                        imageE -> {
                                            imageE.setName(img.getName());
                                            imageE.setUrl(img.getUrl());
                                            return imageE;
                                        }
                                ).orElse(

                                        modelMapper.map(img, Image.class))
                        );
                    }
// ====
                    existingProduct.setCategory(categoryRepository.findById(productDTO.getCategory().getId()).orElse(
                            existingProduct.getCategory()));
                    existingProduct.setImages(image);
// ====
                    modelMapper.map(productDTO, existingProduct);
                    return existingProduct;
                })
                .map(productRepository::save)
                .map(b -> {
                    return modelMapper.map(b, ProductDTO.class);
                });
    }

    @Override
    public List<ProductDTO> searchByName(String nameProduct) {
        List<ProductDTO> listDTO = new ArrayList<>();
        for (Product listProduct : productRepository.findByNameLike("%" + nameProduct + "%")) {
            listDTO.add(modelMapper.map(listProduct, ProductDTO.class));
        }
        return listDTO;
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
