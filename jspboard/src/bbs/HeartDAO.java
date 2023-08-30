package bbs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class HeartDAO {

	private Connection conn;	//db�� �����ϴ� ��ü
	private ResultSet rs;

	public HeartDAO() {
		try {
		    String url = "jdbc:oracle:thin:@localhost:1521:xe";
		    String id = "PROJECT2";
		    String pwd = "1234";
		    Class.forName("oracle.jdbc.OracleDriver");
		    // 오라클 DB연결
		    conn = DriverManager.getConnection(url, id, pwd);
		}
		catch (Exception e) {
		    e.printStackTrace();
		}
	}

	public ArrayList<Bbs> getList(String userID, int pageNumber){
		String SQL = "SELECT * FROM BBS WHERE bbsID = (select bbsID from heart where userID = ?) ORDER BY bbsID DESC FETCH NEXT 10 ROWS ONLY";
		ArrayList<Bbs> list = new ArrayList<Bbs>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Bbs bbs = new Bbs();
				bbs.setBoardID(rs.getInt(1));
				bbs.setBbsID(rs.getInt(2));
				bbs.setBbsTitle(rs.getString(3));
				bbs.setUserID(rs.getString(4));
				bbs.setBbsDate(rs.getDate(5));
				bbs.setBbsContent(rs.getString(6));
				bbs.setBbsAvailable(rs.getInt(7));
				list.add(bbs);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list; //�����ͺ��̽� ����
	}


	public int write(String userID, int bbsID) {
		String SQL = "INSERT INTO heart VALUES(?, ?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, bbsID);
			pstmt.setString(2, userID);
			pstmt.executeUpdate();
			return 1;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1; //�����ͺ��̽� ����
	}

	public ArrayList<heart> getheart(String userID, int bbsID) {
		String SQL = "SELECT * FROM heart WHERE userID = ? AND bbsID = ?";
		ArrayList<heart> list = new ArrayList<heart>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1,  userID);
			pstmt.setInt(2,  bbsID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				heart heart = new heart();
				heart.setBbsID(rs.getInt(1));
				heart.setUserID(rs.getString(2));
				list.add(heart);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public int delete(String userID,int bbsID) {
		String SQL = "DELETE FROM heart WHERE bbsID = ? AND userID = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, bbsID);
			pstmt.setString(2, userID);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // �����ͺ��̽� ����
	}
}