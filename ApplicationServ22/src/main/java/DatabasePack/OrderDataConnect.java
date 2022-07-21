package DatabasePack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

public class OrderDataConnect {
	
		public static void main(String[] arg) {
			System.out.println(checkRequirements("prashantgindani@gmail.com"));
			System.out.println(checkRequirements("prashantgindani@gmail.com","103","INSERT"));
			System.out.println(checkRequirements("prashantgindani@gmail.com"));
			//System.out.println(checkRequirements("prashantgindani@gmail.com"));
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
		 ss = "SELECT oo.OrderID,pp.ProdID,Productname,prodimage,orderdeliverydate,status FROM OrderMaster as oo,productdescript as pp where oo.useremail ='"+search+"' and oo.prodid = pp.prodid;";
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
	public static String checkRequirements(String search,String orderid,String param) {
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
            		 query = "SELECT ProdID,Productname,price,mrpprice,avgrating,prodimage FROM productdescript as pp where prodid="+orderid+"";
            			
            		  Statement smt=conn.createStatement();
                      ResultSet rs= smt.executeQuery(query);
                      double charge;
                      rs.next();
                      charge=Double.parseDouble(rs.getObject(3).toString());
                     
                     
            		Date today = Calendar.getInstance().getTime();
            		String date=(today.getYear()+1900)+"/"+(today.getMonth()+1)+"/"+today.getDate();
            		String date2=(today.getYear()+1900)+"/"+(today.getMonth()+1)+"/"+(today.getDate()+4);
            		query="INSERT INTO OrderMaster(useremail,prodid,orderdate,orderdeliverydate ,deliverycharge,odbillamount,payementmode,status,paidornot,address) VALUES ('"+search+"',"+orderid+",'"+date+"','"+date2+"',"+(charge*0.05)+","+charge+",'COD','PLACED','NO','12, Mahadev Soc, Vadodara')";
                   	mymsg="Item Ordered";
                    	
                }
            	else {
                	query="UPDATE OrderMaster SET status='CANCEL' WHERE OrderID="+orderid+"";
                	mymsg="Order Cancel";
            	}
            	  Statement smt=conn.createStatement();
            	  System.out.println(query);
                  smt.executeUpdate(query);
                  
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
public static String objectify(String orderid,String prodid,String title,String imageurl,String orderdeliverydate,String status) {
		String a="{\"orderid\":\""+orderid+"\", \"prodid\":\""+prodid+"\",\"title\":\""+title+"\", \"imageurl\":\""+imageurl+"\", \"orderdeliverydate\":\""+orderdeliverydate+"\",\"status\":\""+status+"\"}";
		return a;
	}
}
