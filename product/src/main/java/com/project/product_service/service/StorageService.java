package com.project.product_service.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class StorageService {

    @Value("${upload.path}")
    private String uploadPath;

    public String uploadFile(MultipartFile file) {
        if(file.isEmpty()) {
            return "file empty";
        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path storageLocation = Paths.get(uploadPath).toAbsolutePath().normalize();

        try {
            Files.copy(file.getInputStream(), storageLocation.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return storageLocation.resolve(fileName).normalize().toString();
    }

    public ResponseEntity loadFile(String fileName, HttpServletRequest request) {
        Path storageLocation = Paths.get(uploadPath).toAbsolutePath().normalize();
        try {
            Path filePath = storageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists())
                return determineContentType(resource, request);
            else
                return ResponseEntity.notFound().build();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    private ResponseEntity determineContentType(Resource resource, HttpServletRequest request) {
        // Load file as Resource
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType((contentType)))
                .header(HttpHeaders.CONTENT_DISPOSITION, "file name=" + resource.getFilename())
                .body(resource);
    }
}
