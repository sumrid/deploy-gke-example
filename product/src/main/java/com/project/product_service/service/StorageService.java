package com.project.product_service.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@Service
public class StorageService {

    private String uploadPath = "./src/main/resources/product-image-upload";
//
//    public String uploadFile(MultipartFile file) {
//        if(file.isEmpty()) {
//            return "file empty";
//        }
//
//        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//        Path storageLocation = Paths.get(uploadPath).toAbsolutePath().normalize();
//
//        try {
//            Files.copy(file.getInputStream(), storageLocation.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return storageLocation.resolve(fileName).normalize().toString();
//    }
//
//    public ResponseEntity loadFile(String fileName, HttpServletRequest request) {
//        Path storageLocation = Paths.get(uploadPath).toAbsolutePath().normalize();
//        try {
//            Path filePath = storageLocation.resolve(fileName).normalize();
//            Resource resource = new UrlResource(filePath.toUri());
//            if(resource.exists())
//                return determineContentType(resource, request);
//            else
//                return ResponseEntity.notFound().build();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
//        }
//    }
//
//    private ResponseEntity determineContentType(Resource resource, HttpServletRequest request) {
//        // Load file as Resource
//        String contentType = null;
//        try {
//            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        // Fallback to the default content type if type could not be determined
//        if(contentType == null) {
//            contentType = "application/octet-stream";
//        }
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType((contentType)))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "file name=" + resource.getFilename())
//                .body(resource);
//    }


    private Credentials credentials = null;
    {
        try {
            credentials = GoogleCredentials.fromStream(new FileInputStream("/src/main/resources/cloud.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
    private final static String BUCKET_NAME = "soa-group-7-174";

    @SuppressWarnings("deprecation")
    public String uploadFile(MultipartFile file) throws IOException {
        if(file.isEmpty()) {
            return "file empty";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd-HHmmssSSS-");
        String dateStr = dateFormat.format(new Date());

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String newFileName = dateStr + fileName;

        BlobInfo blobInfo = storage.create(BlobInfo.newBuilder(BUCKET_NAME, newFileName)
                .setAcl(new ArrayList<>(Arrays.asList(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER)))).build(),
                file.getInputStream());

        return blobInfo.getName();
    }

    public ResponseEntity loadFile(String fileName, HttpServletRequest request) {
        return ResponseEntity.ok().contentType(MediaType.parseMediaType("image/jpeg"))
                .body(storage.get(BUCKET_NAME).get(fileName).getContent());
    }
}
