package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.http.HttpSession;

import exception.MessageManager;
import model.User;

//DBのusersテーブルのみを扱うDAOクラス
public class UserDao {

	private Connection con = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	
	public void doRegister(String userId, String nickname, String password, String password2) {
		
		if(password.equals(password2)) {
			
			try {
				//DBへの接続処理
				getConnect();
				
				//SQL文の作成(win_rateカラムには0を設定)
				String sql = "INSERT INTO users VALUES (?, ?, ?, 0)";
				ps = con.prepareStatement(sql);
				ps.setString(1, userId);
				ps.setString(2, nickname);
				ps.setString(3, password);
				System.out.println(ps);
				
				ps.executeUpdate();
				
			} catch(ClassNotFoundException e) {
				e.printStackTrace();
			} catch(SQLIntegrityConstraintViolationException e) {
				e.printStackTrace();
				new MessageManager("登録済みかもしくは不正なユーザIDです。");
			} catch(SQLException e) {
				e.printStackTrace();
			} finally {
				closeAll();
			}
		}else {
			new MessageManager("パスワードが一致していません。");
		}
	}
	
	public User loginUser(String userId, String password) {
		User user = new User();
		
		try {
			//DBへの接続処理
			getConnect();
			
			//SQL文の作成
			String sql = "SELECT * FROM users WHERE (user_id = ?) && (password = ?)";
			ps = con.prepareStatement(sql);
			ps.setString(1, userId);
			ps.setString(2, password);
			System.out.println(ps);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				//ログイン情報が正しい場合の処理
				user.setUserId(rs.getString("user_id"));
				user.setNickname(rs.getString("nickname"));
			}else {
				//ログイン情報が誤っている場合の処理
				new MessageManager("ログインに失敗しました。");
			}
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		return user;
	}
	
	public void doDelete(HttpSession session) {
		try {
			//DBへの接続処理
			getConnect();
			
			//SQL文の作成
			String sql = "DELETE FROM users WHERE user_id = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, ((User)session.getAttribute("loginUser")).getUserId());
			System.out.println(ps);
			
			ps.executeUpdate();
			
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
	}
	
	public User editIdName(String userId, String nickname, String sessionUserId) {
		User user = new User();
		
		try {
			//DBへの接続処理
			getConnect();
			
			//SQL文の作成
			String sql = "UPDATE users SET user_id = ?, nickname = ? WHERE user_id = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, userId);
			ps.setString(2, nickname);
			ps.setString(3, sessionUserId);
			System.out.println(ps);
			
			ps.executeUpdate();
			
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		user.setUserId(userId);
		user.setNickname(nickname);
		return user;
	}
	
	
	public void getConnect() throws ClassNotFoundException, SQLException{
		Class.forName("org.mariadb.jdbc.Driver");

		String url = "jdbc:mysql://localhost/bj_nakazawa";
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
