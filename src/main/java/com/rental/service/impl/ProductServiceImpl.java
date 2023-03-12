package com.rental.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.rental.domain.Image;
import com.rental.domain.enums.ProductStatus;
import com.rental.repository.*;
import com.rental.service.dto.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
    @Autowired
    private CartItemsRepository cartItemsRepository;

    @Override
    public ProductDTO save(ProductDTO productDTO) {
        Product product = modelMapper.map(productDTO, Product.class);
        product.setId(-1L);
        // set image
        //  int nameImage = 1;
        for (Image imageClient : product.getImages()) {
            //     imageClient.setName("" + nameImage++);
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
                    //      int nameImage = 1;
                    for (ImageDTO img : productDTO.getImages()) {
                        //      img.setName("" + nameImage++);
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
    public PagingResponse<ProductDTO> searchByName(String nameProduct, Pageable pageable) {
        List<ProductDTO> listDTO = new ArrayList<>();
        long id = 0L;
        try {
            id = Long.parseLong(nameProduct);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        for (Product listProduct : productRepository.findByNameLikeOrId("%" + nameProduct + "%", id)) {
            listDTO.add(modelMapper.map(listProduct, ProductDTO.class));
        }
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), listDTO.size());
        Page<ProductDTO> pageProduct = new PageImpl<>(listDTO.subList(start, end), pageable, listDTO.size());
        return PagingResponse.<ProductDTO>builder()
                .page(pageProduct.getPageable().getPageNumber() + 1)
                .size(pageProduct.getSize())
                .totalPage(pageProduct.getTotalPages())
                .totalItem(pageProduct.getTotalElements())
                .contends(pageProduct.getContent())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable) {
        return productRepository.findAll(pageable).map(p -> {
            return modelMapper.map(p, ProductDTO.class);
        });
    }

//    @Override  // xắp xếp và loc theo fiter
//    public List<ProductDTO> findAllProduct() {
//        return productRepository.findAll().stream().filter(productDTO -> {
//                    if (productDTO.getStatus() != null)
//                        return productDTO.getStatus().equals(ProductStatus.APPROVED);
//                    else
//                        return false;
//                }
//        ).map(
//                x -> modelMapper.map(x, ProductDTO.class)
//        ).collect(Collectors.toList());
//    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductDTO> findOne(Long id) {
        return productRepository.findById(id).map(p -> {
            return modelMapper.map(p, ProductDTO.class);
        });
    }

    @Override
    public List<ProductDTO> searchByNameId(String nameProduct) {
        Long id = 0L;
        try {
            id = Long.parseLong(nameProduct);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return productRepository.findByNameLikeOrId("%" + nameProduct + "%", id).stream().map(
                product -> {
                    return modelMapper.map(product, ProductDTO.class);
                }
        ).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void delete(Long id) {

        cartItemsRepository.removeByProduct(productRepository.findById(id).get());
        productRepository.deleteById(id);
    }
}
