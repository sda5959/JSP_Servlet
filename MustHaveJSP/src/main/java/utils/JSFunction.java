package utils;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;

public class JSFunction {
    
    /*
      	메서드 생성시 static을 통해 정적메서드로 정의되었으므로 객체 생성없이 
      	클래스명으로 즉시 메서드를 호출할 수 있다.
     */
    
    // 메세지 알림창을 띄운 후 명시한 URL로 이동합니다.
    public static void alertLocation(String msg, String url, JspWriter out) {
	
	/*
	  Java클래스에는 JSP의 내장객체를 사용할 수 없으므로 반드시 매개변수로 전달받아
	  사용해야 한다. 여기서는 화면에 문자열을 출력하기 위해 out내장객체를 Jspwriter
	  타입으로 받은 후 사용하고 있다.
	*/
	try {
	    // JS를 하나의 문자열로 정의한다
	    String script = ""
		    	  +"<script>" // 삽입할 자바스크립트 코드
		    	  +"	alert('"+ msg + "');"
		    	  +"	location.href='" + url + "';"
		    	  +"</script>";
	    
	    // 해당 문자열을 웹브라우저에 출력한다.
	    out.println(script); // 자바스크립트 코드를 out 내장객체로 출력
	}
	catch(Exception e) {}
    }
    
    // 메세지 알림창을 띄운 후 이전 페이지로 돌아갑니다.
    public static void alertBack(String msg, JspWriter out) {
	try {
	    String script = ""
		    	  +"<script>"
		    	  +"	alert('"+ msg + "');"
		    	  +"	history.back();"
		    	  +"</script>";
	    out.println(script);
	}
	catch(Exception e) {}
    }
    public static void alertLocation(HttpServletResponse resp, String msg, String url) {
	try {
	    resp.setContentType("text/html;charset=UTF-8");
	    PrintWriter writer = resp.getWriter();
	    String script = ""
		    	  +"<script>" // 삽입할 자바스크립트 코드
		    	  +"	alert('"+ msg + "');"
		    	  +"	location.href='" + url + "';"
		    	  +"</script>";
	    writer.print(script);
	}
	catch (Exception e) {}
    }
    public static void alertBack(HttpServletResponse resp ,String msg) {
   	try {
   	    resp.setContentType("text/html;charset=UTF-8");
   	    PrintWriter writer = resp.getWriter();
   	    String script = ""
   		    	  +"<script>"
   		    	  +"	alert('"+ msg + "');"
   		    	  +"	history.back();"
   		    	  +"</script>";
   	    writer.print(script);
   	}
   	catch(Exception e) {}
    }
}
