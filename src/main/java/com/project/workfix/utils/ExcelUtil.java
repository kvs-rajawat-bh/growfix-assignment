package com.project.workfix.utils;

import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.project.workfix.models.PricePerDay;
import com.project.workfix.models.Product;
import com.project.workfix.repository.ProductRepository;

@Component
public class ExcelUtil {
	
	@Autowired
	private ProductRepository prodRepo;
	
	public List parseExcelFile(InputStream is, String SHEET) throws ParseException {
		try {
			Workbook workbook = new XSSFWorkbook(is);

			Sheet sheet = workbook.getSheet(SHEET);
			Iterator<Row> rows = sheet.iterator();

			List data = new ArrayList();
			
			int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = rows.next();

				// skip header
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}

				Iterator<Cell> cellsInRow = currentRow.iterator();

				something(cellsInRow, data, SHEET);
			}
			workbook.close();
			return data;

		}
		
		catch (IOException e) {
			throw new RuntimeException("FAIL! -> message = " + e.getMessage());
		}
	}
	
	
	 public void something(Iterator<Cell> cellsInRow, List data, String SHEET) throws ParseException {
		int cellIndex = 0;
		Product product=null;
		PricePerDay ppd=null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		NumberFormat defaultFormat = NumberFormat.getPercentInstance();
		defaultFormat.setMinimumFractionDigits(1);
		while (cellsInRow.hasNext() && cellIndex < 4) {
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
							case 0:ppd.setDate(currentCell.getDateCellValue());break;
							case 1:ppd.setPrice_per_lot(currentCell.getNumericCellValue());break;
							case 2:ppd.setProduct(prodRepo.findById((long)currentCell.getNumericCellValue()).get());break;
							}
					}
			}
			cellIndex++;
		}
		if(product!=null) {
			data.add(product);
		}
		if(ppd!=null) {
			data.add(ppd);
		}
	}
}
