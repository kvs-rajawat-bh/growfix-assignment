package com.project.workfix.endpoints;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.workfix.errors.ProductNotFound;
import com.project.workfix.models.Product;
import com.project.workfix.repository.PricePerDayRepository;
import com.project.workfix.repository.ProductRepository;

@RestController
public class Endpoint {
	
	@Autowired
	private ProductRepository productRepo;
	
	
	@Autowired
	private PricePerDayRepository ppdRepo;
	
	// get method to retrieve data when product id and date is passed
	
	@GetMapping("/get-info")
	public Object getProductInfo(@RequestParam long id, @RequestParam Date date) {
		Product prod = null;
		try {
			prod = productRepo.findById(id);
		}
		catch(Exception e) {
			return new ProductNotFound(id, "product not found", 404);
		}
		
		
		ppdRepo.findByProductAndDate(id, date).get();
		
		
		
	}
	
	
	
	//post excel data to sql database. right now only 2 sheets are there.
	
	

}
