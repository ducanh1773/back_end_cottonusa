package com.cottonusa.backend.controller;

import com.cottonusa.backend.modal.Category;
import com.cottonusa.backend.modal.Product;
import com.cottonusa.backend.repository.CategoryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CategoryController {
    private final CategoryRepository repository;
    private List <Category> categories;

    public CategoryController(CategoryRepository repository) {
        this.repository = repository;
    }


    @GetMapping("/category")
    public List<Category> getCategories(

            @RequestParam(required = false, defaultValue = "5") int limit) {

        List<Category> categories = repository.findAll();


        if (categories.isEmpty()) {
            return new ArrayList<>();
        } else {
            return categories.subList(0, Math.min(categories.size(), limit));
        }
    }
}
