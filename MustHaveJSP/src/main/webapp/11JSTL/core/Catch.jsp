<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<!--  
	catch 태그 : EL이나 JSP에서 발생한 예외를 처리할때 사용한다.
		var속성을 사용하면 에러내용을 원하는 위치에 출력할 수 있다.
		JSTL문법 오류는 catch되지 않는다.
 -->
	<h4>자바코드에서의 예외</h4>
	<%
	int num1 = 100;
	%>
	<c:catch var="eMessage">
		<%
		// 정수는 0으로 나누면 무한대가 되므로 연산불능 예외가 발생한다
		int result = num1 / 0;
		%>
	</c:catch>
	예외내용: ${ eMessage }
	
	<h4>EL에서의 예외</h4>
	<c:set var="num2" value="200" />
	<!--  EL에서는 + 기호를 덧셈연산에서만 사용한다. 즉ㄱ문자열을
	연결하는 기능이 없으므로 예외가 발생한다.-->
	<c:catch var="eMessage">
		${ "일" + num2 }
	</c:catch>
	예외내용 : ${ eMessage }
</body>
</html>