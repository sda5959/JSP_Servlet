package model2.mvcboard;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/*
    어노테이션을 통한 매핑은 요청명을 해당 컨트롤러 상단에 기술한다.
    web.xml에 기술하는 것과 동일하게 입력이 잘못되는 경우 톰켓이 시작되지 
    않을 수 있으므로 주의해야 한다.
*/
@WebServlet("/mvcboard/view.do") 
public class ViewController extends HttpServlet {

    
    /*
     	service()는 서블릿의 수명주기 메서드 중 사용자의 요청을
     	전송방식에 상관 없이 먼저 받아 doGet()혹은 doPost()로 요청을 전달하는 
     	역할을한다 .따라서 전송방식에 상관없이 요청을 처리할 수 있다.
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
	
	// DAO객체 생성을 통해 커넥션풀 객체를 얻어 온다.
	MVCBoardDAO dao = new MVCBoardDAO();
	// 일련번호를 받아온다.
	String idx = req.getParameter("idx");
	// 조회수를 증가시킨다.
	dao.updateVistiCount(idx);
	// 게시물을 인출한다.
	MVCBoardDTO dto = dao.selectView(idx);
	// 커넥션풀 자원 반납
	dao.close();
	
	// 내용에 대해서는 줄바꿈 처리를 위해 <br>태그로 변경한다.
	dto.setContent(dto.getContent().replaceAll("\r\n", "<br/>"));
	// 첨부파일이 이미지인 경우화면에 출력하기 위한 로직을 추가한다.
	String ext= null, fileName=dto.getSfile();
	// 서버에 저장된 파일이 있는 경우라면 확장자를 잘라낸다
	if(fileName!=null) {
	    ext = fileName.substring(fileName.lastIndexOf(".")+1);
	}
	// 이미지의 대표적인확장자를 스트링배열로 선언
	String[] mineStr = {"png", "jpg", "gif"};
	//스트링 배열을 리스트컬렉션으로 변환
	List<String> mimeList = Arrays.asList(mineStr);
	boolean isImage =false;
	// 변환된 LIst컬렉션에 우리가 첨부한 이미지의 확장자가 있는지 확인한다.
	if(mimeList.contains(ext)) {
	    // 만약 없다면  true로 변경한다
	    isImage = true;
	}
	// request영역에 저장한다.
	req.setAttribute("isImage", isImage);

	req.setAttribute("dto", dto);
	req.getRequestDispatcher("/14MVCBoard/View.jsp").forward(req, resp);
    }
}
