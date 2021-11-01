package dao;

import java.sql.SQLException;
import java.sql.Timestamp;
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

	public void addHistory(History history) {
		
		String userId = history.getUserId();
		Timestamp time = history.getTime();
		int result = history.getResult();
		
		try {
			// SQL文
			String sql = "INSERT INTO history (user_id, time, result) VALUES (?, ?, ?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, userId);
			ps.setTimestamp(2, time);
			ps.setInt(3, result);
			
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			message = "SQL実行中に例外が発生しました";
		}
	}

	public List<History> getHistoryList(User loginUser) {

		List<History> historyList = new ArrayList<>();

		try {
			// SQL文
			String sql = "SELECT time, result FROM history WHERE user_id = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, loginUser.getUserId());
			rs = ps.executeQuery();

			while (rs.next()) {
				History history = new History();
				history.setTime(rs.getTimestamp("time"));
				history.setResult(rs.getInt("result"));
				historyList.add(history);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			message = "SQL実行中に例外が発生しました";
		} finally {
			closeAll();
		}
		return historyList;
	}
	
	public void setWinRate(User loginUser) {
		
		String userId = loginUser.getUserId();
		
		try {
			//SQL文(勝った回数を取得)
			String sqlWin = "SELECT count(*) FROM history WHERE (user_id = ?) && (result = 1)";
			ps = con.prepareStatement(sqlWin);
			ps.setString(1, userId);
			rs = ps.executeQuery();
			rs.next();
			int win = rs.getInt("count(*)");
			
			//SQL文(総対戦回数を取得)
			String sqlResult = "SELECT count(*) FROM history WHERE user_id = ?";
			ps = con.prepareStatement(sqlResult);
			ps.setString(1, userId);
			rs = ps.executeQuery();
			rs.next();
			int all = rs.getInt("count(*)");
			
			float winRate = (float)win/(float)all;
			
			//SQL文(勝率を更新)
			String sql = "UPDATE users SET win_rate = ? WHERE user_id = ?";
			ps = con.prepareStatement(sql);
			ps.setFloat(1, winRate);
			ps.setString(2, userId);
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			message = "SQL実行中に例外が発生しました";
		} finally {
			closeAll();
		}
		
	}
}
