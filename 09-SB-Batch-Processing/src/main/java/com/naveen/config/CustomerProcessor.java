package com.naveen.config;

import org.springframework.batch.item.ItemProcessor;

import com.naveen.entity.Customer;

public class CustomerProcessor implements ItemProcessor<Customer, Customer> {

	@Override
	public Customer process(Customer item) throws Exception {
		
		//logic
//		if(item.getCountry().equals("India")) {
//			return item;
//		} return null;
		
		return item;
	}

}
