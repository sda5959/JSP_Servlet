<%@page import="file.FileDAO"%>
<%@page import="bbs.Bbs"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.File" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@ page import="com.oreilly.servlet.MultipartRequest"%>
<%@ page import="bbs.BbsDAO" %>
<%@ page import="java.io.PrintWriter" %>
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:useBean id="bbs" class="bbs.Bbs" scope="page" />
<jsp:setProperty name="bbs" property="bbsTitle" />
<jsp:setProperty name="bbs" property="bbsContent" />
<jsp:setProperty name="bbs" property="userID" />
<jsp:setProperty name="bbs" property="boardID" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>맛집 게시판</title>
</head>
<body>
	 <%
	 	String userID = null;
	 	if(session.getAttribute("userID") != null); {
	 		userID = (String) session.getAttribute("userID");
	 	}
	 	int boardID = 0;
		if (request.getParameter("boardID") != null); {
			boardID = Integer.parseInt(request.getParameter("boardID"));
		}
		
		BbsDAO bbsDAO = new BbsDAO();
		Bbs bbs1= new Bbs();
		bbs1.setBbsID(bbsDAO.getNewNext());
		int bbsID = bbs1.getBbsID();
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
	 	
		if(userID == null){
	 		PrintWriter script = response.getWriter();
	 		script.println("<script>");
			script.println("alert('로그인을 해주세요.')");
	 		script.println("location.href = 'login.jsp'");
	 		script.println("</script>");
	 	} else {
	 	    
	 	   System.out.println("write action : check bbs parameter" + bbs.getBbsTitle());
	 	    
	 		if (bbs.getBbsTitle() == null || bbs.getBbsContent() == null){
	 			PrintWriter script = response.getWriter();
		 		script.println("<script>");
		 		script.println("alert('입력이 안된 사항이 있습니다.')");
		 		script.println("history.back()");
		 		script.println("</script>");
		 	} else {
		 	   
		 	   System.out.println("getNewNext before bbsDAO.write : " + bbs.getBbsID());
		 	   int result = bbsDAO.write(boardID, bbs.getBbsTitle(), userID, bbs.getBbsContent());
		 	   
		 	  new FileDAO().upload(fileName, fileRealName, bbs.getBbsID());
				out.write("filename : " + fileName + "<br>");
				out.write("realfilename : " + fileName + "<br>");
		 	    
		 		if (bbsID == -1){
			 		PrintWriter script = response.getWriter();
			 		script.println("<script>");
			 		script.println("alert('글쓰기에 실패했습니다.')");
			 		script.println("history.back()");
			 		script.println("</script>");
			 	}
			 	else{
			 		PrintWriter script = response.getWriter();
			 		script.println("<script>");
					script.println("location.href= 'bbs.jsp'");
			 		script.println("</script>");
			 	}
		 	}
	 	}
	 %>
</body>
</html>