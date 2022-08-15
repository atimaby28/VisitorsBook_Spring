package com.visitor.visitorsbook.model.dao;

import java.util.List;
import java.util.Map;

import com.visitor.visitorsbook.model.VisitorsBookDto;

public interface VisitorsBookDao {
	
	void registerArticle(VisitorsBookDto visitorsBookDto) throws Exception;
	List<VisitorsBookDto> listArticle(Map<String, Object> map) throws Exception;
	int getTotalCount(Map<String, String> map) throws Exception;
	VisitorsBookDto getArticle(int articleNo) throws Exception;
	void updateArticle(VisitorsBookDto visitorsBookDto) throws Exception;
	void deleteArticle(int articleNo) throws Exception;
	
}
