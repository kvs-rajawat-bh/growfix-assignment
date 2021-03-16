package com.project.workfix.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.workfix.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
