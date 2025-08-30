package com.moyeoit.domain.file.service;

import com.moyeoit.domain.file.controller.response.FileUploadResponse;
import com.moyeoit.domain.file.service.util.FileNameUtils;
import io.awspring.cloud.s3.S3Template;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Template template;

    @Value("${app.s3.bucket}")
    String bucket;
    @Value("${app.s3.urlBase}")
    String urlBase;

    public FileUploadResponse uploadAndGetPublicUrl(MultipartFile file) throws IOException {
        Long fileSize = file.getSize();
        String fileName = FileNameUtils.getFileName(file);
        String fileExtension = FileNameUtils.getExtension(file);

        String key = buildKey(file.getOriginalFilename());
        template.upload(bucket, key, file.getInputStream()); // 버킷 정책으로 공개

        return new FileUploadResponse(urlBase + "/" + key,
                fileName,
                fileExtension,
                fileSize);
    }

    private String buildKey(String original) {
        String ext = (original != null && original.contains(".")) ?
                original.substring(original.lastIndexOf('.') + 1).toLowerCase() : "bin";
        return "images/%s.%s".formatted(java.util.UUID.randomUUID(), ext);
    }

}