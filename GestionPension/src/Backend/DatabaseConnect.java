package Backend;
import java.sql.*;

public class DatabaseConnect {
	
	private final String url="jdbc:postgresql://localhost/gestionpension";
	private final String user="postgres";
	private final String password="admin";
	
	public Connection connect() {
		Connection connect=null;
		try{
			connect=DriverManager.getConnection(url,user,password);
		}catch(Exception e) {
			System.out.println(e.getMessage());
			
		}
		if(connect!=null) {
			System.out.println("Connected successfully");
		}else {
			System.out.println("Not connected");
		}
		return connect;
	}
	
	
}
