package com.naveen.binding;

import java.time.LocalDate;

import lombok.Data;

@Data
public class EligResponse {

	private Long caseNum;
	private String holderName;
	private Long holderSsn;
	private String planName;
	private String planStatus;
	private LocalDate planStartDate;
	private LocalDate planEndDate;
	private Double benefitAmt;
	private String denialReason;
}
