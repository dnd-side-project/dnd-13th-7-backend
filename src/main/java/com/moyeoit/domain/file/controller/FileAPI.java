package com.moyeoit.domain.file.controller;

import com.moyeoit.domain.file.controller.response.FileUploadResponse;
import com.moyeoit.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import java.io.IOException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

public interface FileAPI {

    @Operation(summary = "파일 업로드 API", description = "파일 업로드에 사용되는 API 입니다.")
    ResponseEntity<ApiResponse<FileUploadResponse>> upload(@RequestPart("file") MultipartFile file) throws IOException;
}
