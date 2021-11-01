package dao;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import model.User;

/**
 * DBのusersテーブルのみを扱うDAOクラス
 */
public class UserDao extends BaseDao {

	/**
	 * コンストラクタ<br>
	 * 初期処理としてDBに接続する。
	 */
	public UserDao() {
		getConnect();
	}

	/**
	 * DBにユーザ情報を新規登録する。
	 */
	public void doRegister(String userId, String nickname, String password, String password2) {

		if (password.equals(password2)) {

			try {
				// SQL文(win_rateカラムには0を設定)
				String sql = "INSERT INTO users VALUES (?, ?, ?, 0)";
				ps = con.prepareStatement(sql);
				ps.setString(1, userId);
				ps.setString(2, nickname);
				ps.setString(3, password);

				ps.executeUpdate();

			} catch (SQLIntegrityConstraintViolationException e) {
				e.printStackTrace();
				message = "登録済みかもしくは不正なユーザIDです。";
			} catch (SQLException e) {
				e.printStackTrace();
				message = "SQL実行中に例外が発生しました。";
			} finally {
				closeAll();
			}
		} else {
			message = "パスワードが一致していません。";
		}
	}

	public User getLoginUser(String userId, String password) {

		User user = new User();

		try {
			// SQL文
			String sql = "SELECT * FROM users WHERE (user_id = ?) && (password = ?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, userId);
			ps.setString(2, password);

			rs = ps.executeQuery();

			if (rs.next()) {
				// ログイン情報が正しい場合の処理
				user.setUserId(rs.getString("user_id"));
				user.setNickname(rs.getString("nickname"));
			} else {
				// ログイン情報が誤っている場合の処理
				message = "ログインに失敗しました。";
			}

		} catch (SQLException e) {
			e.printStackTrace();
			message = "SQL実行中に例外が発生しました";
		} finally {
			closeAll();
		}

		return user;
	}

	public void doDelete(User loginUser) {

		try {
			// SQL文
			String sql = "DELETE FROM users WHERE user_id = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, loginUser.getUserId());

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			message = "SQL実行中に例外が発生しました";
		} finally {
			closeAll();
		}
	}

	/**
	 * DBのユーザ情報のユーザIDとニックネームを変更する。
	 */
	public User editIdName(String userId, String nickname, String sessionUserId) {

		User loginUser = new User();

		try {
			// SQL文
			String sql = "UPDATE users SET user_id = ?, nickname = ? WHERE user_id = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, userId);
			ps.setString(2, nickname);
			ps.setString(3, sessionUserId);

			ps.executeUpdate();
			message = "ユーザID、ニックネームを変更しました。";

		} catch (SQLIntegrityConstraintViolationException e) {
			e.printStackTrace();
			userId = sessionUserId;
			message = "既に登録されているユーザIDです。";
		} catch (SQLException e) {
			e.printStackTrace();
			message = "SQL実行中に例外が発生しました。";
		} finally {
			closeAll();
		}
		loginUser.setUserId(userId);
		loginUser.setNickname(nickname);

		return loginUser;
	}

	/**
	 * DBのユーザ情報のパスワードを変更する。
	 */
	public void editPassword(String oldPassword, String newPassword, String newPassword2, String sessionUserId) {

		if (newPassword.equals(newPassword2)) {

			try {
				// SQL文
				String sql = "UPDATE users SET password = ? WHERE (user_id = ?) && (password = ?)";
				ps = con.prepareStatement(sql);
				ps.setString(1, newPassword);
				ps.setString(2, sessionUserId);
				ps.setString(3, oldPassword);

				int changedRows = ps.executeUpdate();

				if (changedRows == 0) {
					message = "古いパスワードが間違っています。";
				} else {
					message = "パスワードを変更しました。";
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				message = "SQL実行中に例外が発生しました";
			} finally {
				closeAll();
			}
		} else {
			message = "新しいパスワードが一致していません。";
		}
	}

	public List<User> getRankingList() {

		List<User> rankingList = new ArrayList<User>();

		try {
			// SQL文
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
		} catch (SQLException e) {
			e.printStackTrace();
			message = "SQL実行中に例外が発生しました";
		} finally {
			closeAll();
		}
		return rankingList;
	}

	public int getPopulation() {

		int population = 0;

		try {
			// SQL文
			String sql = "SELECT * FROM users";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				population += 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			message = "SQL実行中に例外が発生しました";
		} 
		return population;
	}
}
