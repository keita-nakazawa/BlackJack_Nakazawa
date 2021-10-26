package dao;

import java.sql.*;
import java.util.*;

import javax.servlet.http.HttpSession;

import model.User;

/**
 * DBのusersテーブルのみを扱うDAOクラス
 */
public class UserDao {

	private Connection con = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	/**
	 * DBにユーザ情報を新規登録する。
	 * @return 新規登録に失敗した旨のメッセージを示すStringオブジェクト<br>成功時はnull
	 */
	public String getRegisterMessage(String userId, String nickname, String password, String password2) {

		String message = null;

		if (password.equals(password2)) {

			try {
				// DBへの接続処理
				getConnect();

				// SQL文の作成(win_rateカラムには0を設定)
				String sql = "INSERT INTO users VALUES (?, ?, ?, 0)";
				ps = con.prepareStatement(sql);
				ps.setString(1, userId);
				ps.setString(2, nickname);
				ps.setString(3, password);
				System.out.println(ps);

				ps.executeUpdate();

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLIntegrityConstraintViolationException e) {
				e.printStackTrace();
				message = "登録済みかもしくは不正なユーザIDです。";
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeAll();
			}
		} else {
			message = "パスワードが一致していません。";
		}
		return message;
	}

	public User getLoginUser(String userId, String password) {

		User user = new User();

		try {
			// DBへの接続処理
			getConnect();

			// SQL文の作成
			String sql = "SELECT * FROM users WHERE (user_id = ?) && (password = ?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, userId);
			ps.setString(2, password);
			System.out.println(ps);

			rs = ps.executeQuery();

			if (rs.next()) {
				// ログイン情報が正しい場合の処理
				user.setUserId(rs.getString("user_id"));
				user.setNickname(rs.getString("nickname"));
			} else {
				// ログイン情報が誤っている場合の処理
				user = null;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		return user;
	}

	public void doDelete(HttpSession session) {
		
		try {
			// DBへの接続処理
			getConnect();

			// SQL文の作成
			String sql = "DELETE FROM users WHERE user_id = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, ((User) session.getAttribute("loginUser")).getUserId());
			System.out.println(ps);

			ps.executeUpdate();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
	}

	/**
	 * DBのユーザ情報のユーザIDとニックネームを変更する。
	 * @return HashMap<br>
	 * "loginUser"キーで、変更後のユーザの情報を持つUserオブジェクトを取得<br>
	 * "message"キーで、エラーメッセージを表すStringオブジェクトを取得
	 */
	public Map<String, Object> editIdName(String userId, String nickname, String sessionUserId) {
		
		Map<String, Object> map = new HashMap<>();
		User loginUser = new User();
		String message = null;

		try {
			// DBへの接続処理
			getConnect();

			// SQL文の作成
			String sql = "UPDATE users SET user_id = ?, nickname = ? WHERE user_id = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, userId);
			ps.setString(2, nickname);
			ps.setString(3, sessionUserId);
			System.out.println(ps);

			ps.executeUpdate();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLIntegrityConstraintViolationException e) {
			e.printStackTrace();
			userId = sessionUserId;
			message = "既に登録されているユーザIDです。";
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		loginUser.setUserId(userId);
		loginUser.setNickname(nickname);
		
		map.put("loginUser", loginUser);
		map.put("message", message);
		return map;
	}

	/**
	 * DBのユーザ情報のパスワードを変更する。
	 * @return パスワード変更に失敗した旨のメッセージを示すStringオブジェクト<br>成功時はnull
	 */
	public String editPassword(String oldPassword, String newPassword, String newPassword2, String sessionUserId) {

		String message = null;
		
		if (newPassword.equals(newPassword2)) {

			try {
				// DBへの接続処理
				getConnect();

				// SQL文の作成
				String sql = "UPDATE users SET password = ? WHERE (user_id = ?) && (password = ?)";
				ps = con.prepareStatement(sql);
				ps.setString(1, newPassword);
				ps.setString(2, sessionUserId);
				ps.setString(3, oldPassword);
				System.out.println(ps);

				int changedRows = ps.executeUpdate();
				if (changedRows == 0) {
					message = "古いパスワードが間違っています。";
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeAll();
			}
		} else {
			message = "新しいパスワードが一致していません。";
		}
		return message;
	}

	public List<User> getRankingList() {

		List<User> rankingList = new ArrayList<User>();

		try {
			// DBへの接続処理
			getConnect();

			// SQL文の作成
			String sql = "SELECT * FROM users ORDER BY win_rate DESC LIMIT 5";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				User user = new User();
				user.setUserId(rs.getString("user_id"));
				user.setNickname(rs.getString("nickname"));
				user.setWinRate(rs.getFloat("win_rate"));
				rankingList.add(user);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		return rankingList;

	}

	
	public void getConnect() throws ClassNotFoundException, SQLException {
		Class.forName("org.mariadb.jdbc.Driver");

		String url = "jdbc:mysql://localhost/bj_nakazawa";
		String user = "root";
		String password = "";

		con = DriverManager.getConnection(url, user, password);
	}

	public void closeAll() {
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
		}
	}
}
