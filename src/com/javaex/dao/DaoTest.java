package com.javaex.dao;

import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.GuestVo;

public class DaoTest {

	public static void main(String[] args) {
		/*
		 * UserDao userDao = new UserDao();
		 * 
		 * UserVo vo = new UserVo("hi", "1234", "이정재", "female"); //UserVo의 생성자를 가져오는거야
		 * 4개생성자만들었어 여기에서 테스트 하기! userDao.insert(vo);
		 */

		/*
		 * BoardDao dao = new BoardDao();
		 * 
		 * dao.insert("안녕하세요", "반갑습니다", 4); List<BoardVo> bList = dao.select();
		 * 
		 * 
		 * System.out.println(bList.toString());
		 * 
		 * 
		 * for(BoardVo vo : bList) { System.out.println("[no : " + vo.getNo() +
		 * ", title : " + vo.getTitle() + ", name : " + vo.getName() + ", user_no : " +
		 * vo.getUser_no() + ", hit : " + vo.getHit() + ", reg_date : " +
		 * vo.getReg_date() + " ]");
		 * 
		 * }
		 */
		
		BookDao dao = new BookDao();
		List<GuestVo> gList = new ArrayList<>();
		
		gList = dao.select();
		
		for(GuestVo vo : gList) {
			System.out.println(vo.getNo() + ", " + vo.getName() + ", " + vo.getPassword() + ", " + vo.getContent() + ", " + vo.getDate());
		}
		
	}

}
