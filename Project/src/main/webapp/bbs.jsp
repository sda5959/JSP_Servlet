<%@page import="java.util.ArrayList"%>
<%@page import="bbs.BbsDAO"%>
<%@page import="bbs.Bbs" %>
<%@ page import="java.lang.Math" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width", initial-scale="1">
<link rel ="stylesheet" href="./css/bootstrap.css">
<link rel ="stylesheet" href="./css/custom.css">
<script src="http://code.jquery.com/jquery.js"></script>
<script src="js/bootstrap.js" ></script>
<title>게시판 웹사이트</title>
<style type="text/css">
	a, a:hover {
		color : #000000;
		text-decoration : none;
}
</style>
</head>
<body>
	<%
		String userID = null;
		if(session.getAttribute("userID") != null){
    		userID = (String)session.getAttribute("userID");
		}
		int pageNumber = 1; // 기본은 1페이지로 할당
		
		// 만약 파라미터로 넘어온 오브젝트 타입 'pageNumber'가 존재한다면
		// 'int'타입으로 캐스팅해주고 그값을 pageNumber 변수에 저장
		if(request.getParameter("pageNumber") != null){
		    pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		}
		
		int boardID = 0;
		if (request.getParameter("boardID") != null){
			boardID = Integer.parseInt(request.getParameter("boardID"));
		}
	%>
	<nav class="navbar navbar-default">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
				aria-expanded="false">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="main.jsp">JSP 게시판 웹 사이트</a>
		</div>
		<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li><a href="main.jsp">메인</a></li>
				<% if (boardID == 1){ %>
					<li class="active"><a href="bbs.jsp?boardID=1&pageNumber=1">맛집 평가</a></li>
					<li><a href="bbs.jsp?boardID=2&pageNumber=1">자유 게시판</a></li>
				<%} else if(boardID == 2){ %>
					<li><a href="bbs.jsp?boardID=1&pageNumber=1">맛집 평가</a></li>
					<li class="active"><a href="bbs.jsp?boardID=2&pageNumber=1">자유 게시판</a></li>
				<% } %>
			</ul>
			<%
				// 로그인하지 않았을 때 보여지는 화면
				if(userID == null){
			%>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown">
					<a href="#" class="dropdown-toggle" 
					data-toggle="dropdown" role="button"
					aria-haspopup="true" aria-expanded="false">접속하기<span class="caret"></span></a>
					 <ul class="dropdown-menu">
					 	<li><a href="login.jsp">로그인</a></li>
					 	<li><a href="join.jsp">회원가입</a></li>
					 </ul>				
				</li>
			</ul>
			<%
				}else{
			%>
			<!--  헤더 우측에 나타나는 드랍다운 영역 -->
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown">
					<a href="#" class="dropdown-toggle" 
						data-toggle="dropdown" role="button" aria-haspopup="true" 
						aria-expanded="false">회원관리<span class="caret"></span></a>
				<ul class="dropdown-menu">
					<li><a href="logoutAction.jsp">로그아웃</a></li>
			</ul>
			</li>
			</ul>
			<%
				}
			%>
		</div>
	</nav>
	<div class="container">
	<%
		if(boardID == 1){
	%>
			<h1>맛집게시판<br></h1>
			<p>맛집게시판입니다. 서로 맛집을 공유합시다!<br><br></p>
	<% }
		else if(boardID == 2){
	%>
			<h1>자유게시판<br></h1>
			<p>자유롭게 글을 쓰는 곳입니다. 서로 존중하며 글과 댓글을 남깁시다.<br><br></p>
	<% }%>
	<div class="container">
		<div class="row">
			<table class="table table-striped" style="text-align: center; border: 1px solid #dddddd">
			<thead>
				<tr>
					<th style="background-color: #eeeeee; text-align : center;">번호</th>
					<th style="background-color: #eeeeee; text-align : center;">제목</th>
					<th style="background-color: #eeeeee; text-align : center;">작성자</th>
					<th style="background-color: #eeeeee; text-align : center;">작성일</th>
				</tr>
			</thead>
			<tbody>
				<%
					BbsDAO bbsDAO = new BbsDAO();
					ArrayList<Bbs> list = bbsDAO.getList(boardID, pageNumber);
					for(int i = 0; i < list.size(); i++){
				%>
				<tr>
					<td><%= list.get(i).getBbsID() %></td>
					<td><a href="view.jsp?boardID=<%=boardID%>&bbsID=<%= list.get(i).getBbsID() %>"><%= list.get(i).getBbsTitle()%></a></td> 
					<td><%= list.get(i).getUserID() %></td>
					<td><%= list.get(i).getBbsDate() %><td>
					</tr>
					<%
					}
					%>
			</tbody>
			</table>
		<div class=container style="text-align:center">
			<%
				BbsDAO bbsDAO1 = new BbsDAO();
				int pages = (int) Math.ceil(bbsDAO1.getCount(boardID)/10)+1;
				for(int i=1; i<=pages; i++){ %>
					<button type="button" onclick="location.href='bbs.jsp?boardID=<%=boardID %>&pageNumber=<%=i %>'"><%=i %></button>
			<%} %>
		</div>
			<a href="write.jsp?boardID=<%=boardID%>" class="btn-primary pull-right">글쓰기</a>
		</div>
	</div>
	</div>
		<script>
	function nwindow(boardID){
		window.name = "parant";
		var url= "search.jsp?boardID="+boardID;
		window.open(url,"","width=250,height=200,left=300");
	}
</script>
</body>
</html> 