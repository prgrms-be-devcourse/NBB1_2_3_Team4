//package com.example.Nadeuri.board;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Repository;
//import org.springframework.web.multipart.MultipartFile;
//import java.io.IOException;
//import java.nio.file.FileVisitResult;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.SimpleFileVisitor;
//import java.nio.file.attribute.BasicFileAttributes;
//import java.util.UUID;
//
//@Repository
//public class LocalImageRepository implements ImageRepository {
//
//    @Value("${file.local.upload.path}")
//    private String uploadPath;
//
//    @Override
//    public String upload(final MultipartFile file) {
//        final String uuid = UUID.randomUUID().toString();
//        final String originalName = file.getOriginalFilename();
//        final Path savePath = Paths.get(uploadPath, uuid + "_" + originalName);
//
//        try {
//            Files.createDirectories(savePath.getParent());
//            file.transferTo(savePath);
//        } catch (Exception e) {
//            throw new IllegalArgumentException("[Error] 이미지 업로드에 실패했습니다.");
//        }
//        return uuid;
//    }
//
//    /**
//     * 디렉토리를 재귀적으로 삭제하는 메서드
//     */
//    public void deleteDirectory(Path path) throws IOException {
//        if (!Files.exists(path)) {
//            return;
//        }
//
//        // 지정된 경로를 시작으로 파일 트리를 탐색
//        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
//
//            // 파일 방문 처리 : 해당 파일을 삭제한 후 탐색을 계속 진행
//            @Override
//            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
//                Files.delete(file);
//                return FileVisitResult.CONTINUE;
//            }
//
//            // 디렉토리 방문 후 처리 : 디렉토리 자체를 삭제한 후 탐색을 계속 진행
//            @Override
//            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
//                Files.delete(dir);
//                return FileVisitResult.CONTINUE;
//            }
//        });
//    }
//}
