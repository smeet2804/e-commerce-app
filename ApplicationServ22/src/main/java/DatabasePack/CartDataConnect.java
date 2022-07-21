package DatabasePack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class CartDataConnect {

	public static void main(String[] arg) {
//		System.out.println(checkRequirements("prashantgindani@gmail.com"));
//		System.out.println(checkRequirements("prashantgindani@gmail.com","121","INSERT"));
//		System.out.println(checkRequirements("prashantgindani@gmail.com"));
//		System.out.println(checkRequirements("prashantgindani@gmail.com","121","INSERT"));
//		System.out.println(checkRequirements("prashantgindani@gmail.com"));
//		System.out.println(checkRequirements("prashantgindani@gmail.com","105","DELETE"));
//		System.out.println(checkRequirements("prashantgindani@gmail.com"));
	}
	public static String checkRequirements(String search) {
		String mymsg="",ss="";
		StringTokenizer st1=new StringTokenizer(search," ");
		ArrayList<String> arr=new ArrayList<String>();
		 while (st1.hasMoreTokens()) {
	            arr.add(st1.nextToken());
	            }
		 int i=0;
		 for(i=0;i<arr.size()-1;i++) {
			 ss += "LOWER(Productname) like '%" + arr.get(i) + "%' OR LOWER(tags) like '%" + arr.get(i) + "%' OR ";
		 }
		 ss += "LOWER(Productname) like '%" + arr.get(i) + "%' OR LOWER(tags) like '%" + arr.get(i) + "%'";
		 ss = "SELECT cc.ProdID,Productname,price,mrpprice,prodimage,cc.quantity FROM CartMaster as cc,productdescript as pp where userid ='"+search+"' and cc.prodid = pp.prodid;";
		System.out.println(ss);
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
            Statement smt=conn.createStatement();
            ResultSet rs= smt.executeQuery(ss);
            arr=new ArrayList<String>();
            String ans="[";
            while(rs.next()) {
            	ans+=objectify(rs.getObject(1).toString(),rs.getObject(2).toString(),rs.getObject(3).toString(),rs.getObject(4).toString(),rs.getObject(5).toString(),rs.getObject(6).toString())+",";
            }
           if(ans.length()>0) {
            ans=ans.substring(0,ans.length()-1);
            }
            ans+="]";
           mymsg=ans;
            }
            
            
             conn.close();           
		}
		catch(Exception e) {
			e.printStackTrace();	
		}
		return mymsg;
	}
	public static String checkRequirements(String search,String prodid,String param) {
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
            //querry the database
           	 String query;
            	if(param.equalsIgnoreCase("INSERT")) {
            		query="select * from CartMaster WHERE prodid="+prodid+" AND userid='"+search+"'";
                    Statement smt=conn.createStatement();
                    ResultSet rs= smt.executeQuery(query);
                    
                    if(!rs.next()) {
                    	query="INSERT INTO CartMaster(userid,prodid,quantity) VALUES ('"+search+"',"+prodid+",1)";
                    	mymsg="Item Added";
                    	
                    }
                    else {
                    	int k=Integer.parseInt(rs.getObject(3).toString())+1;
                    	query="UPDATE CartMaster SET quantity="+k+" WHERE prodid="+prodid+" AND userid='"+search+"'";
                    	mymsg="Quantity Increased";
                    }
                  }
            	else {
            		query="DELETE FROM CartMaster WHERE prodid="+prodid+" AND userid='"+search+"'";
            		mymsg="Item removed";
            	}
            	  Statement smt=conn.createStatement();
                  smt.executeUpdate(query);
                  System.out.println(query);
                 
                 }
                 
                 
                             
     		//System.out.println("Connected");
                 mymsg="{\"message\":\""+mymsg+"\"}";
     		            
             conn.close();           
		}
		catch(Exception e) {
			e.printStackTrace();	
		}
		return mymsg;
	}
	public static String objectify(String prodid,String title,String price,String mrp,String imageurl,String quantity) {
		String a="{\"prodid\":\""+prodid+"\",\"title\":\""+title+"\", \"price\":\""+price+"\",\"mrp\":\""+mrp+"\", \"imageurl\":\""+imageurl+"\", \"quantity\":\""+quantity+"\"}";
		return a;
	}

}
