package com.rental.web.rest;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.rental.domain.Attachment;
import com.rental.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.rental.repository.ImageRepository;
import com.rental.service.ImageService;
import com.rental.service.dto.ImageDTO;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api")
public class ImageResource {
    @Autowired
    private ImageService imageService;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private AttachmentService attachmentService;
    // vì @requestBody chỉ gửi chuỗi string nên không phù hợp cho gửi file
    //dowloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
    // .path("/dowload/").path(attachment.getId()).toUriString();
    @PostMapping(value = "/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageDTO> createImage(@ModelAttribute ImageDTO imageDTO) throws Exception {
        if (imageDTO.getId() != null) {
            throw new IllegalArgumentException("A new image cannot already have an ID  : idexists");
        }
        ImageDTO result = imageService.save(imageDTO);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PatchMapping(value = "/images/{id}", consumes = {"application/json", "application/merge-patch+json"})
    public ResponseEntity<ImageDTO> partialUpdateImage(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody ImageDTO imageDTO) {

        if (imageDTO.getId() == null) {
            throw new IllegalArgumentException("Invalid id  : idnull");
        }
        if (!Objects.equals(id, imageDTO.getId())) {
            throw new IllegalArgumentException("Invalid ID  : idinvalid");
        }

        if (!imageRepository.existsById(id)) {
            throw new IllegalArgumentException("Entity not found  : idnotfound");
        }

        Optional<ImageDTO> result = imageService.partialUpdate(imageDTO);

        return result.map(response -> ResponseEntity.ok().body(response))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/images")
    public ResponseEntity<List<ImageDTO>> getAllImages(
            @org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        Page<ImageDTO> page = imageService.findAll(pageable);
        return ResponseEntity.ok().body(page.getContent());
    }

    @GetMapping("/images/{id}")
    public ResponseEntity<ImageDTO> getImage(@PathVariable Long id) {

        Optional<ImageDTO> imageDTO = imageService.findOne(id);
        return imageDTO.map(response -> ResponseEntity.ok().body(response))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/images/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        imageService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
