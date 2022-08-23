package com.visitor.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.visitor.visitorsbook.model.VisitorDto;

@SuppressWarnings("deprecation")
public class ConfirmInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		VisitorDto visitorDto = (VisitorDto) session.getAttribute("visitorinfo");
		if(visitorDto == null) {
			response.sendRedirect(request.getContextPath() + "/visitor/login");
			return false;
		}
		return true;
	}
	
}
