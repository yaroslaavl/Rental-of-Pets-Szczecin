package org.yaroslaavl.webappstarter.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

@Service
public class ImageService {

    @Value("${app.image.bucket:rental-pets-service/images}")
    private String bucket;

    @SneakyThrows
    public void upload(String imagePath, InputStream content) {
        Path path = Path.of(bucket, imagePath);
        try (content) {
            Files.createDirectories(path.getParent());
            Files.write(path, content.readAllBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        }
    }
    @SneakyThrows
    public Optional<byte[]> get(String imagePath){
        Path path = Path.of(bucket, imagePath);

        return Files.exists(path)
                ? Optional.of(Files.readAllBytes(path))
                : Optional.empty();
    }
}