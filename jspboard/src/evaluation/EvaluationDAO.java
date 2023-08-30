package evaluation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class EvaluationDAO {

	private Connection conn;	//db�� �����ϴ� ��ü
	private ResultSet rs;

	public EvaluationDAO() {
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
	public String getDate() {
		String SQL = "SELECT NOW()";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return ""; //�����ͺ��̽� ����
	}
	public int write(int bbsID, String userID,int good, int soso, int bad) {
		String SQL = "INSERT INTO evaluation VALUES(?,?,?,?,?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, bbsID);
			pstmt.setString(2, userID);
			pstmt.setInt(3, good);
			pstmt.setInt(4, soso);
			pstmt.setInt(5, bad);
			return pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return -1; //�����ͺ��̽� ����
	}
	public ArrayList<Evaluation> whereList(int bbsID, String userID){
		String SQL = "SELECT * FROM evaluation WHERE bbsID = ? AND userID =?";
		ArrayList<Evaluation> list = new ArrayList<Evaluation>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, bbsID);
			pstmt.setString(2, userID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Evaluation eva = new Evaluation();
				eva.setBbsID(rs.getInt(1));
				eva.setUserID(rs.getString(2));
				eva.setgood(rs.getInt(3));
				eva.setsoso(rs.getInt(4));
				eva.setbad(rs.getInt(5));
				list.add(eva);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list; //�����ͺ��̽� ����
	}

	public ArrayList<Evaluation> getList(int bbsID){
		String SQL = "SELECT * FROM evaluation WHERE bbsID = ?";
		ArrayList<Evaluation> list = new ArrayList<Evaluation>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, bbsID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Evaluation eva = new Evaluation();
				eva.setBbsID(rs.getInt(1));
				eva.setUserID(rs.getString(2));
				eva.setgood(rs.getInt(3));
				eva.setsoso(rs.getInt(4));
				eva.setbad(rs.getInt(5));
				list.add(eva);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list; //�����ͺ��̽� ����
	}

	public Evaluation getEvaluation(int bbsID) {
		String SQL = "SELECT * FROM evaluation WHERE bbsID = ?";
		ArrayList<Evaluation>list = new ArrayList<Evaluation>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1,  bbsID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Evaluation eva = new Evaluation();
				eva.setBbsID(rs.getInt(1));
				eva.setUserID(rs.getString(2));
				eva.setgood(rs.getInt(3));
				eva.setsoso(rs.getInt(4));
				eva.setbad(rs.getInt(5));
				list.add(eva);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public int update(int bbsID, String userID,int good, int soso , int bad) {
		String SQL = "UPDATE evaluation SET good=?, soso=?,bad=? WHERE bbsID = ? AND userID = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, good);
			pstmt.setInt(2, soso);
			pstmt.setInt(3, bad);
			pstmt.setInt(4, bbsID);
			pstmt.setString(5, userID);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // �����ͺ��̽� ����
	}

	public int delete(int bbsID, String userID) {
		String SQL = "DELETE FROM evaluation WHERE bbsID = ? AND userID = ?";
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