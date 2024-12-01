package com.fad.tasktracker.controller.admin;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fad.tasktracker.entities.Product;
import com.fad.tasktracker.entities.ProductImage;
import com.fad.tasktracker.service.FileStorageService;
import com.fad.tasktracker.service.ProductImageService;
import com.fad.tasktracker.service.ProductService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/admin/product-images")
public class ProductImageController {

    private final ProductImageService productImageService;
    private final FileStorageService fileStorageService;
    private final ProductService productService;

    @Autowired
    public ProductImageController(ProductImageService productImageService, FileStorageService fileStorageService, ProductService productService) {
        this.productImageService = productImageService;
        this.fileStorageService = fileStorageService;
        this.productService = productService;
    }

    // @PostMapping("/{id}")
    // public ResponseEntity<Product> createProductImage(@PathVariable UUID id, @RequestParam("image") MultipartFile image) {



    //     Product product = productService.getProductById(id);
    //     if(product != null){
    //         ProductImage productImage = new ProductImage();
    //             productImage.setProduct(product);
    //         return ResponseEntity.status(HttpStatus.CREATED).body(product);

    //         // try {

    //         //     // String imageUrl = fileStorageService.storeFile(image);
    //         //     ProductImage productImage = new ProductImage();
    //         //     productImage.setProduct(product);
    //         //     // productImage.setUrl(imageUrl);
    //         //     // ProductImage createdProductImage = productImageService.createProductImage(productImage);
    //         //     return ResponseEntity.status(HttpStatus.CREATED).body(product);

    //         // } catch (IOException e) {
    //         //     e.printStackTrace();
    //         //     return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    //         // }

    //     }else {
    //         return ResponseEntity.notFound().build();
    //     }
    // }

    @PostMapping
    public ResponseEntity<ProductImage> createProductImage(@RequestParam("productId") UUID name, @RequestParam("image") MultipartFile image) {

        Product product = productService.getProductById(name);

        if(product != null){

            try {

                String imageUrl = fileStorageService.storeFile(image);
                ProductImage productImage = new ProductImage();
                productImage.setProduct(product);
                productImage.setUrl(imageUrl);
                ProductImage createdProductImage = productImageService.createProductImage(productImage);
                return ResponseEntity.status(HttpStatus.CREATED).body(createdProductImage);

            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }

        }else {
            return ResponseEntity.notFound().build();
        }
    }

}
