package com.rental.service.impl;

import java.util.*;

import com.rental.domain.Image;
import com.rental.domain.enums.ProductStatus;
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
        product.setId(-1L);
        // set image
        for (Image imageClient : product.getImages()) {
            imageClient.setProduct(product);
        }
        //
        product.setQuantity(1);
        product.setStatus(ProductStatus.APPROVED);
        product.setUser(userRepository.findByUsername("admin"));
        product = productRepository.save(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    @Transactional
    public Optional<ProductDTO> update(ProductDTO productDTO) {
        return productRepository.findById(productDTO.getId())
                .map(existingProduct -> {
                    if (productDTO.getCategory() == null)
                        productDTO.setCategory(modelMapper.map(existingProduct.getCategory(), CategoryDTO.class));
                    if (productDTO.getImages() == null || productDTO.getImages().size() == 0) {
                        imageRepository.findAllByProduct(existingProduct).forEach(
                                image -> productDTO.getImages().add(modelMapper.map(image, ImageDTO.class))
                        );
                    }
                    imageRepository.deleteAllByProduct(existingProduct);
                    List<Image> image = new ArrayList<>();
                    for (ImageDTO img : productDTO.getImages()) {
                        image.add(modelMapper.map(img, Image.class));
                    }
// ====
                    image.forEach(i -> i.setProduct(existingProduct));
                    existingProduct.setCategory(categoryRepository.findById(productDTO.getCategory().getId()).orElse(
                            existingProduct.getCategory()));
                    existingProduct.setImages(image);
// ====
                    productDTO.setQuantity(1);
                    productDTO.setStatus(existingProduct.getStatus());
                    productDTO.setCategory(modelMapper.map(existingProduct.getCategory(), CategoryDTO.class));
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
