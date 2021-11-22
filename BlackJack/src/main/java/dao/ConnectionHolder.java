package dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

/**
 * このクラスのインスタンスをセッションに保管すると、<br>
 * セッションが破棄されるタイミングと同時にDBコネクションをクローズする。
 */
public class ConnectionHolder implements HttpSessionBindingListener {
	
	private Connection con = null;
	
	public ConnectionHolder(Connection con) {
		this.con = con;
	}

	public Connection getCon() {
		return con;
	}
	
	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		try {
//			System.out.println("con.isClosed()_before -> " + con.isClosed());
			con.close();
//			System.out.println("con.isClosed()_after  -> " + con.isClosed() + "\n");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}
