<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ include file="/WEB-INF/views/include/header.jsp" %>

	<div align="center">
		<h3>Spring 방명록!!! (API)</h3>
      	<c:if test="${empty visitorinfo}">
			<a href="${root}/visitor/register">회원가입</a><br />
	      	<a href="${root}/visitor/login">로그인</a><br />
      	</c:if>
      	<c:if test="${!empty visitorinfo}">
			<strong>${visitorinfo.visitorName}</strong> (${visitorinfo.visitorId})님 안녕하세요.<br />
	      	<a href="${root}/visitor/logout">로그아웃</a><br />
	      	<a href="${root}/visitorsbook/list?pg=1&key=&word=">글목록</a><br />
	      	<c:if test="${visitorinfo.visitorId eq 'admin'}">
	      		<a href="${root}/visitor/list">회원목록</a><br />
	      	</c:if>
      	</c:if>
	</div>
  </body>
</html>
