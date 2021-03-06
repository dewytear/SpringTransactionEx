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
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

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

	TransactionTemplate transactionTemplate;

	//Static template을 쓰지 않기 때문에 Autowired Annotation 연결 한다..
	@Autowired
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}
	
	@Autowired
	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}
	
//[region] transactionManager 사용 부분
//	PlatformTransactionManager transactionManager;
//	
//	//servlet-context.xml 의 transactionManager bean에 연결하여 DB 연결하기 위해 Autowired Annotation 연결
//	/* Autowired Annotation을 사용하지 않는 방법은..
//	 * Service Annotation 제거하고 아래처럼 servlet-context.xml에 DAO객체에 transactionManager 연결을 해준다.
//	 * 	<beans:bean name="buydao" class="com.spring.transactionEx.BuyDAO">
//	 *	<beans:property name="transactionManager" ref="transactionManager"/>
//	 *	</beans:bean>
//	 */
//	@Autowired
//	public void setTransactionManager(PlatformTransactionManager transactionManager) {
//		this.transactionManager = transactionManager;
//	}
//[end]
	
	public void buyTicket(BuyVO buyVO) {
		System.out.println("buyTicket() 호출");
		System.out.println("구매 고객 아이디 : " + buyVO.getUserId());
		System.out.println("구매 티켓 수량 : " + buyVO.getAmount());

		//[region] 방법1: transactionTemplate 사용 
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg) {
				
				try {
					
					//카드 테이블에 입력하는 과정
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
	
					//티켓 테이블에 입력하는 과정
					String strQuery = "insert into EX_TICKET(NM_USER, CNT) values(?, ?)";
					template.update(strQuery, new PreparedStatementSetter() {
						@Override
						public void setValues(PreparedStatement preparedStatement) throws SQLException{
							preparedStatement.setString(1, buyVO.getUserId());
							preparedStatement.setInt(2, Integer.parseInt(buyVO.getAmount()));
						}
					});
				
				}
				catch(Exception e) {
					arg.setRollbackOnly();
					System.out.println("Rollback 처리 되었습니다.");
				}
				
			}//doInTransactionWithoutResult
			
		});//execute
		//[end]
		
//[region] 방법2: transactionManager 사용 
//		/*
//		 * - TransactionDefinition와 TransactionStatus 객체를 사용
//		 * . 트랜잭션 초기화
//		 * . 트랜잭션 커밋(commit), 롤백(rollback)
//		 */
//		TransactionDefinition def = new DefaultTransactionDefinition();
//		TransactionStatus status = transactionManager.getTransaction(def);
//		
//		try {
//			//카드 테이블에 입력하는 과정
//			template.update(new PreparedStatementCreator(){
//				@Override
//				public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
//					String strQuery = "insert into EX_CARD(NM_USER, AMOUNT) values(?, ?)";
//					PreparedStatement ps = conn.prepareStatement(strQuery);
//					ps.setString(1, buyVO.getUserId());
//					ps.setInt(2, Integer.parseInt(buyVO.getAmount()));
//					
//					return ps;
//				}
//			});
//			
//			//티켓 테이블에 입력하는 과정
//			String strQuery = "insert into EX_TICKET(NM_USER, CNT) values(?, ?)";
//			template.update(strQuery, new PreparedStatementSetter() {
//				@Override
//				public void setValues(PreparedStatement preparedStatement) throws SQLException{
//					preparedStatement.setString(1, buyVO.getUserId());
//					preparedStatement.setInt(2, Integer.parseInt(buyVO.getAmount()));
//				}
//			});
//			
//			transactionManager.commit(status);
//		}
//		catch(Exception e){
//			transactionManager.rollback(status);
//			System.out.println("Rollback 처리 되었습니다.");
//			e.printStackTrace();
//		}
//[end]
		
	}//buyTicket
}
