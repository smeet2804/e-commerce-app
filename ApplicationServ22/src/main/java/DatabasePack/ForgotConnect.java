package DatabasePack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ForgotConnect {
	public static void main(String[] arg) {
		System.out.println(checkRequirements("prashantgindani@gmail.com"));
	}
	public static String checkRequirements(String email) {
		String mymsg="",realpasscode="";
		// TODO Auto-generated method stub
		try {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		Connection conn;//= DriverManager.getConnection(url);
			String dbURL = "jdbc:sqlserver://abcd-pc\\sqlexpress;databaseName=ApplicationDatabase";
            String user = "saa";
            String pass = "pp11pp11";
            
            conn = DriverManager.getConnection(dbURL, user, pass);

            if (conn != null) {
                /*DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
                System.out.println("Driver name: " + dm.getDriverName());
                System.out.println("Driver version: " + dm.getDriverVersion());
                System.out.println("Product name: " + dm.getDatabaseProductName());
                System.out.println("Product version: " + dm.getDatabaseProductVersion());
            System.out.println("");
           */
            //querry the database
    	 String query="select * from USER_Master where user_email='"+email+"'";
         Statement smt=conn.createStatement();
         ResultSet rs= smt.executeQuery(query);
         
         if(rs.next()) {
        	 
        	 mymsg="mail not sent";
         }
         else {
         	mymsg="User not exists";
         }
         }
            
            
                        
		//System.out.println("Connected");
            mymsg="{\"message\":\""+mymsg+"\"}";
		}
		catch(Exception e) {
			System.out.println(e);	
		}
		return mymsg;
	}

}
