<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>[티켓구매결과]</title>
</head>
<body>
	<div align="center">
	<h2>티켓을 구매 하셨습니다!</h2>
	<hr width="400" color="tomato"/>
			${buyInfo.userId} 님은 <br/>
			한일 전 축구 티켓을 ${buyInfo.amount}매 구매하셨습니다.
	</div>
</body>
</html>