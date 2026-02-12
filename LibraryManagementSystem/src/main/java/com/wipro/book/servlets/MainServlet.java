package com.wipro.book.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wipro.book.bean.BookBean;
import com.wipro.book.dao.AuthorDao;
import com.wipro.book.service.Administrator;

@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String operation=request.getParameter("operation");
		if(operation.equals("AddBook")) {
			String result=addBook(request);
			if(result.equals("SUCCESS")) {
				response.sendRedirect("menu.html");
			}
			else if(result.equals("INVALID")) {
				response.sendRedirect("Invalid.html");
			}
			else if(result.equals("FAILURE")) {
				response.sendRedirect("failure.html");
			}
		}
		else if(operation.equals("Search")) {
			String isbn=request.getParameter("isbn");
			BookBean bookBean=viewBook(isbn);
			if(bookBean==null) {
				response.sendRedirect("Invalid.html");
			}
			else {
				HttpSession session=request.getSession();
				session.setAttribute("book",bookBean);
				RequestDispatcher rd=request.getRequestDispatcher("ViewServlet");
				rd.forward(request, response);

				}
		}
	}
	
	public String addBook(HttpServletRequest request) {
		String isbn=request.getParameter("isbn");
		String bookName=request.getParameter("bookName");
		String bookType=request.getParameter("bookType");
		String authorName=request.getParameter("authorName");
		String cost=request.getParameter("cost");
		
		BookBean bookbean=new BookBean();
		bookbean.setIsbn(isbn);
		bookbean.setBookName(bookName);
		bookbean.setBookType(bookType.charAt(0));
		bookbean.setCost(Float.parseFloat(cost));
		bookbean.setAuthor(new AuthorDao().getAuthor(authorName));
		
		String result=new Administrator().addBook(bookbean);
		return result;
	}
    public BookBean viewBook(String isbn) {
    	Administrator administrator=new Administrator();
    	return administrator.viewBook(isbn);
    }
}