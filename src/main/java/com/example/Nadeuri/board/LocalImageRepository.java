package com.example.Nadeuri.board;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Repository
public class LocalImageRepository implements ImageRepository {

    @Value("${file.local.upload.path}")
    private String uploadPath;

    @Override
    public String upload(final MultipartFile file) {
        final String uuid = UUID.randomUUID().toString();
        final String originalName = file.getOriginalFilename();
        final Path savePath = Paths.get(uploadPath, uuid + "_" + originalName);

        try {
            Files.createDirectories(savePath.getParent());
            file.transferTo(savePath);
        } catch (Exception e) {
            throw new IllegalArgumentException("[Error] 이미지 업로드에 실패했습니다.");
        }
        return uuid;
    }
}
