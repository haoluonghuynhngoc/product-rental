package com.rental.service.impl;

import com.rental.domain.Attachment;
import com.rental.repository.AttachmentRepository;
import com.rental.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AttachmentServiceImpl implements AttachmentService {
    @Autowired
    private AttachmentRepository attachmentRepository;
    @Override
    public Attachment saveAttachment(MultipartFile file) throws Exception {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            Attachment attachment=Attachment.builder()
                    .filename(fileName)
                    .fileType(file.getContentType())
                    .data(file.getBytes())
                    .build();
            return attachmentRepository.save(attachment);
        } catch (Exception ex) {
            throw new Exception("Could not save File: " + fileName);
        }
    }

    @Override
    public Attachment getAttachment(String fileId) throws Exception {
        return attachmentRepository.findById(fileId).orElseThrow(
                () -> new Exception("File not found with Id :" + fileId));
    }
}
