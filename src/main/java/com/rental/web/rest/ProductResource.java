package com.rental.web.rest;

import com.rental.domain.Attachment;
import com.rental.domain.Product;
import com.rental.domain.enums.ProductStatus;
import com.rental.repository.CategoryRepository;
import com.rental.service.AttachmentService;
import com.rental.service.dto.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.rental.repository.ProductRepository;
import com.rental.service.ProductService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/products")
public class ProductResource {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/create")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.save(productDTO));
    }

//    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<ProductDTO> createProduct(@ModelAttribute ProductImageDTO productImageDTO) {
//        ProductDTO productDTO = modelMapper.map(productImageDTO, ProductDTO.class);
//        productDTO.setCategory(modelMapper
//                .map(categoryRepository.findById(productImageDTO.getCategoryId()).orElse(null), CategoryDTO.class));
//
//        productImageDTO.getLocalImage().forEach(i -> {
//            try {
//                Attachment attachment = attachmentService.saveAttachment(i);
//                productDTO.getImages().add(new ImageDTO().builder()
//                        .url(ServletUriComponentsBuilder.fromCurrentContextPath().path("/show/").path(attachment.getId()).toUriString())
//                        .name(attachment.getFilename())
//                        .build());
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        });
//
//        return ResponseEntity.status(HttpStatus.OK).body(productService.save(productDTO));
//    }

    @PutMapping("/update")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO) throws Exception {
        if (productDTO.getId() == null)
            throw new IllegalArgumentException("Không thể để Id trống");
        if (!productRepository.existsById(productDTO.getId()))
            throw new IllegalArgumentException("Không thể tìm thấy sản phẩm có Id :" + productDTO.getId());
        return productService.update(productDTO).map(
                productData -> ResponseEntity.status(HttpStatus.OK).body(productData)).orElseThrow(
                () -> new IllegalArgumentException("Cant not update product")
        );
    }
//    @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<ProductDTO> updateProduct(@ModelAttribute ProductImageDTO productImageDTO) throws Exception {
//        ProductDTO productDTO = modelMapper.map(productImageDTO, ProductDTO.class);
//        if (productDTO.getId() == null)
//            throw new IllegalArgumentException("Không thể để Id trống");
//        if (!productRepository.existsById(productDTO.getId()))
//            throw new IllegalArgumentException("Không thể tìm thấy sản phẩm có Id :" + productDTO.getId());
//        productDTO.setCategory(modelMapper
//                .map(categoryRepository.findById(productImageDTO.getCategoryId()).get(), CategoryDTO.class));
//        // còn chẹck điều kiện
//        for (MultipartFile imageFile : productImageDTO.getLocalImage()) {
//            Attachment attachment = attachmentService.saveAttachment(imageFile);
//            productDTO.getImages().add(new ImageDTO(attachment.getFilename(),
//                    ServletUriComponentsBuilder.fromCurrentContextPath().path("/show/").path(attachment.getId()).toUriString()
//            ));
//        }
//
//        return productService.update(productDTO).map(
//                productData -> ResponseEntity.status(HttpStatus.OK).body(productData)).orElseThrow(
//                () -> new IllegalArgumentException("Cant not update product")
//        );
//    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
        if (!productRepository.existsById(id))
            throw new IllegalArgumentException("Cant not find the Product have id : " + id + " in the data ");
        return productService.findOne(id).map(
                        productData -> ResponseEntity.status(HttpStatus.OK).body(productData))
                .orElseThrow(() -> new IllegalArgumentException("Can not find the product "));
    }
    @GetMapping("/search/{nameId}")
    public ResponseEntity<List<ProductDTO>> getProduct(@PathVariable(name = "nameId") String nameProduct) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.searchByNameId(nameProduct));
    }
    @GetMapping("/{name}")
    public ResponseEntity<PagingResponse<ProductDTO>> getProductByName(@PathVariable(name = "name") String nameProduct,
                                                             @org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.searchByName(nameProduct,pageable));
    }


    //    @GetMapping("/getAllProduct")
//    public ResponseEntity<PagingResponse<ProductDTO>> getAllCategories(
//            @org.springdoc.api.annotations.ParameterObject Pageable pageable) {
//        Page<ProductDTO> page = productService.findAll(pageable);
//        if (page.isEmpty())
//            throw new IllegalArgumentException("Cant not find any product in the data ");
//        Page<ProductDTO> page1 = new PageImpl<>(page.getContent());
//        return ResponseEntity.status(HttpStatus.OK).body(PagingResponse.<ProductDTO>builder()
//                .page(page.getPageable().getPageNumber() + 1)
//                .size(page.getSize())
//                .totalPage(page.getTotalPages())
//                .totalItem(page.getTotalElements())
//                .contends(page.getContent().stream()
//                        .filter(productDTO -> {
//                                    if (productDTO.getStatus() != null)
//                                        return productDTO.getStatus().equals(ProductStatus.APPROVED);
//                                    else
//                                        return false;
//                                }
//                        ).collect(Collectors.toList()))
//                .build());
//    }
//    @GetMapping("/getAllProduct")
//    public ResponseEntity<PagingResponse<ProductDTO>> getAllCategories(
//            @org.springdoc.api.annotations.ParameterObject Pageable pageable) {
//        List<ProductDTO> list = productService.findAllProduct();
//        // thuật toán xắp xếp, phân trang
//        final int start = (int) pageable.getOffset();
//        final int end = Math.min((start + pageable.getPageSize()), list.size());
//        Page<ProductDTO> newPage = new PageImpl<>(list.subList(start, end), pageable, list.size());
//        return ResponseEntity.status(HttpStatus.OK).body(PagingResponse.<ProductDTO>builder()
//                .page(newPage.getPageable().getPageNumber() + 1)
//                .size(newPage.getSize())
//                .totalPage(newPage.getTotalPages())
//                .totalItem(newPage.getTotalElements())
//                .contends(newPage.getContent())
//                .build());
//    }
    @GetMapping("/getAllProduct")
    public ResponseEntity<PagingResponse<ProductDTO>> getAllCategories(
            @org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        Page<ProductDTO> findAllProduct = productService.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(PagingResponse.<ProductDTO>builder()
                .page(findAllProduct.getPageable().getPageNumber() + 1)
                .size(findAllProduct.getSize())
                .totalPage(findAllProduct.getTotalPages())
                .totalItem(findAllProduct.getTotalElements())
                .contends(findAllProduct.getContent())
                .build());
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (!productRepository.existsById(id))
            throw new IllegalArgumentException("Không thể tìm thấy Id : " + id + " trong data ");
        if (productRepository.findById(id).get().getStatus().equals(ProductStatus.RENTING))
            throw new IllegalArgumentException("Sản phẩm đang được người dùng thuê không được xóa");
        productService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);

    }

}
