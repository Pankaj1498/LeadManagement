package com.pm.mf.serviceImpl;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pm.mf.model.ErrorResponse;
import com.pm.mf.model.ErrorResponseDetails;
import com.pm.mf.model.LeadModel;
import com.pm.mf.model.SuccessResponse;
import com.pm.mf.repository.LeadRepository;
import com.pm.mf.service.LeadService;

@Service
public class LeadServiceImpl implements LeadService {

	@Autowired
	private LeadRepository leadRepository;

	@Override
	public ResponseEntity<?> createLead(@Valid LeadModel lead) {

		if (leadRepository.existsById(lead.getLeadId())) {
			ErrorResponseDetails errorDetails = new ErrorResponseDetails("E10010",
					List.of("Lead Already Exists in the database with the lead id"));

			ErrorResponse errorResponse = new ErrorResponse("error", errorDetails);

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		if (!isAlpha(lead.getFirstName())) {
			return createErrorResponse(
					"Invalid first name format. It should strictly contain alphabets with no spaces.");
		}

	
		if (lead.getMiddleName() != null && !lead.getMiddleName().isEmpty() && !isAlpha(lead.getMiddleName())) {
			return createErrorResponse(
					"Invalid middle name format. It should strictly contain alphabets with no spaces.");
		}


		if (!isAlpha(lead.getLastName())) {
			return createErrorResponse(
					"Invalid last name format. It should strictly contain alphabets with no spaces.");
		}


		if (!isValidMobileNumber(lead.getMobileNumber())) {
			return createErrorResponse(
					"Invalid mobile number format. It should strictly contain digits, and the first digit should be greater than 5.");
		}

		
		if (!isValidGender(lead.getGender())) {
			return createErrorResponse("Invalid gender format. It should be Male, Female, or Others.");
		}
		if (!isValidEmail(lead.getEmail())) {
			return createErrorResponse("Invalid email format.");
		}

		leadRepository.save(lead);

		SuccessResponse successResponse = new SuccessResponse("success", "Created Lead Successfully");
		return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);

	}

	private ResponseEntity<?> createErrorResponse(String message) {
		ErrorResponseDetails errorDetails = new ErrorResponseDetails("E10010", List.of(message));
		ErrorResponse errorResponse = new ErrorResponse("error", errorDetails);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}

	private boolean isAlpha(String str) {
		return str != null && str.matches("^[A-Za-z]+$");
	}

	private boolean isValidMobileNumber(String mobileNumber) {
		return mobileNumber != null && mobileNumber.matches("^[6-9]\\d{9}$");
	}

	private boolean isValidGender(String gender) {
		return gender != null && gender.matches("^(Male|Female|Others)$");
	}

	private boolean isValidEmail(String email) {
		return email != null && email.matches(".*@.*");
	}

	@Override
	public List<LeadModel> getLeadsByMobileNumber(String mobileNumber) {
		return leadRepository.findByMobileNumber(mobileNumber);
	}

}
