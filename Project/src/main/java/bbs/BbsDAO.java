package bbs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


public class BbsDAO {
    private Connection conn;
    private ResultSet rs;

    public BbsDAO() {
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
    public String getDate() {
	String sql = "SELECT now()";
	try{
	   PreparedStatement pstmt = conn.prepareStatement(sql);
	   rs = pstmt.executeQuery();
	   if(rs.next()) {
	       return rs.getString(1);
	   }
	}catch (Exception e) {
	    e.printStackTrace();
	}
	return "";
    }
    public int getNext() {
	String sql = "SELECT bbsID FROM bbs ORDER BY bbsID desc";
	try {
	    PreparedStatement pstmt = conn.prepareStatement(sql);
	    rs = pstmt.executeQuery();
	    if(rs.next()) {
		return rs.getInt(1) + 1;
	    }
	    return 1;
	}catch (Exception e) {
	    e.printStackTrace();
	}
	return -1;
    }
    public int getCount(int boardID) {
	String SQL = "SELECT COUNT(*) FROM BBS WHERE boardID = ?";
	try {
		PreparedStatement pstmt = conn.prepareStatement(SQL);
		pstmt.setInt(1, boardID);
		rs = pstmt.executeQuery();
		if (rs.next()) {
			return rs.getInt(1);
		}
	}catch(Exception e) {
		e.printStackTrace();
	}
	return -1;
}
    public int write(int boardID, String bbsTitle, String userID, String bbsContent) {
	String sql = "INSERT INTO bbs(boardid,bbsid,bbstitle,userid,bbscontent,bbsavailable) VALUES(?, ?, ?, ?, ?, ?)";
	try {
	    PreparedStatement pstmt = conn.prepareStatement(sql);
	    pstmt.setInt(1, boardID);
	    pstmt.setInt(2, getNext());
	    pstmt.setString(3, bbsTitle);
	    pstmt.setString(4, userID);
	    pstmt.setString(5, bbsContent);
	    pstmt.setInt(6, 1);
	    return pstmt.executeUpdate();
	}catch (Exception e) {
	    e.printStackTrace();
	}
	return -1;
    }
    public ArrayList<Bbs> getList(int boardID, int pageNumber) {

	String sql = "SELECT * FROM bbs WHERE boardID = ? AND bbsID < ? AND bbsAvailable = 1 ORDER BY bbsID DESC fetch NEXT 10 rows only";
	ArrayList<Bbs> list = new ArrayList<Bbs>();
	try {
	    PreparedStatement pstmt = conn.prepareStatement(sql);
	    pstmt.setInt(1, boardID);
	    pstmt.setInt(2, getNext() - (pageNumber - 1) * 10);
	    rs = pstmt.executeQuery();
	    while(rs.next()) {
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
	}catch (Exception e) {
	    e.printStackTrace();
	}
	return list;
    }
    public boolean nextPage(int boardID, int pageNumber) {
	String sql = "SELECT * FROM bbs WHERE bbsID < ? AND bbsAvailable = 1";
	try {
	    PreparedStatement pstmt = conn.prepareStatement(sql);
	    pstmt.setInt(1, boardID);
	    pstmt.setInt(2, getNext() - (pageNumber - 1) * 10);
	    rs = pstmt.executeQuery();
	    if(rs.next()) {
		return true;
	    }
	}catch (Exception e) {
	    e.printStackTrace();
	}
	return false;
    }
    public Bbs getBbs(int bbsID) {
	String sql = "SELECT * FROM bbs WHERE bbsID = ?";
	try {
	    PreparedStatement pstmt = conn.prepareStatement(sql);
	    pstmt.setInt(1, bbsID);
	    rs = pstmt.executeQuery();
	    System.out.println("if진입전");
	    if(rs.next()) {
		System.out.println("if진입후");
		Bbs bbs = new Bbs();
		bbs.setBoardID(rs.getInt(1));
		bbs.setBbsID(rs.getInt(2));
		bbs.setBbsTitle(rs.getString(3));
		bbs.setUserID(rs.getString(4));
		bbs.setBbsDate(rs.getDate(5));
		bbs.setBbsContent(rs.getString(6));
		bbs.setBbsAvailable(rs.getInt(7));

		return bbs;
	    }
	}catch (Exception e) {
	    e.printStackTrace();
	}
	return null;
    }
    public int update(int bbsID, String bbsTitle, String bbsContent) {
	String sql = "update bbs set bbsTitle = ?, bbsContent = ? where bbsID = ?";
	try {
	    PreparedStatement pstmt = conn.prepareStatement(sql);
	    pstmt.setString(1, bbsTitle);
	    pstmt.setString(2, bbsContent);
	    pstmt.setInt(3, bbsID);
	    return pstmt.executeUpdate();
	}catch (Exception e) {
	    e.printStackTrace();
	}
	return -1;
    }
    public int delete(int bbsID) {
	String sql = "update bbs set bbsAvailable = 0 where bbsID = ?";
	try {
	    PreparedStatement pstmt = conn.prepareStatement(sql);
	    pstmt.setInt(1, bbsID);
	    return pstmt.executeUpdate();
	}catch (Exception e) {
	    e.printStackTrace();
	}
	return -1;
    }
    public int getNewNext() {
	String SQL = "SELECT bbsID FROM BBS ORDER BY bbsID DESC";
	try {
		PreparedStatement pstmt = conn.prepareStatement(SQL);
		rs = pstmt.executeQuery();

		if(rs.next()) {
			return rs.getInt(1)+1;
		}
		return 1;
	} catch (Exception e) {
		e.printStackTrace();
	}
	return -1;
}
}
