<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

	<div id="header">
		<h1>
			<a href="/mysite2/main">MySite</a>
		</h1>

		<c:choose>
			<c:when test="${empty sessionScope.authUser}"> <!-- 로그인 실패 시 -->
				<ul>
					<li><a href="/mysite2/user?action=loginForm">로그인</a></li>
					<li><a href="/mysite2/user?action=joinForm">회원가입</a></li>
				</ul>
			</c:when>

			<c:otherwise> <!-- 로그인 성공 시 -->
				<ul>
					<li>${authUser.name}님안녕하세요^^</li>
					<li><a href="/mysite2/user?action=logout">로그아웃</a></li>
					<li><a href="/mysite2/user?action=modifyForm">회원정보수정</a></li>
				</ul>
			</c:otherwise>
		</c:choose>
	</div>
