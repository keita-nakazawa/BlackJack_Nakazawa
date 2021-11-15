package dao;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import model.User;

/**
 * DBのusersテーブルのみを扱うDAOクラス
 */
public class UserDao extends BaseDao {

	public UserDao(HttpSession session) {
		super(session);
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
	 * @return 入力内容が正しい場合は、そのユーザIDとニックネームとチップ所持枚数を有するUserオブジェクト
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
					user.setChip(rs.getInt("chip"));
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
			if (con != null) {
				String sql = "DELETE FROM users WHERE user_id = ?";
				ps = con.prepareStatement(sql);
				ps.setString(1, loginUser.getUserId());
				ps.executeUpdate();
			}

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
			if (con != null) {
				String sql = "UPDATE users SET user_id = ?, nickname = ? WHERE user_id = ?";
				ps = con.prepareStatement(sql);
				ps.setString(1, userId);
				ps.setString(2, nickname);
				ps.setString(3, sessionUserId);
				ps.executeUpdate();
				message = "ユーザID、ニックネームを変更しました。";
			}

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
				if (con != null) {
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
			if (con != null) {
				String sql = "SELECT count(*) FROM users";
				ps = con.prepareStatement(sql);
				rs = ps.executeQuery();

				rs.next();
				population = rs.getInt("count(*)");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			message = "SQL実行中に例外が発生しました";
		} finally {
			closeAll();
		}
		return population;
	}

	/**
	 * usersテーブルのchipカラムをもとにチップ所持枚数ランキングトップ5を生成する。
	 * 
	 * @return ニックネームとチップ所持枚数を有するUserオブジェクトのリスト
	 */
	public List<User> getRankingList() {

		List<User> rankingList = new ArrayList<User>();

		try {
			if (con != null) {
				String sql = "SELECT * FROM users ORDER BY chip DESC LIMIT 5";
				ps = con.prepareStatement(sql);
				rs = ps.executeQuery();

				while (rs.next()) {
					User user = new User();
					user.setNickname(rs.getString("nickname"));
					user.setChip(rs.getInt("chip"));
					rankingList.add(user);
				}
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
	 * usersテーブルのchipカラムに指定枚数のチップを追加
	 * @param chip 追加されるチップ枚数。負数の場合は差し引かれる。
	 */
	public void addChip(User loginUser, int chip) {

		String userId = loginUser.getUserId();

		try {
			if (con != null) {
				String sql = "UPDATE users SET chip = chip + ? WHERE user_id = ?";
				ps = con.prepareStatement(sql);
				ps.setInt(1, chip);
				ps.setString(2, userId);
				ps.executeUpdate();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			message = "SQL実行中に例外が発生しました";
		} finally {
			closeAll();
		}
	}

}
