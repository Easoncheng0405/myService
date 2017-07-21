package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlConnection{
	private static final String URL = "jdbc:mysql://localhost:3306/common?";
	private static final String NAME = "root";
	private static final String PASSWORD = "0405";
	private Connection conn = null;
	public Connection connectDataBase() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("驱动加载异常");
			return null;
		}
		try {
			conn = DriverManager.getConnection(URL, NAME, PASSWORD);
			System.out.println("数据库连接成功");
		} catch (SQLException e) {
			System.out.println("数据库连接失败");
			return null;
		}
		return conn;
	}
	
}
