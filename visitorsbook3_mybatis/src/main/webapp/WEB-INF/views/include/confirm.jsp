<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <c:if test="${!empty visitorinfo}">
	<div class="m-3 text-right">
		<strong>${visitorinfo.visitorName}</strong> (${visitorinfo.visitorId})님 안녕하세요.
	</div>
	</c:if>
