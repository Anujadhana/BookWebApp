package com.book.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	
	public static final String query="INSERT INTO BOOKDETAILS(BOOKNAME, BOOKEDITION, BOOKPRICE) VALUES(?,?,?)";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//get printwriter
		PrintWriter pw=resp.getWriter();
		
		//get content type
		resp.setContentType("text/html");
		
		//get the book info
		String bookName=req.getParameter("bookName");
		String bookEdition =req.getParameter("bookEdition");
		float bookPrice=Float.parseFloat(req.getParameter("bookPrice"));
		
		//load jdbc driver
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch(ClassNotFoundException cnf) {
			System.out.println(cnf);
		}
		// connection with database
	
		try (Connection con=DriverManager.getConnection("jdbc:mysql:///db1", "root", "root");
				PreparedStatement ps=con.prepareStatement(query);){
			    ps.setString(1, bookName);
			    ps.setString(2, bookEdition);
			    ps.setFloat(3, bookPrice);
			    
			    int count=ps.executeUpdate();
			    if(count==1) {
			    	pw.println("<h2>Record has been submitted successfully</h2>");
			    }
			    	else {
			    		pw.println("<h2>Record not submitted </h2>");
			    	}
			    
			
		}catch(SQLException se){
			
			System.out.println(se);
			pw.println("<h1>"+se.getMessage()+"<h2>");
			
		}catch(Exception e){
			System.out.println(e);
			pw.println("<h1>"+e.getMessage()+"</h2>");
		}
		pw.println("<a href='home.html'>Home</a>");
		pw.println("<br>");
		pw.println("<a href='BookListt'> Book List</a>");
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		doGet(req, resp);
	}

}
