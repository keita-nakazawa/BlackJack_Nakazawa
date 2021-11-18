package dao;

import java.sql.*;

import javax.servlet.http.HttpSession;

public class BaseDao {

	protected Connection con = null;
	protected PreparedStatement ps = null;
	protected ResultSet rs = null;

	protected String message;
	
	/**
	 * ログアウト時のみ使用するコンストラクタ
	 */
	public BaseDao() {
	}
	
	/**
	 * コンストラクタ<br>
	 * 初期処理として、セッションに保存されているDBコネクションを参照するかDBに新規接続する。
	 */
	public BaseDao(HttpSession session) {

		Connection sessionCon = (Connection) session.getAttribute("con");

		if (sessionCon != null) {
			con = sessionCon;
		} else {
			getConnect(session);
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
			session.setAttribute("con", con);
			
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
	
	/**
	 * セッションに退避してあったConnectionオブジェクトのクローズ処理。
	 * ただし、クローズしないうちにセッション切れが起きた場合はクローズ不可能状態になってしまう恐れあり。
	 */
	public void closeCon(HttpSession session) {
		
		Connection sessionCon = (Connection) session.getAttribute("con");
		
		try {
			if (sessionCon != null) {
				sessionCon.close();
				session.setAttribute("con", null);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			message = "例外(closeCon)が発生しました。<br>管理者へお問い合わせください。";
		}
	}
	
	public String getMessage() {
		return message;
	}

}
