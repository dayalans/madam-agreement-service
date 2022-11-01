package com.telecom.admin.utilities;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.springframework.web.multipart.MultipartFile;

import com.telecom.admin.entity.PricePlan;

public class CSVHelper {
	public static String TYPE = "text/csv";
	static String[] HEADERs = { "PRICEPLANCODE", "PRICEPLANDESC", "PRICEPLANPRODUCT", "PRICEPLANSTARTDATE",
			"PRICEPLANENDDATE", "PRICEPLANTYPE", "PRICEPLANCATEGORY" };

	public static boolean hasCSVFormat(MultipartFile file) {
		if (!TYPE.equals(file.getContentType())) {
			return false;
		}
		return true;
	}

	public static List<PricePlan> csvToPricePlans(InputStream inputStream) {
		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
				CSVParser csvParser = new CSVParser(fileReader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
			List<PricePlan> pricePlans = new ArrayList<PricePlan>();
			Iterable<CSVRecord> csvRecords = csvParser.getRecords();
			for (CSVRecord csvRecord : csvRecords) {
				SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-DD");

				PricePlan pricePlan = new PricePlan();
				pricePlan.setPricePlanCode(csvRecord.get(0));
				pricePlan.setPricePlanDesc(csvRecord.get(1));
				pricePlan.setPricePlanProduct(csvRecord.get(2));
				pricePlan.setPricePlanStartDate(format.parse(csvRecord.get(3)));
				pricePlan.setPricePlanEndDate(format.parse(csvRecord.get(4)));
				pricePlan.setPricePlanType(csvRecord.get(5));
				pricePlan.setPricePlanCategory(csvRecord.get(6));
				pricePlans.add(pricePlan);
			}
			return pricePlans;
		} catch (IOException | ParseException e) {
			throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
		}
	}
	
	
	public static ByteArrayInputStream tutorialsToCSV(List<PricePlan> pricePlans) {
	    final CSVFormat format = CSVFormat.DEFAULT.withHeader(HEADERs);
	    SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");
	    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
	        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
	      for (PricePlan pricePlan : pricePlans) {
	        List<String> data = Arrays.asList(
	              pricePlan.getPricePlanCode(),
	             pricePlan.getPricePlanDesc(),
	             pricePlan.getPricePlanCategory(),
	             pricePlan.getPricePlanProduct(),	            
	             pricePlan.getPricePlanStatus(),
	          pricePlan.getPricePlanStartDate().toString(),
	            pricePlan.getPricePlanStartDate().toString()
	
	            );
	        csvPrinter.printRecord(data);
	      }
	      csvPrinter.flush();
	      return new ByteArrayInputStream(out.toByteArray());
	    } catch (IOException e) {
	      throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
	    }
	  }
}
