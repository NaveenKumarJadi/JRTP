package com.naveen.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Table(name = "ELIGIBILITY_DETAILS")
@Data
public class EligibilityDetails {
	
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Integer eligId;
	private String name;
	private Long mobile;
	private String email;
	private Character gender;
	private Long ssn;
	private String planName;
	private String planStatus;
	private LocalDate planStartDate;
	private LocalDate planEndDate;
	private LocalDate creatDate;
	private LocalDate updateDate;
	private String createdBy;
	private String updatedBy;
	
}
