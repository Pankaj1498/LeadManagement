package com.pm.mf.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pm.mf.model.ErrorResponse;
import com.pm.mf.model.ErrorResponseDetails;
import com.pm.mf.model.LeadModel;
import com.pm.mf.model.SuccessResponse;
import com.pm.mf.service.LeadService;

@RestController
@RequestMapping("lead/api/v1")
public class LeadController {

	@Autowired
	private LeadService leadService;

	@PostMapping("/create-lead")
	public ResponseEntity<?> createLeads(@RequestBody LeadModel lead) {
		ResponseEntity<?> responseEntity = leadService.createLead(lead);

		if (responseEntity.getStatusCode().isError()) {
			return responseEntity;
		}

		return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
	}

	@GetMapping("/mobile/{mobileNumber}")
	public ResponseEntity<?> getLeadsByMobileNumber(@PathVariable String mobileNumber) {
		List<LeadModel> leads = leadService.getLeadsByMobileNumber(mobileNumber);

		if (leads.isEmpty()) {
			ErrorResponseDetails errorDetails = new ErrorResponseDetails("E10011",
					List.of("No Lead found with the Mobile Number " + mobileNumber));
			ErrorResponse errorResponse = new ErrorResponse("error", errorDetails);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}

		return ResponseEntity.ok(new SuccessResponse("success", leads));
	}

}
