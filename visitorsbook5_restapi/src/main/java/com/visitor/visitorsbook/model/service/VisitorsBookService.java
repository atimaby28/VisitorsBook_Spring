package com.visitor.visitorsbook.model.service;

import java.util.List;
import java.util.Map;

import com.visitor.util.PageNavigation;
import com.visitor.visitorsbook.model.VisitorsBookDto;

public interface VisitorsBookService {

	void registerArticle(VisitorsBookDto visitorsBookDto) throws Exception;
	List<VisitorsBookDto> listArticle(Map<String, String> map) throws Exception;
	PageNavigation makePageNavigation(Map<String, String> map) throws Exception;
	VisitorsBookDto getArticle(int articleNo) throws Exception;
	void updateArticle(VisitorsBookDto visitorsBookDto) throws Exception;
	void deleteArticle(int articleNo, String path) throws Exception;
	
}
