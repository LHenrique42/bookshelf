package com.henrique.bookshelf.repository;

import java.util.Optional;

import com.henrique.bookshelf.model.Category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category deleteById(long id);
    Optional<Category> findByName(String name);
}
