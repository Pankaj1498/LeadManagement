package com.pm.mf.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.pm.mf.model.LeadModel;

public interface LeadService {

	ResponseEntity<?> createLead(LeadModel ld);

	
	List<LeadModel> getLeadsByMobileNumber(String mobileNumber);

}
