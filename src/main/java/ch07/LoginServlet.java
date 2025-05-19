package ch07;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletConfig;
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
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserService service;
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		service = new UserService();
		SessionManager sessionManager = new SessionManager();
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		String pwd = req.getParameter("pwd");
		// 데이터베이스에서 id 들고와서 pwd 일치하는지 확인 ==> 로그인 성공
		User user = service.findById(id);
		if(user.getPwd().equals(pwd)) {
			HttpSession session = req.getSession();
			
			String session_id = UUID.randomUUID().toString();
			session.setAttribute("session_id", session_id);
			session.setAttribute("id", id);
			session.setAttribute("pwd", pwd);
			//stores.put(session_id, id);
			Cookie cookie = new Cookie("session_id", session_id);
			cookie.setMaxAge(60*1);
			cookie.setPath("/");
			resp.addCookie(cookie);
			resp.sendRedirect("/userinfo");
		} else {
			// 데이터베이스에서 id 들고와서 pwd 일치하지 않으면 ==> 로그인 실패
			resp.sendRedirect("/ch07/login.jsp");
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = req.getSession();
		req.setAttribute("id", session.getAttribute("id"));
		req.getRequestDispatcher("/ch07/loginOK.jsp").forward(req, resp);
	}
}
