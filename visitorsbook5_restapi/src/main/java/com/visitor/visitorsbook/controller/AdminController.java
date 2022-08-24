package com.visitor.visitorsbook.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.visitor.visitorsbook.model.VisitorDto;
import com.visitor.visitorsbook.model.service.VisitorService;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
	private VisitorService visitorService;


/*
	@RequestMapping(value = "/visitor", method = RequestMethod.GET, headers = { "Content-type=application/json" })
	public List<VisitorDto> visitorList() throws Exception {
		List<VisitorDto> list = visitorService.listVisitor();
		logger.debug("회원목록 : {}", list);
		return list;
//		return visitorService.listvisitor();
	}
	
	@RequestMapping(value = "/visitor", method = RequestMethod.POST, headers = { "Content-type=application/json" })
	public List<VisitorDto> visitorRegister(@RequestBody VisitorDto visitorDto) throws Exception {
		visitorService.registerVisitor(visitorDto);
		return visitorService.listVisitor();
	}
	
	@RequestMapping(value = "/visitor/{visitorid}", method = RequestMethod.GET, headers = { "Content-type=application/json" })
	public VisitorDto visitorInfo(@PathVariable("visitorid") String visitorid) throws Exception {
		return visitorService.getVisitor(visitorid);
	}
	
	@RequestMapping(value = "/visitor", method = RequestMethod.PUT, headers = { "Content-type=application/json" })
	public List<VisitorDto> visitorModify(@RequestBody VisitorDto visitorDto) throws Exception {
		visitorService.updateVisitor(visitorDto);
		return visitorService.listVisitor();
	}
	
	@RequestMapping(value = "/visitor/{visitorid}", method = RequestMethod.DELETE, headers = { "Content-type=application/json" })
	public List<VisitorDto> visitorDelete(@PathVariable("visitorid") String visitorid) throws Exception {
		visitorService.deleteVisitor(visitorid);
		return visitorService.listVisitor();
	}
*/
	
	@GetMapping(value = "/visitor")
	public ResponseEntity<List<VisitorDto>> visitorList() throws Exception {
		List<VisitorDto> list = visitorService.listVisitor();
		if(list != null && !list.isEmpty()) {
//			return new ResponseEntity<List<VisitorDto>>(list, HttpStatus.OK);
//			return new ResponseEntity<List<VisitorDto>>(HttpStatus.NOT_FOUND);
			return new ResponseEntity<List<VisitorDto>>(HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
	}
	
	@PostMapping(value = "/visitor")
	public ResponseEntity<List<VisitorDto>> visitorRegister(@RequestBody VisitorDto visitorDto) throws Exception {
		visitorService.registerVisitor(visitorDto);
		List<VisitorDto> list = visitorService.listVisitor();
		return new ResponseEntity<List<VisitorDto>>(list, HttpStatus.OK);
//		return new ResponseEntity<Integer>(cnt, HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/visitor/{visitorid}")
	public ResponseEntity<VisitorDto> visitorInfo(@PathVariable("visitorid") String visitorid) throws Exception {
		logger.debug("파라미터 : {}", visitorid);
		VisitorDto visitorDto = visitorService.getVisitor(visitorid);
		if(visitorDto != null)
			return new ResponseEntity<VisitorDto>(visitorDto, HttpStatus.OK);
		else
			return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping(value = "/visitor")
	public ResponseEntity<List<VisitorDto>> visitorModify(@RequestBody VisitorDto visitorDto) throws Exception {
		visitorService.updateVisitor(visitorDto);
		List<VisitorDto> list = visitorService.listVisitor();
		return new ResponseEntity<List<VisitorDto>>(list, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/visitor/{visitorid}")
	public ResponseEntity<List<VisitorDto>> visitorDelete(@PathVariable("visitorid") String visitorid) throws Exception {
		visitorService.deleteVisitor(visitorid);
		List<VisitorDto> list = visitorService.listVisitor();
		return new ResponseEntity<List<VisitorDto>>(list, HttpStatus.OK);
	}
}
