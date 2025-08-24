package com.moyeoit.domain.file.service.util;

import java.util.Locale;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileNameUtils {

    // 확장자를 제외한 파일명
    public static String getFileName(MultipartFile file) {
        String name = StringUtils.getFilename(file.getOriginalFilename());
        if (name == null || name.isEmpty()) {
            return "";
        }

        // 숨김파일(.env) → 확장자 없음으로 간주
        if (name.startsWith(".") && name.indexOf('.', 1) == -1) {
            return name;
        }

        int lastDot = name.lastIndexOf('.');
        return (lastDot > 0) ? name.substring(0, lastDot) : name;
    }

    // 확장자 (없으면 빈 문자열)
    public static String getExtension(MultipartFile file) {
        String name = StringUtils.getFilename(file.getOriginalFilename());
        if (name == null || name.isEmpty()) {
            return "";
        }

        // 숨김파일(.env) → 확장자 없음
        if (name.startsWith(".") && name.indexOf('.', 1) == -1) {
            return "";
        }

        int lastDot = name.lastIndexOf('.');
        return (lastDot > 0 && lastDot < name.length() - 1)
                ? name.substring(lastDot + 1).toLowerCase(Locale.ROOT)
                : "";
    }

}
