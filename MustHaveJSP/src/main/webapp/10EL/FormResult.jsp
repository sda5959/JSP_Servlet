<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- 
	폼값으로 전송된 파라미터를 받을 때 EL의 내장객체를 사용할 수 있다.
	param : 단일값을 받을 때 사용한다. 
	paramValues : 2개이상의 값을 받을 때 사용한다. 단 이경우 배열을 통해
		값을 구분해야 한다.
 -->
	<h3>EL로 폼값받기</h3>
	<ul>
		<li>이름 : ${ param.name }</li>
		<li>성별 : ${ param.gender }</li>
		<li>학력 : ${ param.grade }</li>
		<!-- 
			checkbox의 경우 체크한 값만 서버로 전송하는데, EL은
			NUllpointerException이 발생하지 않으므로 아래와 같이
			기술해도 문제없다.
		 -->
		<li>관심사항 : ${ paramValues.inter[0] }
			${ paramValues.inter[1] }
			${ paramValues.inter[2] }
			${ paramValues.inter[3] }</li>
	</ul>
	
	<h3>JSP 내장객체를 통해 폼값 받기</h3>
	<%
	String name = request.getParameter("name");
	String[] inteArr = request.getParameterValues("inter");
	
	out.println("이름 :" + name + "<br>");
	%>
</body>
</html>