package com.moyeoit.domain.file.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadResponse {

    private String fileUrl;
    private String fileName;
    private String fileExtension;
    private Long fileSize;

}
