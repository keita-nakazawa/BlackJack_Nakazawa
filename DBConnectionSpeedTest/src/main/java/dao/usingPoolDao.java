package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import model.ConnectionPool;

public class usingPoolDao {

	public static void main(String[] args) throws Exception {
		List<Long> results = new ArrayList<>();

		Class.forName("org.mariadb.jdbc.Driver");

		String url = "jdbc:mysql://localhost/bj_nakazawa";
		String user = "root";
		String password = "";

		Connection con = null;
		PreparedStatement ps = null;

		System.out.println("usingPoolDaoで実行");

		for (int i = 0; i < 10; i++) {

			// タイム計測開始
			long start = System.nanoTime();

			ConnectionPool cp = new ConnectionPool();
			cp.conn = DriverManager.getConnection(url, user, password);

			for (int j = 0; j < 100; j++) {
				con = cp.conn;
				String sql = "SELECT * FROM users";
				ps = con.prepareStatement(sql);
				ps.execute();
			}
			con.close();

			// タイム計測終了
			long end = System.nanoTime();
			System.out.printf("実行時間(%2d回目): %9dナノ秒\n", i + 1, (end - start));

			//平均は3回目以降の実行時間をもとに算出
			if (i > 3) {
				results.add(end - start);
			}
		}
		System.out.printf("実行時間(3回目以降の平均): %.0fナノ秒", results.stream().mapToLong(e -> e).average().orElse(Double.NaN));
	}
}
