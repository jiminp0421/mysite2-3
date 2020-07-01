package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;


@WebServlet("/board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		//System.out.println(action);
		
		if("list".equals(action)) {
			System.out.println("list");
			
			BoardDao boardDao = new BoardDao();
			List<BoardVo> boardList = boardDao.select();
			System.out.println(boardList);
			
			request.setAttribute("boardList", boardList);
			
			WebUtil.forword(request, response, "/WEB-INF/views/board/list.jsp");
		
			//게시글 보기
		}else if("read".equals(action)) {
			System.out.println("read");
			
			int no = Integer.parseInt(request.getParameter("no"));
			System.out.println(no);
			BoardDao boardDao = new BoardDao();
			BoardVo boardVo = boardDao.getpost(no);
			System.out.println(boardVo.toString());
			
			request.setAttribute("readVo", boardVo );
			
			WebUtil.forword(request, response, "/WEB-INF/views/board/read.jsp");
		
			
			
			
			//삭제하기
		}else if("delete".equals(action)) {
			System.out.println("delete");
			
			int no = Integer.parseInt(request.getParameter("no"));
			
			BoardDao boardDao = new BoardDao();
			boardDao.delete(no);
			
			WebUtil.redirect(request, response, "/mysite2/board?action=list");
			
			
		}else if("writeForm".equals(action)) {
			System.out.println("writeForm");
			
			WebUtil.forword(request, response, "/WEB-INF/views/board/writeForm.jsp");
			//글쓰기
		}else if("write".equals(action)) {
			System.out.println("write");
			
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			int user_no = Integer.parseInt(request.getParameter("user_no"));
			
			BoardDao boardDao = new BoardDao();
			boardDao.insert(title, content, user_no);
			
			WebUtil.redirect(request, response, "/mysite2/board?action=list");
		
			
			//게시글 수정
		}else if("modifyForm".equals(action)) {
			System.out.println("modifyForm");

			int no = Integer.parseInt(request.getParameter("no"));
			
			BoardDao boardDao = new BoardDao();
			BoardVo boardVo = boardDao.getpost(no);
			
			request.setAttribute("modifyVo", boardVo);
			
			WebUtil.forword(request, response, "/WEB-INF/views/board/modifyForm.jsp");
		
		}else if("modify".equals(action)) {
			System.out.println("modify");
			
			int no = Integer.parseInt(request.getParameter("no"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			
			BoardDao boardDao = new BoardDao();
			boardDao.modify(no, title, content);
			
			WebUtil.redirect(request, response, "/mysite2/board?action=list");
		}
		
		
		
		
		
	}


	
	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
