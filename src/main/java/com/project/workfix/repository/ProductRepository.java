package com.project.workfix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.workfix.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
