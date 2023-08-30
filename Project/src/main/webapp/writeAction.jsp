<%@page import="file.FileDAO"%>
<%@page import="bbs.Bbs"%>
<%@page import="bbs.BbsDAO"%>
<%@page import="java.io.PrintWriter"%>
<%@ page import="java.io.File" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@ page import="com.oreilly.servlet.MultipartRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("utf-8"); %>
<jsp:useBean id="bbs" class="bbs.Bbs" scope="page" />
<jsp:setProperty name="bbs" property="bbsTitle" />
<jsp:setProperty name="bbs" property="bbsContent" />
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
		
		BbsDAO bbsDAO = new BbsDAO();
		
		Bbs bbs= new Bbs();
		bbs.setBbsID(bbsDAO.getNewNext());
		int bbsID = bbs.getBbsID();
		String directory = application.getRealPath("/bbsUpload/"+bbsID+"/");
		
		File targetDir = new File(directory);
		if(!targetDir.exists()){
			targetDir.mkdirs();
		}
		
		int maxSize = 1024 * 1024 * 500;
		String encoding = "UTF-8";
		
		MultipartRequest multipartRequest
		= new MultipartRequest(request, directory, maxSize, encoding,
						new DefaultFileRenamePolicy());
		
		String fileName = multipartRequest.getOriginalFileName("file");
		String fileRealName = multipartRequest.getFilesystemName("file");
		
		String bbsTitle = multipartRequest.getParameter("bbsTitle");
		String bbsContent = multipartRequest.getParameter("bbsContent");
		bbs.setBbsTitle(bbsTitle);
		bbs.setBbsContent(bbsContent);
		
		// 로그인을 한 사람만 글을 쓸 수 있도록 코드를 수정한다.
		if(userID == null){
	    	PrintWriter script = response.getWriter();
	    	script.println("<script>");
			script.println("alert('로그인을 하세요')");
			script.println("location.href='login.jsp'");
	    	script.println("</script>");
		}else{
		    // 입력이 안되 부분이 있는지 확인
		    if(bbs.getBbsTitle() == null || bbs.getBbsContent() == null){
		    PrintWriter script = response.getWriter();
		    script.println("<script>");
			script.println("alert('입력이 안된 사항이 있습니다')");
			script.println("history.back()");
		    script.println("</script>");
		}else{
		    
		    System.out.println("getNewNext before bbsDAO.write : " + bbs.getBbsID());
			int result = bbsDAO.write(boardID ,bbs.getBbsTitle(), userID, bbs.getBbsContent());
		    
		    new FileDAO().upload(fileName, fileRealName, bbs.getBbsID());
			out.write("filename : " + fileName + "<br>");
			out.write("realfilename : " + fileName + "<br>");
		  
		    // 데이터베이스 오류
		    if(bbsID == -1){
			PrintWriter script = response.getWriter();
		    script.println("<script>");
			script.println("alert('글쓰기 실패')");
			script.println("history.back()");
		    script.println("</script>");
		}else{
		    
		    // 글쓰기가 정상적으로 실행되면 알림창 띄우고 게시판 메인으로 이동
			PrintWriter script = response.getWriter();
		    script.println("<script>");
			script.println("alert('글쓰기 성공')");
			script.println("location.href= 'bbs.jsp?boardID="+boardID+"'");
		    script.println("</script>");
		    }
		}
	}
	%>
</body>
</html>