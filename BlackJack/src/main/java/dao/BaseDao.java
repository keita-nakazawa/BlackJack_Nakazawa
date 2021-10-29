package dao;

import java.sql.*;

public abstract class BaseDao {

	protected Connection con = null;
	protected PreparedStatement ps = null;
	protected ResultSet rs = null;

	protected String message;
	
	/**
	 * DB接続処理<br>
	 * DBを起動していないとcon==nullとなり、UserDaoクラスで
	 * NullPointerExceptionを発生させるのでしっかりcatchすること。
	 */
	protected void getConnect() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");

			String url = "jdbc:mysql://localhost/bj_nakazawa";
			String user = "root";
			String password = "";

			con = DriverManager.getConnection(url, user, password);		
			
			if (con == null) {
				message = "DBにアクセスできません"; 
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			message = "JDBCドライバが見つかりません";
		} catch (SQLException e) {
			e.printStackTrace();
			message = "SQL実行中に例外が発生しました";
		}
	}
	
	/**
	 * DBとの接続を解除
	 */
	protected void closeAll() {
		try {
			if (con != null) {
				con.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "close処理中に例外が発生しました";
		}
	}
	
	public String getMessage() {
		return message;
	}

}
