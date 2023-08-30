package real.project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.xml.utils.URI.MalformedURIException;

@WebServlet("/NaverSearchAPI.do")
public class NaverSearchServlet extends HttpServlet {
//    private static final long serialVersionUID = 1L;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	String clientId = "eEYV91F_tlpikwCypQoY";
	String clientSecret = "r_TnivVAS9";

	String keyword = req.getParameter("keyword");
	String startNum = req.getParameter("startNum");

	String text = null;
	try {
	    text = URLEncoder.encode(keyword, "UTF-8");
	} catch (UnsupportedEncodingException e) {
	    throw new RuntimeException("검색어 인코딩 실패", e);
	}
	String apiURL = "https://openapi.naver.com/v1/search/blog?query=" + text + "&display=20&start=" + startNum;

	Map<String, String> requestHeaders = new HashMap<>();
	requestHeaders.put("X-Naver-Client-Id", clientId);
	requestHeaders.put("X-Naver-Client-Secret", clientSecret);
	String responseBody = get(apiURL, requestHeaders);

	System.out.println(responseBody);

	resp.setContentType("text/html;charset=utf-8");
	resp.getWriter().write(responseBody);
    }
    private static String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }


            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 오류 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }


    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURIException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }


    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);


        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();


            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }


            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
        }
    }
}
