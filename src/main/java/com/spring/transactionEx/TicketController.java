/**
 * 
 */
package com.spring.transactionEx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @CLASS Name
 *  TicketController
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
@Controller
public class TicketController {
	
	@Autowired
	private BuyDAO buyDAO;
	
	@RequestMapping("/buy")
	public String buy() {
		
		return "buy";
	}
	
	@RequestMapping("/buyOk")
	public String buyOk(BuyVO buyVO, Model model) {
		
		System.out.println("========== Ticketting ==========");
		System.out.println("고객 아이디 : " + buyVO.getUserId());
		System.out.println("티켓 수량 : " + buyVO.getAmount());
		
		buyDAO.buyTicket(buyVO);
		
		model.addAttribute("buyInfo", buyVO);
		
		return "buyResult";
	}
}
