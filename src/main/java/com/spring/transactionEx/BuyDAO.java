/**
 * 
 */
package com.spring.transactionEx;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Service;

/**
 * @CLASS Name
 *  BuyDAO
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
@Service
public class BuyDAO {

	JdbcTemplate template;
	
	//Static template을 쓰지 않기 때문에 Autowired Annotation 연결 한다..
	@Autowired
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}
	
	public void buyTicket(BuyVO buyVO) {
		System.out.println("buyTicket() 호출");
		System.out.println("구매 고객 아이디 : " + buyVO.getUserId());
		System.out.println("구매 티켓 수량 : " + buyVO.getAmount());
		
		template.update(new PreparedStatementCreator(){
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				String strQuery = "insert into EX_CARD(NM_USER, AMOUNT) values(?, ?)";
				PreparedStatement ps = conn.prepareStatement(strQuery);
				ps.setString(1, buyVO.getUserId());
				ps.setInt(2, Integer.parseInt(buyVO.getAmount()));
				
				return ps;
			}
		});
		
		String strQuery = "insert into EX_TICKET(NM_USER, CNT) values(?, ?)";
		template.update(strQuery, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement preparedStatement) throws SQLException{
				preparedStatement.setString(1, buyVO.getUserId());
				preparedStatement.setInt(2, Integer.parseInt(buyVO.getAmount()));
			}
		});
		
	}//buyTicket
}
