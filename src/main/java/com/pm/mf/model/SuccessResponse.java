package com.pm.mf.model;

import java.util.List;

import lombok.Data;

@Data
public class SuccessResponse {

	private String status;
	private String data;
	private List<LeadModel> datas;

	public SuccessResponse(String status, String data) {
		super();
		this.status = status;
		this.data = data;
	}


	public SuccessResponse(String status, List<LeadModel> datas) {
		
		this.status = status;
		this.datas = datas;
	}
}
