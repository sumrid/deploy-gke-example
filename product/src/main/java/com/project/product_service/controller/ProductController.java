package com.project.product_service.controller;



import com.project.product_service.model.Product;
import com.project.product_service.service.ProductService;
import com.project.product_service.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    private StorageService storageService = new StorageService();

    @GetMapping()
    public List<Product> getProduct() {
        return productService.retrieveProduct();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomer(@PathVariable Long id) {
        Optional<Product> product = productService.retrieveProduct(id);
        if(!product.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }


    @PostMapping("/save")
    public ResponseEntity<?> postCustomer(@Valid @RequestBody Product body) {
        Product product = productService.saveProduct(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putProduct(@PathVariable Long id, @Valid @RequestBody Product body) {
        Optional<Product> product = productService.updateProduct(id, body);
        if(!product.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        if(!productService.deleteProduct(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/upload-image")
    public ResponseEntity<?> uploadImage(@RequestParam MultipartFile file) {
        String filePath = storageService.uploadFile(file);
        return ResponseEntity.ok().body(filePath);
    }

    @GetMapping("/image/{id}")
    public ResponseEntity getImageByProductId(@PathVariable Long id, HttpServletRequest request) {
        Optional<Product> product = productService.retrieveProduct(id);
        if(!product.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return storageService.loadFile(product.get().getImg(), request);
    }
}
