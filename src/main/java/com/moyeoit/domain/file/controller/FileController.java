package com.moyeoit.domain.file.controller;

import com.moyeoit.domain.file.controller.response.FileUploadResponse;
import com.moyeoit.domain.file.service.S3Service;
import com.moyeoit.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/file")
@Tag(name = "파일 API", description = "파일 업로드와 관련된 API 입니다.")
public class FileController implements FileAPI {

    private final S3Service s3Service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<FileUploadResponse>> upload(@RequestPart("file") MultipartFile file)
            throws IOException {
        FileUploadResponse response = s3Service.uploadAndGetPublicUrl(file);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
