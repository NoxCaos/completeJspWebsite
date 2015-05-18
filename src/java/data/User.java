package data;

public class User {
	private Integer id;
	private String login;
	private String password;
	private int isAdmin;
	
	public User(String login, String password, int isAdmin) {
		this.login = login;
		this.password = password;
		this.isAdmin = isAdmin;
	}

	public Integer getId() {
		return id;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}
	
	public int getIsAdmin() {
		return this.isAdmin;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setIsAdmin(int a) {
		this.isAdmin = a;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", login=" + login + ", password=" + password
				+ ", isAdmin=" + this.isAdmin + "]";
	}
}