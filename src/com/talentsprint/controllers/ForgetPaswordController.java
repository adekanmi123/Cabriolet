package com.talentsprint.controllers;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.talentsprint.beans.UserBean;
import com.talentsprint.dbconnection.UserDAOImplementation;

import sun.applet.resources.MsgAppletViewer_pt_BR;

/**
 * Servlet implementation class ForgetPaswordController
 */
@WebServlet("/ForgetPaswordController")
public class ForgetPaswordController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ForgetPaswordController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		UserDAOImplementation userDAO = new UserDAOImplementation();
		String Id = ((String)request.getParameter("fp_phoneNumber").trim());
		String email = ((String)request.getParameter("fp_email").trim());
		String emailId = userDAO.forgetPasswordGetEmail(Id);
		session.setAttribute("UserId",Id);
		System.out.println(email +" " + emailId);
		if(emailId.equals(email)){
			String[] to = { emailId };
			String subject = "Change Password";
			String msg = "You can reset your password using this verification code '"+userDAO.getPassword(Id)+"'";
			
			userDAO.sendMail(to, subject, msg);
			RequestDispatcher rd = request.getRequestDispatcher("ChangePassword.jsp");
			rd.forward(request, response);
			
			}else {
			userDAO.msgbox("Please enter the registerd E-mailId!!");
			RequestDispatcher rd = request.getRequestDispatcher("ForgetPassword.jsp");
			rd.forward(request, response);
			
			
		}
	}

}
