package com.visitor.visitorsbook.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.visitor.util.PageNavigation;
import com.visitor.visitorsbook.model.VisitorDto;
import com.visitor.visitorsbook.model.VisitorsBookDto;
import com.visitor.visitorsbook.model.service.VisitorsBookService;

//방명록 처리용 controller
@Controller
@RequestMapping("/visitorsbook")
public class VisitorsBookController {

	@Autowired
	private VisitorsBookService visitorsBookService;

	@GetMapping("/register")
	public String register() {
		return "visitorsbook/write";
	}

	@PostMapping("/register")
	public String register(VisitorsBookDto visitorsBookDto, Model model, HttpSession session) throws Exception {
		VisitorDto visitorDto = (VisitorDto) session.getAttribute("visitorinfo");
		visitorsBookDto.setVisitorId(visitorDto.getVisitorId());
//		try {
		visitorsBookService.registerArticle(visitorsBookDto);
		return "redirect:/visitorsbook/list?pg=1&key=&word=";
//		} catch (Exception e) {
//			e.printStackTrace();
//			model.addAttribute("msg", "글작성 처리중 문제발생!!!");
//			return "error/error";
//		}
	}

	@GetMapping("/list")
	public ModelAndView list(@RequestParam Map<String, String> map) {
		ModelAndView mav = new ModelAndView();

		String spp = map.get("spp"); // size per page (페이지당 글갯수)
		map.put("spp", spp != null ? spp : "10");
		try {
			List<VisitorsBookDto> list = visitorsBookService.listArticle(map);
			PageNavigation pageNavigation = visitorsBookService.makePageNavigation(map);
			mav.addObject("articles", list);
			mav.addObject("navigation", pageNavigation);
			mav.addObject("key", map.get("key"));
			mav.addObject("word", map.get("word"));
			mav.setViewName("visitorsbook/list");
		} catch (Exception e) {
			e.printStackTrace();
			mav.addObject("msg", "글목록 출력 중 문제 발생!!!");
			mav.setViewName("error/error");
		}
		return mav;
	}

	@GetMapping("/modify")
	public ModelAndView modify(@RequestParam("articleno") int articleNo) {
		ModelAndView mav = new ModelAndView();
		try {
			VisitorsBookDto visitorsBookDto = visitorsBookService.getArticle(articleNo);
			mav.addObject("article", visitorsBookDto);
			mav.setViewName("visitorsbook/modify");
		} catch (Exception e) {
			e.printStackTrace();
			mav.addObject("msg", "글얻기 중 문제 발생!!!");
			mav.setViewName("error/error");
		}
		return mav;
	}

	@PostMapping("/modify")
	public String modify(VisitorsBookDto visitorsBookDto, Model model) {
		try {
			visitorsBookService.updateArticle(visitorsBookDto);
//			model.addAttribute("msg", "글 수정 성공!!!");
			return "redirect:/visitorsbook/list?pg=1&key=&word=";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "글수정 처리중 문제발생!!!");
			return "error/error";
		}
	}

	@GetMapping("/delete")
	public String delete(@RequestParam("articleno") int articleNo, Model model, RedirectAttributes redirectAttributes) {
		try {
			visitorsBookService.deleteArticle(articleNo);
			redirectAttributes.addAttribute("msg", "글 삭제 성공!!!");
//			redirectAttributes.addFlashAttribute("msg", "글 삭제 성공!!!");
			return "redirect:/visitorsbook/list?pg=1&key=&word=";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "글삭제 처리중 문제발생!!!");
			return "error/error";
		}
	}

}
