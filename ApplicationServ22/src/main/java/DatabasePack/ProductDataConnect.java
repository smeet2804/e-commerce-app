package DatabasePack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ProductDataConnect {
	public static void main(String[] arg) {
		System.out.println(checkRequirements("102","SEARCH"));
	}
	public static String checkRequirements(String search) {
		String mymsg="",ss="";
		if(search.length()==0) {
			search="%";
		}
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
		 ss = "SELECT ProdID,Productname,price,mrpprice,avgrating,prodimage FROM productdescript as pp where ("+ss+")";
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
	public static String checkRequirements(String prodid,String param) {
		String mymsg="",ss="";
		 ss = "SELECT ProdID,Productname,price,mrpprice,avgrating,prodimage FROM productdescript as pp where prodid="+prodid+"";
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
            //querry the database
            Statement smt=conn.createStatement();
            ResultSet rs= smt.executeQuery(ss);
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
	public static String objectify(String prodid,String title,String price,String mrp,String ratings,String imageurl) {
		String temp="Increasing impression interested expression he my at. Respect invited request charmed me warrant to. Expect no pretty as do though so genius afraid cousin. Girl when of ye snug poor draw. Mistake totally of in chiefly. Justice visitor him entered for. Continue delicate as unlocked entirely mr relation diverted in. Known not end fully being style house. An whom down kept lain name so at easy. Alteration literature to or an sympathize mr imprudence. Of is ferrars subject as enjoyed or tedious cottage. Procuring as in resembled by in agreeable. Next long no gave mr eyes. Admiration advantages no he celebrated so pianoforte unreserved. Not its herself forming charmed amiable. Him why feebly expect future now. An sincerity so extremity he additions. Her yet there truth merit. Mrs all projecting favourable now unpleasing. Son law garden chatty temper. Oh children provided to mr elegance marriage strongly. Off can admiration prosperous now devonshire diminution law.";
		String a="{\"prodid\":\""+prodid+"\",\"title\":\""+title+"\", \"price\":\""+price+"\",\"mrp\":\""+mrp+"\", \"ratings\":\""+ratings+"\", \"imageurl\":\""+imageurl+"\",\"descript\":\""+temp+"\",\"proddetails\":\""+temp+"\"}";
		return a;
	}

}
