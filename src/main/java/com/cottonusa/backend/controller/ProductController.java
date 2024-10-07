package com.cottonusa.backend.controller;


import com.cottonusa.backend.DTO.ProductDTO;
import com.cottonusa.backend.DTO.SkuDTO;
import com.cottonusa.backend.modal.Customer;
import com.cottonusa.backend.modal.Product;
import com.cottonusa.backend.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController

public class ProductController {
    private final ProductRepository repository;
    private List<Product> products;

    ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/products")
    public List<Product> getProductLimit(

            @RequestParam(required = false, defaultValue = "100") int limit) {

        List<Product> products = repository.findAll();


        if (products.isEmpty()) {
            return new ArrayList<>();
        } else {
            return products.subList(0, Math.min(products.size(), limit));
        }
    }

    @PostMapping("/products/create-product")
    public Product creatProduct(@RequestBody Product product) {
        Product savedProduct = repository.save(product);
        return product;
    }

    @GetMapping("/products/FindProduct")
    public Product findProductByID(@RequestBody Product product){
        return product;
    }


    @GetMapping("products/findProduct/{id}")
    public ResponseEntity<ProductDTO> findProductByID(@PathVariable Long id) {
        Optional<Product> productOpt = repository.findById(id);

        if (productOpt.isPresent()) {
            Product product = productOpt.get();

            // Map Product and SKUs to ProductDTO
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setNameProduct(product.getNameProduct());
            productDTO.setPriceProduct(product.getPriceProduct());
            productDTO.setSummary(product.getSummary());
            productDTO.setCover(product.getCover());
            productDTO.setDescription(product.getDescription());
            productDTO.setCategory_id(product.getCategory_id());
            productDTO.setImg_product(product.getImg_product());

            // Map SKUs to SkuDTO
            List<SkuDTO> skuDTOList = product.getSkus().stream().map(sku -> {
                SkuDTO skuDTO = new SkuDTO();
                skuDTO.setSize_attribute_id(sku.getSize_attribute_id());
                skuDTO.setColor_attribute_id(sku.getColor_attribute_id());
                skuDTO.setSku(sku.getSku());
                skuDTO.setPrice(sku.getPrice());
                skuDTO.setQuantity(sku.getQuantity());
                return skuDTO;
            }).collect(Collectors.toList());

            productDTO.setSkus(skuDTOList);

            return ResponseEntity.ok(productDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }




}
