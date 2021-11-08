package dao;

import java.sql.*;

import model.ConnectionPool;

public class usingPoolDao {
	/**
	 * DB接続処理
	 */
	public static void main(String[] args) {

		try {
			Class.forName("org.mariadb.jdbc.Driver");

			String url = "jdbc:mysql://localhost/bj_nakazawa";
			String user = "root";
			String password = "";

			ConnectionPool cp = new ConnectionPool();
			cp.con = DriverManager.getConnection(url, user, password);
			PreparedStatement ps = null;
			ResultSet rs = null;
			
			//タイム計測開始
			
			for (int i = 0; i < 1000000; i++) {
				String sql = "SELECT * FROM users";
				ps = cp.con.prepareStatement(sql);
				rs = ps.executeQuery();
			}
			cp.con.close();
			
			//タイム計測終了

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
