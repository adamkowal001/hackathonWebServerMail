package DB;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import email.Email;
 

public class DBsupport {
 
    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:DBsupport.db";
 
    private Connection conn;
    private Statement stat;
 
    public DBsupport() {
        try {
            Class.forName(DBsupport.DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("Brak sterownika JDBC");
            e.printStackTrace();
        }
 
        try {
            conn = DriverManager.getConnection(DB_URL);
            stat = conn.createStatement();
        } catch (SQLException e) {
            System.err.println("Problem z otwarciem polaczenia");
            e.printStackTrace();
        }
 
        createTables();
    }
 
    public boolean createTables()  {
        String createUsers = "CREATE TABLE IF NOT EXISTS users (id_user INTEGER PRIMARY KEY AUTOINCREMENT, user_login varchar(255), user_password varchar(255))";
        String createMails = "CREATE TABLE IF NOT EXISTS emails (id_email INTEGER PRIMARY KEY AUTOINCREMENT, author varchar(255), user_login varchar(255), content text)";
        try {
        	stat.execute(createUsers);
            stat.execute(createMails);
        } catch (SQLException e) {
            System.err.println("Error - creating tables");
            e.printStackTrace();
            return false;
        }
        return true;
    }
 
    public boolean insertUsers(String login, String password) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement(
                    "insert into users values (NULL, ?, ?);");
            prepStmt.setString(1, login);
            prepStmt.setString(2, password);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Error - insert users");
            e.printStackTrace();
            return false;
        }
        return true;
    }
 
    public boolean insertMails(String from, String to, String body) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement(
                    "insert into emails values (NULL, ?, ?, ?);");
            prepStmt.setString(1, from);
            prepStmt.setString(2, to);
            prepStmt.setString(3, body);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Error - insert emails");
            return false;
        }
        return true;
    }
    
    public List<User> selectUsers() {
    	List<User> users = new ArrayList<User>();
    	
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM users");
            int id=-1;
            String login="NOT FOUND";
            String password= "NOT FOUND";
            while(result.next()) {
	                id = result.getInt("id_user");
	                login = result.getString("user_login");
	                password = result.getString("user_password");
	                users.add(new User(id, login, password));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return users;
    }
    
    public List<Email> selectMails(String login) {
    	List<Email> emails = new ArrayList<Email>();
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM emails WHERE user_login = " + "\"" + login + "\"");
            int id=-1;
            String to = "NOT FOUND";
            String from = "NOT FOUND";
            String content = "NOT FOUND";
            
            while(result.next()) {
	                id = result.getInt("id_email");
	                from = result.getString("author");
	                to = result.getString("user_login");
	                content = result.getString("content");
	                emails.add(new Email(id, from, to, content));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return emails;
    }
    
   public boolean delete(String name){
	   try {
           return stat.execute("DROP TABLE " + name);
       } catch (SQLException e) {
           e.printStackTrace();
           return false;
       }   
   }
 
   public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.err.println("Problem z zamknieciem polaczenia");
            e.printStackTrace();
        }
    }
   
   public static void main(String[] args) {
	   
	   DBsupport obj = new DBsupport();
	   obj.delete("users");
	   obj.delete("emails");
	   obj.createTables();
	   obj.insertUsers("AK@gmail.com", "zaq");
	   obj.insertUsers("KB@gmail.com", "wsx");
	   obj.insertUsers("MK@gmail.com", "cde");
	   
	   obj.insertMails("fr", "AK@gmail.com" , "elo elo ssAK");
	   obj.insertMails("fr", "KB@gmail.com" , "elo elo KB");
	   
	   List<User> lista = obj.selectUsers();
	   List<Email> listaEmail1 = obj.selectMails("AK@gmail.com");
	   List<Email> listaEmail2 = obj.selectMails("KB@gmail.com");
	   
	   System.out.println("size " + listaEmail1.size() + " " + listaEmail1.get(0).getContent());
	   System.out.println("size " + listaEmail2.size() + " " + listaEmail2.get(0).getContent());
	   
	   System.out.println("size: " + lista.size() + " " + lista.get(0).getLogin() + " " + lista.get(1).getLogin() + " " + lista.get(2).getLogin());
	   obj.closeConnection();

//	   obj.insertWypozycz(1, 1);
//	   obj.selectCzytelnicy();
   }
}