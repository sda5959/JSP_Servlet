package model2.mvcboard;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import fileupload.FileUtil;
import utils.JSFunction;

@WebServlet("/mvcboard/edit.do")
public class EditController extends HttpServlet{
    
    // 수정 페이지로 진입해서 기존 내용을 수정폼에 설정한다.
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
	// 수정페이지로 전달된 일련번호를 통해 게시물을 인출한다.
	String idx = req.getParameter("idx");
	MVCBoardDAO dao = new MVCBoardDAO();
	MVCBoardDTO dto = dao.selectView(idx);
	
	// 인출된 내용은 request영역에 저장한 후 Vies로 포워드한다.
	req.setAttribute("dto", dto);
	req.getRequestDispatcher("/14MVCBoard/Edit.jsp").forward(req, resp);
    }
    
    // 게시물 수정처리 및 파일 업로드
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
	// 디렉토리의 물리적경로 얻어오기 
	String saveDirectory = req.getServletContext().getRealPath("/Uploads");
	
	// 파일업로드 제한용량 얻어오기
	ServletContext application = this.getServletContext();
	int maxPostSize = Integer.parseInt(application.getInitParameter("maxPostSize"));
	
	// 파일 업로드 처리. 객체생성과 동시에 업로드는 완료된다.
	MultipartRequest mr = FileUtil.uploadFile(req, saveDirectory, maxPostSize);
	
	if(mr == null) {
	    JSFunction.alertBack(resp, "첨부파일이 제한용량을 초과합니다");
	    return;
	}
	// 파일을 제외한 나머지 폼값을 얻어온다.
	// hidden박스에 저장된 내용(게시물수정 및 파일 수정에 필요함)
	String idx = mr.getParameter("idx");
	String prevOfile = mr.getParameter("prevOfile");
	String prevSfile = mr.getParameter("prevSfile");
	
	// 사용자가 직접 입력한 값
	String name = mr.getParameter("name");
	String title = mr.getParameter("title");
	String content = mr.getParameter("content");
    
	/*
	 * 패스워드의 경우 인증완료시 session영역에 저장해 둔 것을 얻어온다.
	 * update쿼리 실행시 where에 조건으로 추가된다. 즉, 검증이 완료된
	 * 사용자만 게시물을 수정할 수 있다. 
	 */
	HttpSession session = req.getSession();
	String pass = (String)session.getAttribute("pass");
	
	// DTO에 데이터 저장
	MVCBoardDTO dto = new MVCBoardDTO();
	dto.setIdx(idx);
	dto.setName(name);
	dto.setTitle(title);
	dto.setContent(content);
	dto.setPass(pass);
	
	// 수정페이지에서 첨부한 파일이 있는 경우 파일명을 변경한다.
	String fileName = mr.getFilesystemName("ofile");
	if(fileName != null) {
	    
	    // 날짜와 시간으로 파일명을 생성한 후 확장자와 합쳐서 서버에 
	    // 저장된 파일명을 만든다.
	    String now = new SimpleDateFormat("yyyyMMdd_HmsS").format(new Date());
	    String ext = fileName.substring(fileName.lastIndexOf("."));
	    String newFileName = now + ext;
	    
	    // 파일객체 생성 후 파일명을 변경한다.
	    File oldFile = new File(saveDirectory + File.separator + fileName);
	    File newFile = new File(saveDirectory + File.separator + newFileName);
	    oldFile.renameTo(newFile);
	    
	    dto.setOfile(fileName);
	    dto.setSfile(newFileName);
	    
	    FileUtil.deleteFile(req, "/Uploads", prevSfile);
	}
	else {
	    dto.setOfile(prevOfile);
	    dto.setSfile(prevSfile);
	}
	MVCBoardDAO dao = new MVCBoardDAO();
	int result = dao.updatePost(dto);
	dao.close();
	
	if(result == 1) {
	    session.removeAttribute("pass");
	    resp.sendRedirect("../mvcboard/view.do?idx=" + idx);
	}
	else {
	    JSFunction.alertLocation(resp, "비밀번호검증을 다시진행해 주세요", "../mvcboard/view.do?idx=" + idx);
	}
    }
}
