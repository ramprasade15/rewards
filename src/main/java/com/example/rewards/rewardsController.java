package com.example.rewards;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class rewardsController {
	
	
	@Autowired
	RewardsServiceImpl  rewardsServiceImpl;
	

	@PostMapping("/customer/{id}")
	@ResponseBody
	public  TotalRewards getCustomerRewardsByListOfIds(@RequestBody List<Transaction> transactionsList, @PathVariable String id) {
		
		return rewardsServiceImpl.getAllCustomerRewardsByCustomer(transactionsList, id);
		
	}
	
	@PostMapping("/customers")
	@ResponseBody
	public  List<TotalRewards> getAllCustomerRewards(@RequestBody List<Transaction> transactionsList) {
		
		return rewardsServiceImpl.getAllCustomerRewards(transactionsList);
		
	}
	

}
