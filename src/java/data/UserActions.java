package data; 

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserActions {

	private UserActions() {

	}

	// Add new user to database
	public static void insertInDB(User user) {
		try {
			// Prepare our SQL string
			final String sqlInsert = "INSERT INTO users (login, password, isAdmin) "
					+ "VALUES ('"
					+ user.getLogin()
					+ "', '"
					+ user.getPassword()
					+ "', '"
					+ user.getIsAdmin() + "')";
			// Get DB connection
			Connection con = DBC.getInstance().getConnection();
			// Convert SQL string to SQL Statement
			final PreparedStatement ps = con.prepareStatement(sqlInsert);
			// Send our SQL Statement
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Load user from database
	public static User getUserFromDB(String login, String password) {
		try {
			// Prepare our SQL string
			final String sqlGet = "SELECT * FROM users WHERE login = '"
					+ login + "' AND  password = '" + password + "'";
			// Get DB connection
			Connection con = DBC.getInstance().getConnection();
			// Convert SQL string to SQL Statement
			final PreparedStatement ps = con.prepareStatement(sqlGet);
			// Send our SQL Statement and get the result set
			final ResultSet rs = ps.executeQuery();
			// Store some temporary variables
			String _login = "", _password = "";
			Integer _id = 0, _isAdmin = 0;
			// Parsing result set
			if (rs.next()) {
				_id = rs.getInt("id");
				_login = rs.getString("login");
				_password = rs.getString("password");
				_isAdmin = rs.getInt("isAdmin");
			}
			// Create user from database data
			User tUser = new User(_login, _password, _isAdmin);
			tUser.setId(_id);
                                                      if(_id == 0){
                                                          return null;
                                                      }

			return tUser;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	// Check if the database includes login or not
	// true-exist, false-not
	public static Boolean checkLoginInDB(String login) {
		try {
			// Prepare our SQL string
			final String sqlCheck = "SELECT count(*) FROM Users WHERE login = '"
					+ login + "'";
			// Get DB connection
			Connection con = DBC.getInstance().getConnection();
			// Convert SQL string to SQL Statement
			final PreparedStatement ps = con.prepareStatement(sqlCheck);
			// Send our SQL Statement and get the result set
			final ResultSet rs = ps.executeQuery();
			// Parsing result set
			if (rs.next()) {
				// Getting login entries count
				final int count = rs.getInt(1);
				if (count >= 1) {
					return true;
				}
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static User getUserByID(Integer id) {
		try {
			// Prepare our SQL string
			final String sqlGet = "SELECT * FROM Users WHERE id = '" + id + "'";
			// Get DB connection
			Connection con = DBC.getInstance().getConnection();
			// Convert SQL string to SQL Statement
			final PreparedStatement ps = con.prepareStatement(sqlGet);
			// Send our SQL Statement and get the result set
			final ResultSet rs = ps.executeQuery();
			// Store some temporary variables
			String _login = "", _password = "";
			Integer _id = 0, _isAdmin = 0;
			// Parsing result set
			if (rs.next()) {
				_id = rs.getInt("id");
				_login = rs.getString("login");
				_password = rs.getString("password");
				_isAdmin = rs.getInt("isAdmin");
			}
			// Create user from database data
			User tUser = new User(_login, _password, _isAdmin);
			tUser.setId(_id);

			return tUser;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
