package com.sachet.userservice.controller;

import com.sachet.userservice.model.ProductDto;
import com.sachet.userservice.service.UserSellerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seller")
public class UserSellerController {

    private final UserSellerService userSellerService;

    public UserSellerController(UserSellerService userSellerService) {
        this.userSellerService = userSellerService;
    }

    @PostMapping("/add-product")
    public ResponseEntity<String> sendProductCreatedEvent(@RequestBody ProductDto productDto) {
        userSellerService.sendAddProductEvent(productDto);
        return ResponseEntity.ok("Product Created Successfully");
    }
}
