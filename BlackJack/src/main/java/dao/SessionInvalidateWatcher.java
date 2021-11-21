package dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

/**
 * このクラスのインスタンスをセッションに保管すると、<br>
 * セッションが破棄されるタイミングと同時にDBコネクションをクローズする。
 */
public class SessionInvalidateWatcher implements HttpSessionBindingListener {
	
	private Connection con;
	
	public SessionInvalidateWatcher(Connection con) {
		this.con = con;
	}
	
	public void valueUnbound(HttpSessionBindingEvent event) {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}
