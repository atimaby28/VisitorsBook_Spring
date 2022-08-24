package com.visitor.visitorsbook.controller;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.visitor.visitorsbook.model.VisitorDto;
import com.visitor.visitorsbook.model.service.VisitorService;

//회원 처리용 controller
@Controller
@RequestMapping("/visitor")
public class VisitorController {

	private static final Logger logger = LoggerFactory.getLogger(VisitorController.class);

	@Autowired
	private VisitorService visitorService;

	@GetMapping("/register")
	public String register() {
		return "visitor/join";
	}
	
	@GetMapping("/idcheck")
//	@ResponseBody
	public @ResponseBody String idCheck(@RequestParam("ckid") String checkId) throws Exception {
		int idCount = visitorService.idCheck(checkId);
		JSONObject json = new JSONObject();
		json.put("idcount", idCount);
		return json.toString();
	}

	@PostMapping("/register")
	public String register(VisitorDto visitorDto, Model model) throws Exception {
		logger.debug("visitorDto info : {}", visitorDto);
		visitorService.registerVisitor(visitorDto);
		return "redirect:/visitor/login";
	}

	@GetMapping("/login")
	public String login() {
		return "visitor/login";
	}

	@PostMapping("/login")
	public String login(@RequestParam Map<String, String> map, Model model, HttpSession session,
			HttpServletResponse response) throws Exception {
		logger.debug("map : {}", map.get("visitorId"));
		VisitorDto visitorDto = visitorService.login(map);
		if (visitorDto != null) {
			session.setAttribute("visitorinfo", visitorDto);

			Cookie cookie = new Cookie("visitor_id", map.get("visitorId"));
			cookie.setPath("/");
			if ("saveok".equals(map.get("idsave"))) {
				cookie.setMaxAge(60 * 60 * 24 * 365 * 40);
			} else {
				cookie.setMaxAge(0);
			}
			response.addCookie(cookie);
			return "redirect:/";
		} else {
			model.addAttribute("msg", "아이디 또는 비밀번호 확인 후 다시 로그인하세요!");
			return "visitor/login";
		}
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	@GetMapping("/list")
	public String list() {
		return "visitor/list";
	}

}
