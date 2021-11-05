package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import model.History;
import model.User;

/**
 * DBのusersテーブルのみを扱うDAOクラス
 */
public class UserDao extends BaseDao {

	/**
	 * コンストラクタ<br>
	 * 初期処理として、セッションに保存されているDBコネクションを参照するかDBに新規接続する。
	 */
	public UserDao(HttpSession session) {

		Connection sessionCon = (Connection) session.getAttribute("con");

		if (sessionCon != null) {
			con = sessionCon;
		} else {
			getConnect(session);
		}
	}

	/**
	 * DBにユーザ情報を新規登録する。 conのnullチェックを行う。
	 */
	public void doRegister(String userId, String nickname, String password, String password2) {

		if (password.equals(password2)) {

			try {
				if (con != null) {
					String sql = "INSERT INTO users(user_id, nickname, password) VALUES (?, ?, ?)";
					ps = con.prepareStatement(sql);
					ps.setString(1, userId);
					ps.setString(2, nickname);
					ps.setString(3, password);
					ps.executeUpdate();
				}

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

	/**
	 * ログインフォームの入力内容が正しいかDBに問い合わせる。 conのnullチェックを行う。
	 * 
	 * @return 入力内容が正しい場合は、そのユーザIDとニックネームを有するUserオブジェクト
	 */
	public User getLoginUser(String userId, String password) {

		User user = new User();

		try {
			if (con != null) {
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
	 * 
	 * @return 変更後のユーザIDとニックネームを有するUserオブジェクト
	 */
	public User editIdName(String userId, String nickname, String sessionUserId) {

		User loginUser = new User();

		try {
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

	/**
	 * usersテーブルから総プレイ人口を求める。
	 */
	public int getPopulation() {

		int population = 0;

		try {
			String sql = "SELECT count(*) FROM users";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			rs.next();
			population = rs.getInt("count(*)");

		} catch (SQLException e) {
			e.printStackTrace();
			message = "SQL実行中に例外が発生しました";
		} finally {
			closeAll();
		}
		return population;
	}

	/**
	 * usersテーブルのwin_rateカラムをもとに勝率ランキングトップ5を生成する。
	 * 
	 * @return ニックネームと勝率を有するUserオブジェクトのリスト
	 */
	public List<User> getRankingList() {

		List<User> rankingList = new ArrayList<User>();

		try {
			String sql = "SELECT * FROM users ORDER BY win_rate DESC LIMIT 5";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				User user = new User();
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

	/**
	 * usersテーブルからログイン中ユーザの情報を取得する。
	 * 
	 * @return 勝利回数、敗北回数、引き分け回数、勝率を有するUserオブジェクト
	 */
	public User getUserInfo(User loginUser) {

		User user = new User();
		String userId = loginUser.getUserId();

		try {
			String sql = "SELECT * FROM users WHERE user_id = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, userId);
			rs = ps.executeQuery();

			rs.next();
			user.setWin(rs.getInt("win"));
			user.setLose(rs.getInt("lose"));
			user.setDraw(rs.getInt("draw"));
			user.setWinRate(rs.getFloat("win_rate"));

		} catch (SQLException e) {
			e.printStackTrace();
			message = "SQL実行中に例外が発生しました";
		} finally {
			closeAll();
		}
		return user;
	}

	/**
	 * usersテーブルのwin,lose,drawカラムのいずれかに1を加算し、win_rateカラムを更新する。
	 */
	public void updateResult(User loginUser, History history) {

		String userId = loginUser.getUserId();
		String strResult = history.getStrResult();

		try {
			// win,lose,drawカラムのいずれかに1を加算
			String sql = String.format("UPDATE users SET %s = %s + 1 WHERE user_id = ?", strResult, strResult);
			ps = con.prepareStatement(sql);
			ps.setString(1, userId);
			ps.executeUpdate();

			// win_rateカラムを更新
			sql = "UPDATE users SET win_rate = 100 * win / (win + lose + draw) WHERE user_id = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, userId);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			message = "SQL実行中に例外が発生しました";
		} finally {
			closeAll();
		}
	}

}
