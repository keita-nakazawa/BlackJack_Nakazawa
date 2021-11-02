package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.History;
import model.User;

/**
 * DBのhistoryテーブルのみを扱うDAOクラス
 */
public class HistoryDao extends BaseDao {

	/**
	 * コンストラクタ<br>
	 * 初期処理としてDBに接続する。
	 */
	public HistoryDao() {
		getConnect();
	}

	/**
	 * historyテーブルにユーザIDと勝敗結果(int)を記録する。<br>
	 * プレイ時刻はDB側のDEFAULT句で自動的に記録される。
	 */
	public void addHistory(History history) {

		String userId = history.getUserId();
		int result = history.getResult();

		try {
			if (con != null) {
				String sql = "INSERT INTO history (user_id, result) VALUES (?, ?)";
				ps = con.prepareStatement(sql);
				ps.setString(1, userId);
				ps.setInt(2, result);
				ps.executeUpdate();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			message = "SQL実行中に例外が発生しました";
		}
	}

	/**
	 * @return これまでのゲームのプレイ時刻と勝敗結果(int)を有するHistoryオブジェクトのリスト。
	 */
	public List<History> getHistoryList(User loginUser) {

		List<History> historyList = new ArrayList<>();

		try {
			if (con != null) {
				String sql = "SELECT timestamp, result FROM history WHERE user_id = ?";
				ps = con.prepareStatement(sql);
				ps.setString(1, loginUser.getUserId());
				rs = ps.executeQuery();

				while (rs.next()) {
					History history = new History();
					history.setTimestamp(rs.getTimestamp("timestamp"));
					history.setResult(rs.getInt("result"));
					historyList.add(history);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			message = "SQL実行中に例外が発生しました";
		} finally {
			closeAll();
		}
		return historyList;
	}
}
