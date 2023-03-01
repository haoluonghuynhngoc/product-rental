
package com.rental.service.dto;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ImageDTO implements Serializable {
    private Long id;
    private String name;
    private String url;
    //private MultipartFile image;

    public ImageDTO(String name, String url) {
        this.name = name;
        this.url = url;
    }
}

