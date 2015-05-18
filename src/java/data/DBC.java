package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBC {
	private static DBC instance;
	
	private Connection con = null;
	
	private String url = "jdbc:mysql://localhost:3306/livingessentials";
	private String user = "root";
	private String password = "usbw";
	private String driver = "com.mysql.jdbc.Driver";

	private DBC() {
	
	}

	public void connect() {
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			System.out.println("Connected");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void disconnect() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static DBC getInstance() {
		if (instance == null) {
			instance = new DBC();
			System.out.println("Created DBC instance");
			instance.connect();
		}
		return instance;
	}
	
	public Connection getConnection() {
		System.out.println("Got connection to manage DB data");
		return con;
	}
}
