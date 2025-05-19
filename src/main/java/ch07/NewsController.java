package ch07;

import java.io.IOException;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.beanutils.BeanUtils;

/**
 * Servlet implementation class NewsController
 */
@WebServlet("/news")
@MultipartConfig(maxFileSize = 1024*1024*2, location = "c:/dev/workspace/example/src/main/webapp/img")
public class NewsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private NewsDAO newsDAO;
	private ServletContext context;

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		newsDAO = new NewsDAO();
		context = getServletContext();
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//req.setCharacterEncoding("utf-8");

		String action = req.getParameter("action");
		String path = "/ch07/";
		String view = "";
		
		if(action == null) {
			resp.sendRedirect("/news?action=list");
		} else {
			switch(action) {// try-catch 정리
			case "list" : view = list(req, resp); break;
			case "view" : view = view(req, resp); break;
			case "addNews" : view = addNews(req, resp); break;
			case "delNews" : view = delNews(req, resp); break;
			}
			if(view.startsWith("redirect:/")) {
				String redirectUrl = view.substring("redirect:/".length());
				resp.sendRedirect(redirectUrl);
			} else {
				context.getRequestDispatcher(path + view).forward(req, resp);	
			}
		}
	}
	
	private String addNews(HttpServletRequest req, HttpServletResponse resp)  {
		System.out.println(req.getMethod());
		if(req.getMethod().equals("POST")) {
			News n = new News();
			try {
				Part part = req.getPart("img");
				String filename = part.getSubmittedFileName();
				System.out.println(filename);
				if(filename != null && !filename.isEmpty()) {
					part.write(filename);
				}
				BeanUtils.populate(n, req.getParameterMap());
				
				n.setImg("img/"+ filename);
				
				newsDAO.addNews(n);
			} catch (Exception e) {
				e.printStackTrace();
				return list(req,resp);
			}
			return "redirect:/news?action=list";
		} else {
			return null;
		}
	}
	private String list(HttpServletRequest req, HttpServletResponse resp)  {
		if(req.getMethod().equals("GET")) {
			try {
				List<News> news = newsDAO.getAll();
				req.setAttribute("newsList", news);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "newsList.jsp";
		} else {
			return null;
		}
	}
	private String view(HttpServletRequest req, HttpServletResponse resp) {
		if(req.getMethod().equals("GET")) {
			int aid = Integer.parseInt(req.getParameter("aid"));
			try {
				News news = newsDAO.getNews(aid);
				req.setAttribute("news", news);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "newsView.jsp";
		} else {
			return null;
		}
	}
	
	private String delNews(HttpServletRequest req, HttpServletResponse resp) {
			int aid = Integer.parseInt(req.getParameter("aid"));
      try {
        newsDAO.delNews(aid);
      } catch (Exception e) {
        e.printStackTrace();
				req.setAttribute("error", "뉴스 삭제 중 오류 발생");
				return list(req,resp);
      }
      return "redirect:/news?action=list";
		}
}
