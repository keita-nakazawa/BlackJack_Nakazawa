package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import exception.MessageManager;

//DBのusersテーブルを扱うDAOクラス
public class UserDao {

	private Connection con = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	
	public void registerUser(String id, String nickname, String password, String password2) {
		
		if(password.equals(password2)) {
			
			try {
				//DBへの接続処理
				getConnect();
				
				//SQL文の作成(win_rateには0を設定)
				String sql = "INSERT INTO users VALUES (?, ?, ?, 0)";
				ps = con.prepareStatement(sql);
				ps.setString(1, id);
				ps.setString(2, nickname);
				ps.setString(3, password);
				System.out.println(ps);
				
				ps.executeUpdate();
				
			} catch(ClassNotFoundException e) {
				e.printStackTrace();
			} catch(SQLIntegrityConstraintViolationException e) {
				e.printStackTrace();
				new MessageManager("登録済みIDです。");
			} catch(SQLException e) {
				e.printStackTrace();
			} finally {
				closeAll();
			}
		}else {
			new MessageManager("パスワード2回目が間違っています。");
		}
	}
	
	
	public void getConnect() throws ClassNotFoundException, SQLException{
		Class.forName("org.mariadb.jdbc.Driver");

		String url = "jdbc:mysql://localhost/blackjack";
		String user = "root";
		String password = "";
		
		con = DriverManager.getConnection(url, user, password);
	}
	
	public void closeAll() {
		try {
			if(con != null) {
				con.close();
			}
			if(ps != null) {
				ps.close();
			}
			if(rs != null) {
				rs.close();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}	
	}
}
