package DB;

public class User {
	private String login;
	private String password;
	private int id;
	
	public User(int id, String login, String password) {
		this.id = id;
		this.login = login;
		this.password = password;
	}	
	
	public void setLogin(String login) {
		this.login = login;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getLogin() {
		return login;
	}
	public String getPassword() {
		return password;
	}

}
