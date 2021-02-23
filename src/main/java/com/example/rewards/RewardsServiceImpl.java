package com.example.rewards;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class RewardsServiceImpl {

	private static List<Transaction> transactionAmountList = new ArrayList<>();

	LocalDate threeMonthsAgoDate = LocalDate.now().plusMonths(-3);
	LocalDate oneMonthAgoDate = LocalDate.now().plusMonths(-1);

	public List<TotalRewards> getCustomerRewardsById(Customer customer) {
		System.out.println("threeMonthsAgoDate        " + threeMonthsAgoDate);
		System.out.println("oneMonthAgoDate        " + oneMonthAgoDate);
		List<TotalRewards> totalRewardsList = new ArrayList<>();
		List<String> custList = customer.getCustList();
		for (String custId : custList) {
			for (Transaction transactionAmount : transactionAmountList) {
				TotalRewards totalRewards = calculateRewads(transactionAmount, custId);

				if (!totalRewardsList.isEmpty() && totalRewards != null && totalRewards.getCustomerId() != null) {
					for (TotalRewards totalRewards1 : totalRewardsList) {

						if (totalRewards != null && !totalRewardsList.isEmpty() && totalRewards1 != null
								&& totalRewards1.getCustomerId() != null && totalRewards1.getCustomerId().equals(custId)
								&& (totalRewards.getOneMonthRewardPoints() != 0
								|| totalRewards.getThreeMonthsRewardPoints() != 0)) {
							totalRewards.setOneMonthRewardPoints(
									totalRewards1.getOneMonthRewardPoints() + totalRewards.getOneMonthRewardPoints());

							totalRewards.setThreeMonthsRewardPoints(totalRewards1.getThreeMonthsRewardPoints()
									+ totalRewards.getThreeMonthsRewardPoints());
							for (TotalRewards t1 : totalRewardsList) {
								if (t1.getCustomerId().equals(totalRewards.getCustomerId())) {
									totalRewardsList.remove(t1);
									totalRewardsList.add(totalRewards);
								}
							}
							break;
						} else if (totalRewards.getOneMonthRewardPoints() != 0
								&& totalRewards.getThreeMonthsRewardPoints() != 0) {

							totalRewardsList.add(totalRewards);
							break;
						}
					}

				} else if (totalRewards != null && totalRewards.getCustomerId() != null
						&& totalRewards.getThreeMonthsRewardPoints() != 0
						&& totalRewards.getThreeMonthsRewardPoints() != 0) {
					totalRewardsList.add(totalRewards);

				}
			}

		}
		return totalRewardsList;

	}

	private TotalRewards calculateRewads(Transaction transactionAmount, String custId) {
		TotalRewards totalRewards = new TotalRewards();
		Integer oneMonthRewardPoints = 0;
		Integer threeMonthsRewardPoints = 0;
		LocalDate transactionDate = LocalDate.of(transactionAmount.getTransactionDate().getYear(),
				transactionAmount.getTransactionDate().getMonth(),
				transactionAmount.getTransactionDate().getDayOfMonth());

		if (custId != null && custId.equals(transactionAmount.getCustomerId())) {

			if (threeMonthsAgoDate.isBefore(transactionDate)) {

				threeMonthsRewardPoints = threeMonthsRewardPoints
						+ rewardsCalculate(transactionAmount.getTransactionAmount());

			}

			if (oneMonthAgoDate.isBefore(transactionDate)) {
				oneMonthRewardPoints = oneMonthRewardPoints
						+ rewardsCalculate(transactionAmount.getTransactionAmount());
			}
			if (oneMonthRewardPoints != 0 || threeMonthsRewardPoints != 0) {
				totalRewards.setCustomerId(custId);
				totalRewards.setOneMonthRewardPoints(oneMonthRewardPoints);
				totalRewards.setThreeMonthsRewardPoints(threeMonthsRewardPoints);
			} else {
				totalRewards = null;
			}

		}
		return totalRewards;
	}

	private Integer rewardsCalculate(Double transactionAmount) {
		Integer rewardPoints = 0;
		if (transactionAmount >= 50 && transactionAmount <= 100) {
			rewardPoints = (rewardPoints + (int) (transactionAmount - 50));
		} else if (transactionAmount > 100) {
			rewardPoints = (rewardPoints + (int) (2 * (transactionAmount - 100) + 50));
		} else {
			rewardPoints = 0;
		}
		return rewardPoints;
	}

	public TotalRewards getCustomerRewardsById(String custId) {
		TotalRewards totalRewardsForCustomer = new TotalRewards();
		TotalRewards totalRewardsForCustomer1 = new TotalRewards();
		for (Transaction transactionAmountListForCustomer : transactionAmountList) {
			if (transactionAmountListForCustomer.getCustomerId().equals(custId)) {
				totalRewardsForCustomer1 = calculateRewads(transactionAmountListForCustomer, custId);
				totalRewardsForCustomer.setCustomerId(totalRewardsForCustomer1.getCustomerId());
				totalRewardsForCustomer.setOneMonthRewardPoints(totalRewardsForCustomer.getOneMonthRewardPoints()
						+ totalRewardsForCustomer1.getOneMonthRewardPoints());
				totalRewardsForCustomer.setThreeMonthsRewardPoints(totalRewardsForCustomer.getThreeMonthsRewardPoints()
						+ totalRewardsForCustomer1.getThreeMonthsRewardPoints());

			}

		}
		return totalRewardsForCustomer;
	}

	public List<TotalRewards> getAllCustomerRewards() {
		return calculateAllRewards(transactionAmountList);

	}

	private List<TotalRewards> calculateAllRewards(List<Transaction> transactionAmountList2) {
		List<TotalRewards> totalRewardsList = new ArrayList<>();
		TotalRewards tr = new TotalRewards();
		for (Transaction transactionAmount : transactionAmountList2) {
			String custId = transactionAmount.getCustomerId();
			TotalRewards totalRewards = calculateRewads(transactionAmount, custId);
			if (totalRewardsList.size() == 0 && totalRewards != null) {
				totalRewardsList.add(totalRewards);
			} else if (totalRewardsList.size() >= 1 && totalRewards != null) {
				for (TotalRewards ta1 : totalRewardsList) {
					if (totalRewards.getCustomerId() != null && ta1.getCustomerId() != null
							&& ta1.getCustomerId().equals(totalRewards.getCustomerId())) {
						tr = ta1;
					}
				}
				if (totalRewardsList.contains(tr)) {
					totalRewards.setOneMonthRewardPoints(
							tr.getOneMonthRewardPoints() + totalRewards.getOneMonthRewardPoints());
					totalRewards.setThreeMonthsRewardPoints(
							tr.getThreeMonthsRewardPoints() + totalRewards.getThreeMonthsRewardPoints());
					totalRewardsList.indexOf(tr);
					totalRewardsList.remove(tr);
					totalRewardsList.add(totalRewards);

				} else {
					totalRewardsList.add(totalRewards);
				}

			}

		}
		return totalRewardsList;
	}

	public List<TotalRewards> getAllCustomerRewards(List<Transaction> transactionsList) {
		return calculateAllRewards(transactionsList);
	}

	public TotalRewards getAllCustomerRewardsByCustomer(List<Transaction> transactionsList, String id) {
		List<TotalRewards> totalRewardsList = getAllCustomerRewards(transactionsList);

		TotalRewards result = totalRewardsList.stream().filter(x -> id.equals(x.getCustomerId())).findAny()
				.orElse(null);
		return result;
	}

}
