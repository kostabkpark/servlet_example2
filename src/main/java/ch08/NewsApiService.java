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

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<News> getAllNews() {
    List<News> newsList = null;
    try {
      newsList = newsDAO.getAll();
    } catch (Exception e) {
      return null;
    }
    return newsList;
  }

  @DELETE
  @Path("/{aid}")
  public String delNews(@PathParam("aid") int aid) {
    try {
      newsDAO.delNews(aid);
    } catch (Exception e) {
      return "news api : 뉴스 삭제 실패"; // 204
    }
    return "news api : 뉴스 삭제 성공"; // 204
  }

  @GET
  @Path("/{aid}")
  @Produces(MediaType.APPLICATION_JSON)
  public News getNews(@PathParam("aid") int aid) {
    News news = null;
    try {
      news = newsDAO.getNews(aid);
    } catch (Exception e) {
    }
    return news;
  }

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
