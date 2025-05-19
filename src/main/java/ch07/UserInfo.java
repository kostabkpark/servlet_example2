package ch07;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/userinfo")
public class UserInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = req.getSession();
		Cookie[] cookies = req.getCookies();
		boolean login = false;
		for (Cookie cookie : cookies) {
			if(cookie.getName().equals("session_id") && 
			   cookie.getValue().equals(session.getAttribute("session_id"))) {
				login = true;
			}
		}
		if(login) {
			// LoginServlet 의 stores 에서 session_id 로 저장된 id 를 가져와야함.
			req.setAttribute("id", session.getAttribute("id"));
			req.setAttribute("pwd", session.getAttribute("pwd"));
		} else {
			req.setAttribute("id", null);
			req.setAttribute("pwd", null);
		}
		req.getRequestDispatcher("/ch07/loginOK.jsp").forward(req, resp);		
	}
}
