package com.rental.web.rest;

import com.rental.domain.Attachment;
import com.rental.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AttachmentResource {
    @Autowired
    private AttachmentService attachmentService;

    @GetMapping("/show/{fileId}")
    public ResponseEntity<?> dowloadFile(@PathVariable("fileId") String fileId)throws Exception{
        Attachment attachment=null;
        attachment=attachmentService.getAttachment(fileId);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.parseMediaType(attachment.getFileType()))
                .body(new ByteArrayResource(attachment.getData()));

    }
}
