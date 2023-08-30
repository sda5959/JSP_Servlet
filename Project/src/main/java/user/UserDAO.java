package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public UserDAO() {
	try {
	    // 오라클 드라이브 로드
	    // 커넥션 URL, 계정 아이디와 패스워드 기술
	    String url = "jdbc:oracle:thin:@localhost:1521:xe";
	    String id = "PROJECT1";
	    String pwd = "1234";
	    Class.forName("oracle.jdbc.OracleDriver");
	    // 오라클 DB연결
	    conn = DriverManager.getConnection(url, id, pwd);
	}
	catch (Exception e) {
	    e.printStackTrace();
	}
    }
    public int login(String userID, String userPassword) {
	String sql="SELECT userPassword FROM User1 WHERE userID = ?";
	try {
	    pstmt = conn.prepareStatement(sql);
	    pstmt.setString(1, userID);

	    rs = pstmt.executeQuery();
	    if(rs.next()) {
		System.out.println(userPassword);
		System.out.println(rs.getString(1));
		if(rs.getString(1).equals(userPassword)) {
		    return 1;
		}else
		    return 0;
	    }
	    return -1;
	}catch (Exception e) {
	    e.printStackTrace();
	}
	return -2;
    }
    public int join(user1 user) {
	String sql="INSERT INTO USER1 VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
	try {
	    pstmt = conn.prepareStatement(sql);
	    pstmt.setString(1, user.getUserID());
	    pstmt.setString(2, user.getUserPassword());
	    pstmt.setString(3, user.getUserName());
	    pstmt.setString(4, user.getUserGender());
	    pstmt.setString(5, user.getUserEmail());
	    pstmt.setInt(6, user.getUserPostcode());
	    pstmt.setString(7, user.getUserAddress());
	    pstmt.setString(8, user.getUserDetailAddress());
	    pstmt.setString(9, user.getUserExtraAddress());
	    return pstmt.executeUpdate();
	}catch (Exception e) {
	    e.printStackTrace();
	}
	return -1;
    }
}
