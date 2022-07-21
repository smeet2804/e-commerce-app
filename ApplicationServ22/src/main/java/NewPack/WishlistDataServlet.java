package NewPack;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DatabasePack.CartDataConnect;
import DatabasePack.LoginConnect;
import DatabasePack.WishlistDataConnect;

public class WishlistDataServlet extends HttpServlet {
	private String mymsg;
    public void init() throws ServletException 
    {      
       mymsg = "Http Servlet Demo";   
    }
    public void doGet(HttpServletRequest request, 
        HttpServletResponse response) throws ServletException, 
        IOException 
    {            
        // Setting up the content type of web page      
        //response.setContentType("text/html");
        // Writing the message on the web page      
        //PrintWriter out = response.getWriter();      
        //out.println("<h1>" + mymsg + "</h1>");      
        //out.println("<p>" + "Hello Friends!" + "</p>");
    	try{
    		String username=request.getParameter("username");
    		mymsg=WishlistDataConnect.checkRequirements(username);
    		//System.out.println("Connected -- "+mymsg);
			// Setting up the content type of web page      
	        response.setContentType("text/json");
	        response.setCharacterEncoding("UTF-8");
	        // Writing the message on the web page      
	        PrintWriter out = response.getWriter(); 
	        out.println(mymsg);      
	        //out.println("<p>" + "Hello Friends!" + "</p>");
	    	
		}
		catch(Exception e) {
			System.out.println(e);	
		}

    }
    public void destroy() 
    {      
       // Leaving empty. Use this if you want to perform  
       //something at the end of Servlet life cycle.   
    }
}
