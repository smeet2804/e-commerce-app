package DatabasePack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SignupConnect {
	public static void main(String[] arg) {
		System.out.println(checkRequirements("prashantgindani1@gmail.com", "Prashant", "Prasdca@12"));
	}
	public static String checkRequirements(String email,String fullname,String passcode) {
		String mymsg="";
		// TODO Auto-generated method stub
		try {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		Connection conn;//= DriverManager.getConnection(url);
			String dbURL = "jdbc:sqlserver://abcd-pc\\sqlexpress;databaseName=ApplicationDatabase";
            String user = "saa";
            String pass = "pp11pp11";
            
            conn = DriverManager.getConnection(dbURL, user, pass);

            if (conn != null) {
            //query the database
            String query="select * from USER_Master where user_email='"+email+"'";
            Statement smt=conn.createStatement();
            ResultSet rs= smt.executeQuery(query);
            
            if(!rs.next()) {
            	query="INSERT INTO USER_Master(user_email,firstname,lastname,password) VALUES ('"+email+"','"+fullname+"','','"+passcode+"')";
            	smt=conn.createStatement();
                smt.executeUpdate(query);
                mymsg="User created";
            	
            }
            else {
            	mymsg="User Already exists";
            }
            }
            
            conn.close();
                        
		//System.out.println("Connected");
            mymsg="{\"message\":\""+mymsg+"\"}";
		}
		catch(Exception e) {
			System.out.println(e);	
		}
		return mymsg;
	}

}
