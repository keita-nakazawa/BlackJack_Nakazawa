package dao;

import java.sql.*;

import javax.servlet.http.HttpSession;

public abstract class BaseDao {

	protected Connection con = null;
	protected PreparedStatement ps = null;
	protected ResultSet rs = null;

	protected String message;
	
	/**
	 * コンストラクタ<br>
	 * 初期処理として、セッションに保存されている有効なDBコネクションを参照するかDBに新規接続する。
	 */
	public BaseDao(HttpSession session) {

		ConnectionHolder connectionHolder = (ConnectionHolder)session.getAttribute("connectionHolder");

		try {
			
			if (connectionHolder != null) {
				
				Connection sessionCon = connectionHolder.getCon();
				
				if (sessionCon.isValid(0)) {
					con = sessionCon;
				} else {
					sessionCon.close();
					session.removeAttribute("connectionHolder");
					getConnect(session);
				}
				
			} else {
				
				getConnect(session);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
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
			session.setAttribute("connectionHolder", new ConnectionHolder(con));
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			message = "JDBCドライバが見つかりません。<br>管理者へお問い合わせください。";
		} catch (SQLException e) {
			e.printStackTrace();
			message = "データベースへの接続に失敗しました。<br>しばらく経ってから再度接続してください。";
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
		} catch (SQLException e) {
			e.printStackTrace();
			message = "例外(closeAll)が発生しました。<br>管理者へお問い合わせください。";
		}
	}

	public String getMessage() {
		return message;
	}

}
