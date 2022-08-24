package com.visitor.visitorsbook.model.service;

import java.util.List;
import java.util.Map;

import com.visitor.visitorsbook.model.VisitorDto;

public interface VisitorService {

	VisitorDto login(Map<String, String> map) throws Exception;
	
	int idCheck(String checkId) throws Exception;
	void registerVisitor(VisitorDto visitorDto) throws Exception;
	
	List<VisitorDto> listVisitor() throws Exception;
	VisitorDto getVisitor(String visitorId) throws Exception;
	void updateVisitor(VisitorDto visitorDto) throws Exception;
	void deleteVisitor(String visitorId) throws Exception;
	
}
