package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;


public class BoardDao {
	// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		private String driver = "oracle.jdbc.driver.OracleDriver";
		private String url = "jdbc:oracle:thin:@localhost:1521:xe";
		private String id = "webdb";
		private String pw = "webdb";

		private void getConnection() {
			try {
				// 1. JDBC 드라이버 (Oracle) 로딩
				Class.forName(driver);

				// 2. Connection 얻어오기
				conn = DriverManager.getConnection(url, id, pw);
				// System.out.println("접속성공");

			} catch (ClassNotFoundException e) {
				System.out.println("error: 드라이버 로딩 실패 - " + e);
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		}

		public void close() {
			// 5. 자원정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		}

	
		public List<BoardVo> select() {
			System.out.println("select");
			List<BoardVo> boardList = new ArrayList<BoardVo>();
	    	getConnection();
	    	
			try {
				String query = "";
				query += " SELECT bo.no, " ;
				query += "       bo.title, " ;
				query += "       us.name," ;
				query += "		 bo.user_no, ";
				query += "       bo.hit," ;
				query += "       bo.reg_date" ;
				query += " FROM users us,board bo" ;
				query += " WHERE us.no=bo.user_no" ;
			
				System.out.println(query);
				
				pstmt = conn.prepareStatement(query);
				rs = pstmt.executeQuery();
			    
				
			    // 4.결과처리
			    while(rs.next()) {
			    	int no = rs.getInt("no");
			    	String title = rs.getString("title");
			    	String name = rs.getString("name");
			    	int user_no = rs.getInt("user_no");
			    	int hit = rs.getInt("hit");
			    	String reg_date = rs.getString("reg_date");
			    	
			    	//리스트에 추가
			    	BoardVo boardVo = new BoardVo(no, title, name, user_no, hit, reg_date);
			    	boardList.add(boardVo);
			    }
		
			} catch (SQLException e) {
			    System.out.println("error:" + e);
			}
			
			close();
			return boardList;
		}
		
		//read 글보기
		public BoardVo getpost(int pNo) {
			//리스트 준비
			BoardVo boardVo = null;
			getConnection();
			try {
			    // 3. SQL문 준비 / 바인딩 / 실행
				String query = "";
				query += " select bo.no, ";
				query += " 		  bo.title, ";
				query += " 		  bo.content, ";
				query += " 		  us.name, ";
				query += " 		  bo.hit, ";
				query += " 		  bo.reg_date, ";
				query += " 		  bo.user_no ";
				query += " from board bo, users us ";
				query += " where bo.user_no = us.no ";
				query += " and bo.no = ? ";				
				
				pstmt = conn.prepareStatement(query);
				
				pstmt.setInt(1, pNo);
				rs = pstmt.executeQuery();
				
		    // 4.결과처리
				while (rs.next()) {
					int no = rs.getInt("no");
					String title = rs.getString("title");
					String content = rs.getString("content");
					String name = rs.getString("name");
					int hit = rs.getInt("hit");
					String reg_date = rs.getString("reg_date");
					int user_no = rs.getInt("user_no");
					
					boardVo = new BoardVo();
					boardVo.setNo(no);
					boardVo.setTitle(title);
					boardVo.setContent(content);
					boardVo.setName(name);
					boardVo.setHit(hit);
					boardVo.setReg_date(reg_date);
					boardVo.setUser_no(user_no);
					
				}
				
					System.out.println(boardVo.toString());
			} catch (SQLException e) {
			    System.out.println("error:" + e);
			}
			close();
			return boardVo;
		}
		
		
			
	
		public void delete(int no) {
			getConnection();
			try {
			    // 3. SQL문 준비 / 바인딩 / 실행
				String query = "";
				query += " delete from board ";
				query += " where no=? ";
				pstmt = conn.prepareStatement(query);
				
				pstmt.setInt(1, no);
				pstmt.executeUpdate();
				
			    
		    // 4.결과처리
			} catch (SQLException e) {
			    System.out.println("error:" + e);
			}
			close();
		}
		
		//게시글 등록
		public void insert(String title, String content, int user_no) {
			getConnection();

			try {
				// 3. SQL문 준비 / 바인딩 / 실행 --> 완성된 sql문을 가져와서 작성할것
				String query = "";
				query += " INSERT INTO board VALUES(seq_board_no.nextval, ?, ?, 0, sysdate, ?) ";

				pstmt = conn.prepareStatement(query); // 쿼리로 만들기

				pstmt.setString(1, title); // ?(물음표) 중 1번째, 순서중요
				pstmt.setString(2, content); // ?(물음표) 중 2번째, 순서중요
				pstmt.setInt(3, user_no); // ?(물음표) 중 3번째, 순서중요

				pstmt.executeUpdate(); // 쿼리문 실행
			
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
			close();
		}
		//게시글 수정
		public void modify(int no, String title, String content) {
			getConnection();

			try {
				// 3. SQL문 준비 / 바인딩 / 실행
				String query = ""; // 쿼리문 문자열만들기, ? 주의
				query += " update board ";
				query += " set title = ? , ";
				query += "     content = ? ";
				query += " where no = ? ";

				pstmt = conn.prepareStatement(query); // 쿼리로 만들기

				pstmt.setString(1, title); // ?(물음표) 중 1번째, 순서중요
				pstmt.setString(2, content); // ?(물음표) 중 2번째, 순서중요
				pstmt.setInt(3, no); // ?(물음표) 중 3번째, 순서중요

				pstmt.executeUpdate(); // 쿼리문 실행

			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

			close();
		}
		
		
	
		
		
		
		
}//boardDao
