package com.visitor.interceptor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.mvc.WebContentInterceptor;

import com.visitor.visitorsbook.model.VisitorDto;

public class ConfirmInterceptor2 extends WebContentInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws ServletException {
		boolean result = false;
		
		HttpSession session = request.getSession();
		VisitorDto visitorDto = (VisitorDto) session.getAttribute("visitorinfo");
		if (visitorDto == null) {
			if(isAjaxRequest(request)) {
				try {
					/*
					 * ajax호출시 설정.
					 * beforeSend : function(xmlHttpRequest) {
					 * 	xmlHttpRequest.setRequestHeader("AJAX", "true");
					 * },
					 * success : function(data) {
					 * 	data 처리
					 * },
					 * error : function(e) {
					 * 	if (e.status == 400) {
					 * 		error처리
					 * 	}
					 * }
					 */
					response.sendError(400);
					result = false;
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				try {
					response.sendRedirect(request.getContextPath() + "/visitor/login");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else
			result = true;
		return result;
	}

	private boolean isAjaxRequest(HttpServletRequest req) {
		String header = req.getHeader("AJAX");
		if ("true".equals(header)) {
			return true;
		} else {
			return false;
		}
	}

}
