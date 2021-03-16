package com.project.workfix.utils;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.workfix.models.PricePerDay;
import com.project.workfix.models.Product;
import com.project.workfix.repository.PricePerDayRepository;
import com.project.workfix.repository.ProductRepository;

@Component
public class ExcelUtil {
	
	@Autowired
	private ProductRepository prodRepo;
	
	@Autowired
	private PricePerDayRepository ppdRepo;
	
	public ExcelUtil(ProductRepository prodRepo, PricePerDayRepository ppdRepo) {
		this.prodRepo=prodRepo;
		this.ppdRepo=ppdRepo;
	}
	
	public void parseExcelFile(InputStream is, String SHEET) throws ParseException {
		try {
			
			
			Workbook workbook = new XSSFWorkbook(is);

			Sheet sheet = workbook.getSheet(SHEET);
			Iterator<Row> rows = sheet.iterator();
			
			int rowNumber = 0;
			
			//going through all rows
			while (rows.hasNext()) {
				Row currentRow = rows.next();

				// skip header
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}

				//iterator to iterate over each cell of current row
				Iterator<Cell> cellsInRow = currentRow.iterator();

				//function that does something :)
				something(cellsInRow, SHEET);
			}
			workbook.close();

		}
		
		catch (IOException e) {
			throw new RuntimeException("FAIL! -> message = " + e.getMessage());
		}
	}
	
	
	//read each cell of the row and create an object to store in database
	 public void something(Iterator<Cell> cellsInRow,String SHEET) throws ParseException {
		 
		int cellIndex = 0;
		Product product=null;
		PricePerDay ppd=null;
		
		//format excel interest column data, for time being.
		NumberFormat defaultFormat = NumberFormat.getPercentInstance();
		defaultFormat.setMinimumFractionDigits(1);
		
		
		int maxCellIndex = (SHEET=="Product Details") ? 4:3;
		while (cellsInRow.hasNext() && cellIndex < maxCellIndex) {
			
			Cell currentCell = cellsInRow.next();
			switch(currentCell.getCellType()) {
			case BLANK:break;
			default:if(SHEET=="Product Details") {
						if(product==null) {product = new Product();}
						
				
						switch(cellIndex) {
						case 0:product.setId((long)currentCell.getNumericCellValue());break;
						case 1:product.setName(currentCell.getStringCellValue());break;
						case 2:product.setMaturity(currentCell.getStringCellValue());break;
						case 3:product.setInterest(defaultFormat.format(currentCell.getNumericCellValue()));break;
						}
					}
					else {
						 if(ppd==null) {ppd = new PricePerDay();}
						 
						 switch(cellIndex) {
							case 0:try{
								ppd.setDate(currentCell.getDateCellValue());break;
							}
							catch(Exception e) {
								DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
								Date new_date = format.parse(currentCell.getStringCellValue());
								ppd.setDate(new_date);break;
							}
							case 1:try{
								ppd.setPrice_per_lot(currentCell.getNumericCellValue());break;
							}
							catch(Exception e) {
								ppd.setPrice_per_lot(Double.parseDouble(currentCell.getStringCellValue()));break;
							}
							case 2:try{
								ppd.setProduct(prodRepo.findById((long)currentCell.getNumericCellValue()).get());break;
							}
							catch(Exception e) {
								ppd.setProduct(prodRepo.findById(Long.parseLong(currentCell.getStringCellValue())).get());break;
							}
							}
					}
			}
			cellIndex++;
		}
		if(product!=null) {
			prodRepo.save(product);
		}
		if(ppd!=null) {
			ppdRepo.save(ppd);
		}
	}
}
