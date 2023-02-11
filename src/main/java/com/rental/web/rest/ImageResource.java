package com.rental.web.rest;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.rental.repository.ImageRepository;
import com.rental.service.ImageService;
import com.rental.service.dto.ImageDTO;

/**
 * REST controller for managing {@link com.swp391.domain.Image}.
 */
@RestController
@RequestMapping("/api")
public class ImageResource {

    private final ImageService imageService;

    private final ImageRepository imageRepository;

    public ImageResource(ImageService imageService, ImageRepository imageRepository) {
        this.imageService = imageService;
        this.imageRepository = imageRepository;
    }

    @PostMapping("/images")
    public ResponseEntity<ImageDTO> createImage(@RequestBody ImageDTO imageDTO) {

        if (imageDTO.getId() != null) {
            throw new IllegalArgumentException("A new image cannot already have an ID  : idexists");
        }
        ImageDTO result = imageService.save(imageDTO);
        return ResponseEntity.ok(result);

    }

    @PutMapping("/images/{id}")
    public ResponseEntity<ImageDTO> updateImage(
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

        ImageDTO result = imageService.update(imageDTO);
        return ResponseEntity
                .ok(result);

    }

    @PatchMapping(value = "/images/{id}", consumes = { "application/json", "application/merge-patch+json" })
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
