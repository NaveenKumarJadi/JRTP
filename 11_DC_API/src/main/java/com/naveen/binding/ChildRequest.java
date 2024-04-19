package com.naveen.binding;

import java.util.List;

import lombok.Data;

@Data
public class ChildRequest {

	private Long caseNum;
	
	private List<Child> childs;
	
	//option
	private String childName;
	private Integer childAge;
	
	
}
