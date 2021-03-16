package com.project.workfix.endpoints;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.workfix.errors.DataNotFound;
import com.project.workfix.errors.ProductNotFound;
import com.project.workfix.models.PricePerDay;
import com.project.workfix.models.Product;
import com.project.workfix.pojo.ProductDetails;
import com.project.workfix.repository.PricePerDayRepository;
import com.project.workfix.repository.ProductRepository;
import com.project.workfix.utils.ExcelUtil;

@RestController
public class Endpoint {
	
	@Autowired
	private ProductRepository productRepo;
	
	
	
	@Autowired
	private PricePerDayRepository ppdRepo;
	
	// get method to retrieve data when product id and date is passed
	
	@GetMapping("/get-info")
	public Object getProductInfo(@RequestParam long id, @RequestParam String date) throws ParseException {
		Product prod = null;
		PricePerDay ppd = null;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date new_date = format.parse(date);
		try {
			prod = productRepo.findById(id).get();
		}
		catch(Exception e) {
			return new ProductNotFound(id, "product not found", 404);
		}
		
		try {
			ppd = ppdRepo.findByProductAndDate(prod, new_date);
		}
		catch(Exception e) {
			return new DataNotFound();
		}
		
		
		ProductDetails detail = new ProductDetails();
		
		detail.setName(prod.getName());
		detail.setPrice(ppd.getPrice_per_lot());
		detail.setInterest(prod.getInterest());
		
		return detail;
		
	}
	
	
	
	//post excel data to sql database. right now only 2 sheets are there.
	@PostMapping("/upload")
	public ProductDetails uploadExcelToDB(@RequestParam MultipartFile file) throws ParseException, IOException {
		ExcelUtil excel = new ExcelUtil(productRepo, ppdRepo);
		excel.parseExcelFile(file.getInputStream(), "Product Details");

		excel.parseExcelFile(file.getInputStream(), "Prices Per Day");

		return null;
		
	}
	
	

}
