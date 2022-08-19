package com.visitor.visitorsbook.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.visitor.util.PageNavigation;
import com.visitor.visitorsbook.model.FileInfoDto;
import com.visitor.visitorsbook.model.VisitorsBookDto;
import com.visitor.visitorsbook.model.VisitorDto;
import com.visitor.visitorsbook.model.service.VisitorsBookService;

//방명록 처리용 controller
@Controller
@RequestMapping("/visitorsbook")
public class VisitorsBookController {

	private static final Logger logger = LoggerFactory.getLogger(VisitorsBookController.class);

	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private VisitorsBookService visitorsBookService;

	@GetMapping("/register")
	public String register() {
		return "visitorsbook/write";
	}

	@PostMapping("/register")
	public String register(VisitorsBookDto visitorsBookDto, @RequestParam("upfile") MultipartFile[] files, Model model,
			HttpSession session, RedirectAttributes redirectAttributes)
			throws Exception {
		VisitorDto visitorDto = (VisitorDto) session.getAttribute("visitorinfo");
		visitorsBookDto.setVisitorId(visitorDto.getVisitorId());

//		FileUpload 관련 설정.
		logger.debug("MultipartFile.isEmpty : {}", files[0].isEmpty());
		if (!files[0].isEmpty()) {
			String realPath = servletContext.getRealPath("/upload");
//			String realPath = servletContext.getRealPath("/resources/img");
			String today = new SimpleDateFormat("yyMMdd").format(new Date());
			String saveFolder = realPath + File.separator + today;
			logger.debug("저장 폴더 : {}", saveFolder);
			File folder = new File(saveFolder);
			if (!folder.exists())
				folder.mkdirs();
			List<FileInfoDto> fileInfos = new ArrayList<FileInfoDto>();
			for (MultipartFile mfile : files) {
				FileInfoDto fileInfoDto = new FileInfoDto();
				String originalFileName = mfile.getOriginalFilename();
				if (!originalFileName.isEmpty()) {
					String saveFileName = UUID.randomUUID().toString()
							+ originalFileName.substring(originalFileName.lastIndexOf('.'));
					fileInfoDto.setSaveFolder(today);
					fileInfoDto.setOriginFile(originalFileName);
					fileInfoDto.setSaveFile(saveFileName);
					logger.debug("원본 파일 이름 : {}, 실제 저장 파일 이름 : {}", mfile.getOriginalFilename(), saveFileName);
					mfile.transferTo(new File(folder, saveFileName));
				}
				fileInfos.add(fileInfoDto);
			}
			visitorsBookDto.setFileInfos(fileInfos);
		}

		visitorsBookService.registerArticle(visitorsBookDto);
		redirectAttributes.addFlashAttribute("msg", "글작성 성공!!!");
		return "redirect:/visitorsbook/list?pg=1&key=&word=";
	}

	@GetMapping("/list")
	public ModelAndView list(@RequestParam Map<String, String> map) throws Exception {
		ModelAndView mav = new ModelAndView();

		String spp = map.get("spp"); // size per page (페이지당 글갯수)
		map.put("spp", spp != null ? spp : "10");
		List<VisitorsBookDto> list = visitorsBookService.listArticle(map);
		PageNavigation pageNavigation = visitorsBookService.makePageNavigation(map);
		mav.addObject("articles", list);
		mav.addObject("navigation", pageNavigation);
		mav.addObject("key", map.get("key"));
		mav.addObject("word", map.get("word"));
		mav.setViewName("visitorsbook/list");
		return mav;
	}

	@GetMapping("/modify")
	public ModelAndView modify(@RequestParam("articleno") int articleNo) throws Exception {
		ModelAndView mav = new ModelAndView();
		VisitorsBookDto visitorsBookDto = visitorsBookService.getArticle(articleNo);
		mav.addObject("article", visitorsBookDto);
		mav.setViewName("visitorsbook/modify");
		return mav;
	}

	@PostMapping("/modify")
	public String modify(VisitorsBookDto visitorsBookDto, Model model, RedirectAttributes redirectAttributes)
			throws Exception {
		visitorsBookService.updateArticle(visitorsBookDto);
		redirectAttributes.addFlashAttribute("msg", "글 수정 성공!!!");
		return "redirect:/visitorsbook/list?pg=1&key=&word=";
	}

	@GetMapping("/delete")
	public String delete(@RequestParam("articleno") int articleNo, Model model, RedirectAttributes redirectAttributes)
			throws Exception {
		visitorsBookService.deleteArticle(articleNo);
		redirectAttributes.addFlashAttribute("msg", "글 삭제 성공!!!");
		return "redirect:/visitorsbook/list?pg=1&key=&word=";
	}
	
	@GetMapping(value = "/download")
	public ModelAndView downloadFile(@RequestParam("sfolder") String sfolder, @RequestParam("ofile") String ofile,
			@RequestParam("sfile") String sfile, HttpSession session) {
		VisitorDto visitorDto = (VisitorDto) session.getAttribute("visitorinfo");
		if (visitorDto != null) {
			Map<String, Object> fileInfo = new HashMap<String, Object>();
			fileInfo.put("sfolder", sfolder);
			fileInfo.put("ofile", ofile);
			fileInfo.put("sfile", sfile);

			return new ModelAndView("fileDownLoadView", "downloadFile", fileInfo);
		} else {
			return new ModelAndView("redirect:/");
		}
	}

}
