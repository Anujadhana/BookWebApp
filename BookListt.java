package com.book.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/BookListt")
public class BookListt extends HttpServlet {
	
public static final String query="SELECT BOOKNAME, BOOKEDITION, BOOKPRICE FROM BOOKDETAILS";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//get printwriter
		PrintWriter pw=resp.getWriter();
		
		//get content type
		resp.setContentType("text/html");
		
		
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
			    ResultSet rs=ps.executeQuery();
			    pw.println("<table>");
			    pw.println("<tr>");
			    pw.println("<th>Book Name</th>");
			    pw.println("<th>Book Edition</th>");
			    pw.println("<th>Book Price</th>");
			    pw.println("</tr>");
			    
			    while(rs.next()) {
			    	   
			    	    pw.println("<tr>");
					    pw.println("<td>"+rs.getString(1)+"</td>");
					    pw.println("<td>"+rs.getString(2)+"</td>");
					    pw.println("<td>"+rs.getFloat(3)+"</td>");
					    pw.println("</tr>");
			    }
			    pw.println("</table>");
		}catch(SQLException se){
			
			System.out.println(se);
			pw.println("<h1>"+se.getMessage()+"<h2>");
			
		}catch(Exception e){
			System.out.println(e);
			pw.println("<h1>"+e.getMessage()+"</h2>");
		}
		pw.println("<a href='home.html'>Home</a>");
		
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		doGet(req, resp);
	}

}
