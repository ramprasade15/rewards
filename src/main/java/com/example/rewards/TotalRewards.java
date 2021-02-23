package com.example.rewards;


public class TotalRewards {
	
	String customerId;
	Integer oneMonthRewardPoints  = 0;
	Integer threeMonthsRewardPoints  = 0;
	
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public Integer getOneMonthRewardPoints() {
		return oneMonthRewardPoints;
	}
	public void setOneMonthRewardPoints(Integer oneMonthRewardPoints) {
		this.oneMonthRewardPoints = oneMonthRewardPoints;
	}
	public Integer getThreeMonthsRewardPoints() {
		return threeMonthsRewardPoints;
	}
	public void setThreeMonthsRewardPoints(Integer threeMonthsRewardPoints) {
		this.threeMonthsRewardPoints = threeMonthsRewardPoints;
	}

	

}
