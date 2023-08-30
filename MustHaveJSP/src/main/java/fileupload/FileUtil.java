package fileupload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

public class FileUtil {
    public static MultipartRequest uploadFile(HttpServletRequest req, String saveDirectory, int maxPostSize) {
	try {
	    return new MultipartRequest(req, saveDirectory, maxPostSize, "UTF-8");
	} catch (Exception e) {
	    e.printStackTrace();
	    return null;
	}
    }

    public static void download(HttpServletRequest req, HttpServletResponse resp, String directory, String sfileName,
	    String ofileName) {
	String sDirectory = req.getServletContext().getRealPath(directory);
	try {
	    File file = new File(sDirectory, sfileName);
	    InputStream iStream = new FileInputStream(file);
	    String client = req.getHeader("User-Agent");
	    if (client.indexOf("WOW64") == -1) {
		ofileName = new String(ofileName.getBytes("UTF-8"), "ISO-8859-1");
	    } else {
		ofileName = new String(ofileName.getBytes("KSC5601"), "ISO-8859-1");
	    }
	    resp.reset();
	    resp.setContentType("application/octet-stream");
	    resp.setHeader("Content-Disposition", "attachment; filename=\"" + ofileName + "\"");
	    resp.setHeader("Content-Length", "" + file.length());
	    // out.clear(); // 출력 스트림 초기화
	    OutputStream oStream = resp.getOutputStream();
	    byte b[] = new byte[(int) file.length()];
	    int readBuffer = 0;
	    while ((readBuffer = iStream.read(b)) > 0) {
		oStream.write(b, 0, readBuffer);
	    }
	    // 입/출력 스트림 닫음
	    iStream.close();
	    oStream.close();
	} catch (FileNotFoundException e) {
	    System.out.println("파일을 찾을 수 없습니다.");
	    e.printStackTrace();
	} catch (Exception e) {
	    System.out.println("예외가 발생하였습니다.");
	    e.printStackTrace();
	}
    }

    // 파일 삭제를 위한 메서드
    public static void deleteFile(HttpServletRequest req, String directory, String filename) {
	
	// 물리적 경로와 파일명을 통해 File객체를 생성한다.
	String sDirectory = req.getServletContext().getRealPath(directory);
	File file = new File(sDirectory + File.separator + filename);
	
	// 해당 경로에 파일이 존재하면 ...
	if (file.exists()) {
	    // 파일을 삭제한다.
	    file.delete();
	}
    }
}

