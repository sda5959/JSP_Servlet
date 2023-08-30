package file;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class FileDAO {

	private Connection conn;

	public FileDAO() {
		try {
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String id = "project2";
			String pwd = "1234";
			Class.forName("oracle.jdbc.OracleDriver");
			conn = DriverManager.getConnection(url, id, pwd);
		} catch(Exception e) {
			e.printStackTrace();
		}

	}



	public int upload(String fileName, String fileRealName, int bbsID) {
		String SQL = "INSERT INTO bbs_file VALUES (?,?,?)";
		try {

			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, fileName);
			pstmt.setString(2, fileRealName);
			pstmt.setInt(3, bbsID);
			return pstmt.executeUpdate();
		} catch (Exception e) {

		}
		return -1;

	}
}