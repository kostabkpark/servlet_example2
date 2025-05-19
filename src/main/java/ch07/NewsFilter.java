package ch07;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/news")
public class NewsFilter implements Filter {
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest httpReq = (HttpServletRequest) request;
    if(httpReq.getMethod().equalsIgnoreCase("POST")) {
      request.setCharacterEncoding("UTF-8");
    }
    chain.doFilter(request, response);
  }
 }
