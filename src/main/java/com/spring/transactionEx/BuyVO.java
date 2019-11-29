/**
 * 
 */
package com.spring.transactionEx;

/**
 * @CLASS Name
 *  BuyVO
 * 
 * @AUTHOR     : Rony Kwak
 * @CREATE DATE: 2019-11-25
 * @PROJECT    : SpringTransactionEx
 * @PACKAGE    : com.spring.transactionEx
 * @Description: 
 * =============================
 * @Change History
 * v1.0: 
 * v1.1: 
 * =============================
 **/
public class BuyVO {
	private String UserId;
	private String amount;
	
	//[region] setter, getter
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return UserId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		UserId = userId;
	}
	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}
	//[end]
	
}
