package ch07;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NewsDAO {
	final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	final String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/newsdb?serverTimezone=Asia/Seoul";
	
	public Connection open() {
		Connection conn = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(JDBC_URL, "root", "1111");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public void addNews(News n) throws Exception {
		Connection conn = open();
		
		String sql = "insert into news (title, img, date, content) values(?,?,CURRENT_TIMESTAMP(),?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		try(conn; pstmt) {
			pstmt.setString(1, n.getTitle());
			pstmt.setString(2, n.getImg());
			pstmt.setString(3, n.getContent());
			
			pstmt.executeUpdate();	
		}
	}
	
	public List<News> getAll() throws Exception {
		Connection conn = open();
		List<News> newsList = new ArrayList<>();
		
		String sql = "select aid, title, date_format(date, '%Y-%m-%d %H:%i:%s') as cdate from news";

		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		try(conn; pstmt; rs) {
			while(rs.next()) {
				News n = new News();
				
				n.setAid(rs.getInt("aid"));
				n.setTitle(rs.getString("title"));
				n.setDate(rs.getString("cdate"));
				
				newsList.add(n);
			}
		}

		return newsList;
	}
	
	public News getNews(int aid) throws Exception {
		// 1) Connection 을 얻어온다.
		Connection conn = open();
		// 2) sql 을 작성한다.
		String sql = "select aid, title, img, date_format(date, '%Y-%m-%d %H:%i:%s') as cdate, content from news"
				+ " where aid=?";
		// 3) pstmt 에 sql 을 적용한다. 매개변수가 있다면 argument를 설정한다.
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, aid);
		
		News n = new News();
		// 4) pstmt sql 문을 실행한다. ==> rs 에 담는다.
		ResultSet rs = pstmt.executeQuery();
		
		try(conn; pstmt; rs) {
		// 5) 가져온 데이터를 처리한다.
			if(rs.next()) {
				n.setAid(rs.getInt("aid"));
				n.setTitle(rs.getString("title"));
				n.setImg(rs.getString("img"));
				n.setDate(rs.getString("cdate"));
				n.setContent(rs.getString("content"));
			};
		// 6) 리소스를 닫는다. try with resources 에 의해 자동으로 닫힘
		}
		// 7) 데이터베이스로부터 가져온 데이터를 반환한다.
		return n;
	}

	public void delNews(int aid) throws Exception {
		Connection conn = open();
		
		String sql = "delete from news where aid = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, aid);
		
		try(conn; pstmt) {
			if(pstmt.executeUpdate() == 0) {
				throw new SQLException("DB - news 삭제 에러");
			};
		}
	}
}
