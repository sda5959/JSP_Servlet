<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.File" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@ page import="com.oreilly.servlet.MultipartRequest"%>
<%@page import="bbs.Bbs"%>
<%@page import="bbs.BbsDAO"%>
<%@page import="java.io.PrintWriter"%>
<% request.setCharacterEncoding("utf-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 웹사이트</title>
</head>
<body>
	<%
		// 현재 세션 상태를 체크
		String userID = null;
		if(session.getAttribute("userID") != null){
	    	userID = (String)session.getAttribute("userID");
		}
		int boardID = 0;
		if (request.getParameter("boardID") != null){
			boardID = Integer.parseInt(request.getParameter("boardID"));
		}
		
		// 로그인을 한 사람만 글을 쓸 수 있도록 코드를 수정한다.
		if(userID == null){
	    	PrintWriter script = response.getWriter();
	    	script.println("<script>");
			script.println("alert('로그인을 하세요')");
			script.println("location.href='login.jsp'");
	    	script.println("</script>");
		}
		int bbsID = 0;
		if(request.getParameter("bbsID") != null) {
		    bbsID = Integer.parseInt(request.getParameter("bbsID"));
		}
		if(bbsID == 0){
	    	PrintWriter script = response.getWriter();
	    	script.println("<script>");
			script.println("alert('유효하지 않은 글입니다.')");
			script.println("location.href='bbs.jsp'");
	    	script.println("</script>");
		}
		// 해당 bbsID에 대한 게시글을 가져온 다음 세션을 통하여 작성자 본인이맞는지 체크
		Bbs bbs = new BbsDAO().getBbs(bbsID);
		
		if(!userID.equals(bbs.getUserID())){
	    	PrintWriter script = response.getWriter();
	    	script.println("<script>");
			script.println("alert('권한이 없습니다.')");
			script.println("location.href='bbs.jsp'");
	    	script.println("</script>");
		} else{
		    // 입력이 안됐거나 빈 값이 있는지 체크한다.
		if(request.getParameter("bbsTitle") == null || request.getParameter("bbsContent") == null
		    || request.getParameter("bbsTitle").equals("") || request.getParameter("bbsContent").equals("")){
			PrintWriter script = response.getWriter();
	    	script.println("<script>");
			script.println("alert('입력이 안 된 사항이 있습니다.')");
			script.println("history.back()");
	    	script.println("</script>");
		}else{
		    // 정상적으로 입력되었다면 글 수정을 수행한다.
		    BbsDAO bbsDAO = new BbsDAO();
		    int result = bbsDAO.update(bbsID, request.getParameter("bbsTitle"), request.getParameter("bbsContent"));
		    // 데이터베이스 오류인 경우
		    if(result == -1){
			PrintWriter script = response.getWriter();
	    	script.println("<script>");
			script.println("alert('글수정에 실패했습니다.')");
			script.println("history.back()");
	    	script.println("</script>");
		    }else{
			PrintWriter script = response.getWriter();
			
	    	script.println("<script>");
			script.println("alert('글수정 성공.')");
			script.println("location.href= \'bbs.jsp?boardID="+boardID+"\'");
	    	script.println("</script>");
		    }
		}
	}
	%>
</body>
</html>