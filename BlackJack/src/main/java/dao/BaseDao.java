package dao;

import java.sql.*;

import javax.servlet.http.HttpSession;

public class BaseDao {

	protected Connection con = null;
	protected PreparedStatement ps = null;
	protected ResultSet rs = null;

	protected String message;
	
	/**
	 * DB接続処理
	 */
	protected void getConnect(HttpSession session) {
		try {
			Class.forName("org.mariadb.jdbc.Driver");

			String url = "jdbc:mysql://localhost/bj_chip_nakazawa";
			String user = "root";
			String password = "";

			con = DriverManager.getConnection(url, user, password);
			session.setAttribute("con", con);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			message = "JDBCドライバが見つかりません";
		} catch (SQLException e) {
			e.printStackTrace();
			message = "DB接続処理のSQL実行中に例外が発生しました";
		}
	}
	
	/**
	 * PreparedStatment、ResultSetオブジェクトのクローズ処理
	 */
	protected void closeAll() {
		try {
			if (ps != null) {
				ps.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "クローズ処理中に例外が発生しました";
		}
	}
	
	/**
	 * セッションに退避してあったConnectionオブジェクトのクローズ処理
	 */
	public void closeCon(HttpSession session) {
		
		Connection sessionCon = (Connection) session.getAttribute("con");
		
		try {
			if (sessionCon != null) {
				sessionCon.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "Connectionのクローズ処理中に例外が発生しました";
		}
	}
	
	public String getMessage() {
		return message;
	}

}
