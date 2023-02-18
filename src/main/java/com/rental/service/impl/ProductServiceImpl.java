package com.rental.service.impl;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import com.rental.domain.Image;
import com.rental.repository.BrandRepository;
import com.rental.repository.CategoryRepository;
import com.rental.repository.ImageRepository;
import com.rental.service.dto.BrandDTO;
import com.rental.service.dto.CategoryDTO;
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
        for (Image imageClient : product.getImages()) {
            imageClient.setProduct(product);
        }
        //
        product = productRepository.save(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    @Transactional
    public Optional<ProductDTO> update(ProductDTO productDTO) {
        return productRepository
                .findById(productDTO.getId())
                .map(existingProduct -> {
                    // set Brand và category nếu client không truyền về giá trị
                    if (productDTO.getBrand() == null)
                        productDTO.setBrand(modelMapper.map(existingProduct.getBrand(), BrandDTO.class));
                    if (productDTO.getCategory() == null)
                        productDTO.setCategory(modelMapper.map(existingProduct.getCategory(), CategoryDTO.class));
//                    if (productDTO.getImages().isEmpty()) {
//                        for (Image imageEntity : existingProduct.getImages()) {
//                            productDTO.getImages().add(modelMapper.map(imageEntity, ImageDTO.class));
//                        }
//                    }
//      Tạo list SET để dễ map qua Entity update
                    Set<Image> image = new HashSet<>();
                    for (ImageDTO img : productDTO.getImages()) {
                        image.add(imageRepository.findById(img.getId()).map(
                                imageE -> {
                                    imageE.setName(img.getName());
                                    imageE.setUrl(img.getUrl());
                                    return imageE;
                                }
                        ).orElseThrow(null));
                    }
// ====
                    existingProduct.setCategory(categoryRepository.findById(productDTO.getCategory().getId()).orElse(
                            existingProduct.getCategory()));
                    existingProduct.setBrand(brandRepository.findById(productDTO.getBrand().getId()).orElse(
                            existingProduct.getBrand()
                    ));
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
