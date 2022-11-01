package com.telecom.admin.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.telecom.admin.entity.FileUploadResponseMessage;
import com.telecom.admin.entity.PricePlan;
import com.telecom.admin.service.AgreementAdminService;
import com.telecom.admin.utilities.CSVHelper;

@Controller
@RequestMapping("v1/agreementadmin/api/")
public class AgreementAdminPricePlanController {
	
	  @Autowired
	  AgreementAdminService agreementAdminService;
	  @PostMapping("/csv/upload")
	  public ResponseEntity<FileUploadResponseMessage> uploadFile(@Valid @RequestParam("file") MultipartFile file) {
	    String message = "";
	    if (CSVHelper.hasCSVFormat(file)) {
	      try {
	    	  agreementAdminService.save(file);
	        message = "Uploaded the file successfully: " + file.getOriginalFilename();
	        return ResponseEntity.status(HttpStatus.OK).body(new FileUploadResponseMessage(message));
	      } catch (Exception e ) {
	        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new FileUploadResponseMessage(e.getMessage()));
	      }
	    }
	    message = "Please upload a csv file!";
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new FileUploadResponseMessage(message));
	  }
	  @GetMapping("/priceplans")
	  public ResponseEntity<List<PricePlan>> getAllPricePlans() {
	    try {
	      List<PricePlan> pricePlans = agreementAdminService.getAllPricePlans();
	      if (pricePlans.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	      }
	      return new ResponseEntity<>(pricePlans, HttpStatus.OK);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	  
	  @GetMapping("/priceplans/active")
	  public ResponseEntity<List<PricePlan>> getAllActivePricePlans() {
	    try {
	      List<PricePlan> pricePlans = agreementAdminService.getAllActivePricePlans();
	      if (pricePlans.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	      }
	      return new ResponseEntity<>(pricePlans, HttpStatus.OK);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	  
	  @GetMapping("/priceplans/download")
	  public ResponseEntity<Resource> getFile() {
	    String filename = "allpriceplan.csv";
	    InputStreamResource file = new InputStreamResource(agreementAdminService.downloadPricePlans());
	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
	        .contentType(MediaType.parseMediaType("application/csv"))
	        .body(file);
	  }
}
