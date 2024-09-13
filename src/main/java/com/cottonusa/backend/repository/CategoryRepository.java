package com.cottonusa.backend.repository;

import com.cottonusa.backend.modal.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
