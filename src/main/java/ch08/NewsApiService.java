package ch08;

import ch07.News;
import ch07.NewsDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/news")
public class NewsApiService {
  NewsDAO newsDAO ;

  public NewsApiService() {
    newsDAO = new NewsDAO();
  }

//  @GET
//  @Produces(MediaType.APPLICATION_JSON)
//  public List<News> getNews() {
//    newsDAO.addNews();
//    return null;
//  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public String addNews(News news) {
    try {
      newsDAO.addNews(news);
    } catch (Exception e) {
      return "News API : 뉴스 등록 실패"; // 204
    }
    return "News API : 뉴스 등록 성공"; // 201
  }
}
