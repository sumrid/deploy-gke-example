package com.project.product_service.controller;



import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.project.product_service.model.Product;
import com.project.product_service.model.Stock;
import com.project.product_service.service.ProductService;
import com.project.product_service.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;


    private StorageService storageService = new StorageService();

    @GetMapping()
    @HystrixCommand(fallbackMethod = "fallbackGetAll")
    public List<Product> getProduct() {
        return productService.retrieveProduct();
    }

    @GetMapping("/{id}")
    @HystrixCommand(fallbackMethod = "fallbackGet")
    public ResponseEntity<?> getCustomer(@PathVariable Long id) {
        Optional<Product> product = productService.retrieveProduct(id);
        if(!product.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }


    @PostMapping("/save")
    @HystrixCommand(fallbackMethod = "fallbackCreateProduct")
    public ResponseEntity<?> postCustomer(@Valid @RequestBody Product body) {
        Product product = productService.saveProduct(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/{id}")
    @HystrixCommand(fallbackMethod = "fallbackUpdateProduct")
    public ResponseEntity<?> putProduct(@PathVariable Long id, @Valid @RequestBody Product body) {
        Optional<Product> product = productService.updateProduct(id, body);
        if(!product.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @HystrixCommand(fallbackMethod = "fallbackDeleteProduct")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        if(!productService.deleteProduct(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/upload-image")
//    @HystrixCommand(fallbackMethod = "fallbackUploadImage")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        String fileName = storageService.uploadFile(file);
        return ResponseEntity.ok().body(fileName);
    }

    @GetMapping("/image/{id}")
    public ResponseEntity getImageByProductId(@PathVariable Long id, HttpServletRequest request) {
        Optional<Product> product = productService.retrieveProduct(id);
        if(!product.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return storageService.loadFile(product.get().getImg(), request);
    }

    @GetMapping("/image-name/{imagename}")
    public ResponseEntity getImageByImageName(@PathVariable String imagename, HttpServletRequest request) {
        return storageService.loadFile(imagename, request);
    }

    @PostMapping("/quantity")
    @HystrixCommand(fallbackMethod = "fallbackCreateProduct")
    public ResponseEntity<?> postQuantity(@Valid @RequestBody Stock stock) {
        long id = stock.getProductId();
        Optional<Product> retrieveProduct = productService.retrieveProduct(id);
        if(retrieveProduct.isPresent()) {
            Product product = retrieveProduct.get();
            int quantity = product.getQuantity();
            int different = stock.getQuantity();
            quantity = quantity + different;
            product.setQuantity(quantity);
            productService.updateProduct(id,product);
        }


        return ResponseEntity.ok().build();
    }

    public List<Product> fallbackGetAll() {
        return new ArrayList<>();
    }

    public ResponseEntity<?> fallbackGet(Long id) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Please try again.");
    }

    public ResponseEntity<?> fallbackCreateProduct(Product product) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Please try again.");
    }

    public ResponseEntity<?> fallbackUpdateProduct(Long id, Product product) {
        product.setId(id);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(product);
    }

    public ResponseEntity<?> fallbackDeleteProduct(Long id) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Please try again.");
    }

    public ResponseEntity<?> fallbackUploadImage(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Please try again. File=" + fileName);
    }
}
