<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// 로그아웃 처리를 위한 방법
// 1. 회원 인증 정보를 session영역에서 삭제한다.
session.removeAttribute("UserId");
session.removeAttribute("UserName");

// 2.session영역에 모든 속성을 한꺼번에 삭제한다.
session.invalidate();

response.sendRedirect("LoginForm.jsp");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>