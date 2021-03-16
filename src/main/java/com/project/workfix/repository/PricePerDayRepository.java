package com.project.workfix.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.workfix.models.PricePerDay;
import com.project.workfix.models.Product;

public interface PricePerDayRepository extends JpaRepository<PricePerDay, Long>{
	
	public PricePerDay findByProductAndDate(Product product, Date date);
}
