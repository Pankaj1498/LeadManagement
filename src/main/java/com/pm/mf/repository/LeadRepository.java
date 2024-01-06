package com.pm.mf.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pm.mf.model.LeadModel;

@Repository
public interface LeadRepository extends JpaRepository<LeadModel, Integer> {

	List<LeadModel> findByMobileNumber(String mobileNumber);

}
