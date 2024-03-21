package com.visitor.visitorsbook.model.dao;

import java.util.Map;

import com.visitor.visitorsbook.model.VisitorDto;

public interface VisitorDao {

	int idCheck(String id) throws Exception;
	void registerVisitor(VisitorDto visitorDto) throws Exception;
	VisitorDto login(Map<String, String> map) throws Exception;
	
//	VisitorDto getVisitor(String id) throws Exception;
//	void updateVisitor(VisitorDto visitorDto) throws Exception;
//	void deleteVisitor(String id) throws Exception;
	
}
